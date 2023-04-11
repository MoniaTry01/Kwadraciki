import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Klasa, w ktorej przechowywane sa informacje nt poziomow
 */
public class Poziomy {

    /**
     * Lista przechowujaca kolory kontenerow/kwadratow obecnie wybranego poziomu
     */
    static ArrayList<Color> dostepneKolory = new ArrayList<Color>(4);
    /**
     * Obecny poziom w grze
     */
    static int ktoryPoziom = 1;
    /**
     * Ostatni poziom
     */
    static int maxPoziom = 5;
    /**
     * Obraz tla
     */
    public static ImageIcon tlo = new ImageIcon();

    /**
     * Wybor poziomu oraz ladowanie nastepnego po jego wygraniu
     */
    public void Poziomy() {

        switch (ktoryPoziom) {

            case 1:

                tlo = new ImageIcon("images/tlo_poziom1.jpg");

                GenerujKwadrat.ileKwadratow = 3;        //Ilosc kwadratow
                CountdownTimer.czas = 20;               //Czas na poziom

                dostepneKolory.add(new Color(198, 162, 100));          //Kolory kwadratow
                dostepneKolory.add(new Color(118, 82, 19));
                dostepneKolory.add(new Color(143, 165, 33));
                dostepneKolory.add(new Color(255, 187, 141));

                break;

            case 2:

                tlo = new ImageIcon("images/tlo_poziom2.jpg");

                GenerujKwadrat.ileKwadratow = 6;
                CountdownTimer.czas = 30;

                dostepneKolory.add(new Color(106,30,222));
                dostepneKolory.add(new Color(49,23,88));
                dostepneKolory.add(new Color(113,86,125));
                dostepneKolory.add(new Color(156,51,149));
                break;

            case 3:

                tlo = new ImageIcon("images/tlo_poziom3.jpg");

                GenerujKwadrat.ileKwadratow = 5;
                CountdownTimer.czas = 25;

                dostepneKolory.add(new Color(21,125,132));
                dostepneKolory.add(new Color(55,142,169));
                dostepneKolory.add(new Color(152,246,186));
                dostepneKolory.add(new Color(107,138,139));

                break;

            case 4:

                tlo = new ImageIcon("images/tlo_poziom4.jpg");

                GenerujKwadrat.ileKwadratow = 7;
                CountdownTimer.czas = 40;

                dostepneKolory.add(new Color(30,141,149));
                dostepneKolory.add(new Color(153,41,156));
                dostepneKolory.add(new Color(14,47,131));
                dostepneKolory.add(new Color(232,221,176));

                break;

            case 5:

                tlo = new ImageIcon("images/tlo_poziom5.jpg");

                GenerujKwadrat.ileKwadratow = 10;
                CountdownTimer.czas = 40;

                dostepneKolory.add(Color.darkGray);
                dostepneKolory.add(Color.gray);
                dostepneKolory.add(Color.lightGray);
                dostepneKolory.add(Color.black);

                break;

            default:
                break;

        }

        if (ktoryPoziom <= maxPoziom) {
            for (int i = 0; i < 4; i++)
                Okno.kontener[i].setBackground(dostepneKolory.get(i));
        }
         else{
             ZagrajDzwiek zd = new ZagrajDzwiek();
             zd.play("sounds/wygrana.wav");

            ktoryPoziom = Okno.poziomPoczatkowy;
            Okno.poziomObecny.setText("OBECNY POZIOM: " + Poziomy.ktoryPoziom);
            Okno.stopWcisniety = true;
            CountdownTimer.czas = 0;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            Okno.start.setEnabled(true);
            Okno.poziom.setEnabled(true);
            Okno.stop.setEnabled(false);
            Okno.wyjscie.setEnabled(true);
        }
    }

}
