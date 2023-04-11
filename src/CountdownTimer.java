import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer odliczajacy w dol czas w sekundach.
 * Tworzy nowy Thread, ktory obsluguje czas
 */
public class CountdownTimer extends Thread{
    /**
     * Timer odliczajacy czas
     */
    static Timer timer;
    /**
     * Czas Timera
     */
    static int czas = 1;

    /**
     * Glowna metoda Timera, ktora rozpoczyna jego odliczanie
     */
    public void run() {
        /**
         * Opoznienie Timera (w ms)
         */
        int delay = 0;
        /**
         * Czas po jakim wartosc Timera zmniejsza sie o '1'
         */
        int period = 1000;
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setInterval();
            }
        }, delay, period);

    }

    /**
     * Odliczanie czasu przez Timer, aktualizacja czasu, sprawdzanie czy jego wartość wynosi 0
     * Jezeli czas Timera wynosi 0 gra zostaje zakonczona.
     * @return czas Timera;
     */
    private static final int setInterval() {
        if (czas < 1){
            timer.cancel();
            if(!Okno.stopWcisniety){
                GenerujKwadrat gk = new GenerujKwadrat();
                GenerujKwadrat.ileKwadratow = 0;
                GenerujKwadrat.czyWygrana = false;
                gk.repaint();
                gk.SprawdzenieWygranej();
            }
        }

        Okno.licznik.setText("CZAS: " + czas);
        return czas--;
    }
}
