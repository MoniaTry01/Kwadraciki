import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Klasa do odtwarzania dzwiekow
 */
public class ZagrajDzwiek extends Thread implements LineListener{

    /**
     * Czy odtwarzanie nagrania zostalo zakonczone
     */
    boolean czyKoniec;

    /**
     * Odtwarza dzwiek
     * @param audioFilePath sciezka do pliku audio
     */
    void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);

        try {
            //ladujemy plik dzwiekowy
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);

            audioClip.start();

            //Czekamy na zakonczenie nagrania
            while (!czyKoniec) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            //Upewniamy sie, ze klip bedzie mozna jeszcze raz odtworzyc
            if (audioClip.isRunning()) {
                audioClip.stop();
                audioClip.flush();
            }

            } catch (UnsupportedAudioFileException ex) {
                System.out.println("Niepoprawne rozszerzenie nagrania.");
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                System.out.println("Audio line jest niedostepny.");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Blad przy odtwarzaniu nagrania.");
                ex.printStackTrace();
            }

    }

    /**
     * Sprawdza czy dzwiek skonczyl odtwarzanie
     * @param event wydarzenie dzwieku
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START)
            czyKoniec = true;   //informacja o zakonczeniu nagrania
    }

    /**
     * Klasa glowna
     * @param args argument main
     */
    public static void main(String[] args) {
        String audioFilePath = "";
        ZagrajDzwiek player = new ZagrajDzwiek();
        player.play(audioFilePath);
    }
}