import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa, w ktorej tworzone jest okno oraz jego elementy
 */
public class Okno extends JFrame {

    /**
     * Okno programu
     */
    JFrame frame = new JFrame();

    /**
     * Glowny panel gry
     */
    static GenerujKwadrat gra = new GenerujKwadrat();
    /**
     * Kontenery, do ktorych przeciagane sa kwadraty
     */
    static JPanel[] kontener = new JPanel[4];
    /**
     * Panel menu
     */
    static JPanel menu = new JPanel();
    //elementy menu
    /**
     * Przycisk START
     */
    static JButton start = new JButton("START");
    /**
     * Przycisk STOP
     */
    static JButton stop = new JButton("STOP");
    /**
     * Przycisk WYJSCIE
     */
    static JButton wyjscie = new JButton("WYJSCIE");
    /**
     * Spinner do zmiany poziomow
     */
    static JSpinner poziom = new JSpinner(new SpinnerNumberModel(1, 1, Poziomy.maxPoziom, 1));
    /**
     * Label, na ktorym wyswietlany jest pozostaly czas na ukonczenie poziomu
     */
    static JLabel licznik = new JLabel();
    /**
     * Label wyswietlajcy obecny poziom gry
     */
    static JLabel poziomObecny = new JLabel("OBECNY POZIOM: " + Poziomy.ktoryPoziom);
    /**
     * Domyslny poczatkowy poziom
     */
    static int poziomPoczatkowy = 1;
    /**
     * Label, na ktorym wyswietlany jest poczatkowy poziom
     */
    static JLabel poziomPocz = new JLabel("POCZĄTKOWY POZIOM: " + poziomPoczatkowy);
    /**
     * Szerokosc okna
     */
    public static int width = 1280;
    /**
     * Wysokosc ookna
     */
    public static int height = 1024;

    /**
     * Metoda definiuje parametry okna oraz jego elemntow
     */
    Okno() {

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setTitle("Kwadraciki");
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setSize(width, height);
        panel.setBackground(Color.gray);

        gra.Panel();

        menu.setPreferredSize(new Dimension(width - 30, 124));

        JPanel informacjaPoziom = new JPanel();
        informacjaPoziom.setLayout(new GridLayout(3, 1));


        start.setPreferredSize(new Dimension(200, 100));
        start.addActionListener(new PrzyciskStart());
        start.setFont(new Font("Roboto", Font.BOLD, 30));

        stop.setPreferredSize(new Dimension(200, 100));
        stop.addActionListener(new PrzyciskStop());
        stop.setEnabled(false);
        stop.setFont(new Font("Roboto", Font.BOLD, 30));

        wyjscie.setPreferredSize(new Dimension(200, 100));
        wyjscie.addActionListener(new PrzyciskWyjscie());
        wyjscie.setFont(new Font("Roboto", Font.BOLD, 30));

        poziom.setPreferredSize(new Dimension(100, 100));
        poziom.addChangeListener(new Spinner());
        ((JSpinner.DefaultEditor) poziom.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) poziom.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        poziom.setFont(new Font("Roboto", Font.BOLD, 40));

        licznik.setText("CZAS: " + countdownTimer.czas);
        licznik.setFont(new Font("Roboto", Font.BOLD, 20));

        poziomPocz.setFont(new Font("Roboto", Font.BOLD, 20));
        poziomObecny.setFont(new Font("Roboto", Font.BOLD, 20));


        frame.add(panel);

        panel.add(gra);
        panel.add(menu);

        menu.add(start);
        menu.add(Box.createHorizontalStrut(20));
        menu.add(stop);
        menu.add(Box.createHorizontalStrut(20));
        menu.add(wyjscie);
        menu.add(Box.createHorizontalStrut(20));
        menu.add(poziom);
        menu.add(Box.createHorizontalStrut(50));
        menu.add(informacjaPoziom);

        informacjaPoziom.add(licznik);
        informacjaPoziom.add(poziomPocz);
        informacjaPoziom.add(poziomObecny);

        frame.setVisible(true);
    }

    /**
     * Spinner sluzacy do zmiany poziomu
     */
    //Spinner, w ktorym zmieniany jest poczatkowy poziom
    public static class Spinner implements ChangeListener {

        /**
         * Wybor poziomu
         * @param e zmiana liczby na spinnerze
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner s = (JSpinner) e.getSource();
            int poziom = (int) s.getValue();
            poziomPoczatkowy = poziom;
            Poziomy.ktoryPoziom = poziom;

        }
    }

    /**
     * Jezeli program zostal dopiero wlaczony, wyswietl okno tytulowe
     */
    boolean czyPierwszeWlaczenie = true;
    /**
     * Timer odliczajacy czas w poziomach
     */
    CountdownTimer countdownTimer = new CountdownTimer();

    /**
     * Przycisk START, po kliknieciu laduje wybrany poziom
     */
    public class PrzyciskStart implements ActionListener {
        /**
         * Inicjuje ladowanie poziomu na ekran gry
         * @param e wcisniecie przycisku;
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            stopWcisniety = false;

            //Generacja kontenerow
            if (czyPierwszeWlaczenie) {

                kontener[0] = new JPanel();
                kontener[0].setSize(300, 200);
                kontener[0].setLocation(10, 645);

                kontener[1] = new JPanel();
                kontener[1].setSize(300, 200);
                kontener[1].setLocation(320, 645);

                kontener[2] = new JPanel();
                kontener[2].setSize(300, 200);
                kontener[2].setLocation(630, 645);

                kontener[3] = new JPanel();
                kontener[3].setSize(300, 200);
                kontener[3].setLocation(940, 645);

                for (int i = 0; i < 4; i++)
                    gra.add(kontener[i]);

                czyPierwszeWlaczenie = false;
            }

            //Wylaczenie przycisku START i slidera do wyboru poziomu
            start.setEnabled(false);
            poziom.setEnabled(false);
            stop.setEnabled(true);
            wyjscie.setEnabled(false);

            Poziomy.ktoryPoziom = poziomPoczatkowy;
            poziomPocz.setText("POCZATKOWY POZIOM: " + poziomPoczatkowy);
            poziomObecny.setText("OBECNY POZIOM: " + Poziomy.ktoryPoziom);

            gra.repaint();
            countdownTimer.run();

            gra.GenerujKoordynaty();
        }
    }

    /**
     * Spraawdza, czy przycisk stop zostal wcisniety
     */
    static boolean stopWcisniety = false;

    /**
     * Przerywa gre
     */
    public class PrzyciskStop implements ActionListener {

        /**
         * Gra zostaje wstrzymana
         * @param e wcisniecie przycisku
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            stopWcisniety = true;

            //Program musi zaczekac 1s, aby timer na pewno zakonczyl prace
            //Inaczej istnieje ryzyko powstania drugiego odliczania;

            countdownTimer.czas = 0;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            //Usuniecie wszystkich elementow na panelu
            GenerujKwadrat.kwadraty.removeAll(GenerujKwadrat.kwadraty);
            GenerujKwadrat.kolory.removeAll(GenerujKwadrat.kolory);
            Poziomy.dostepneKolory.removeAll(Poziomy.dostepneKolory);

            start.setEnabled(true);
            poziom.setEnabled(true);
            stop.setEnabled(false);
            wyjscie.setEnabled(true);
        }
    }

    /**
     * Przycisk wylacza gre
     */
    public class PrzyciskWyjscie implements ActionListener {

        /**
         * Program zostaje przerwany
         * @param e wcisniecie przycisku
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            countdownTimer.czas = 0;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            frame.dispose();
        }
    }
}