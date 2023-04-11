import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Graphics;


/**
 * Klasa bedaca glownym panelem gry oraz obslugujaca rozgrywke
 */
public class GenerujKwadrat extends JPanel{

    /**
     * Metoda definiujaca parametry panelu gry
     */
    //Parametry glownego panelu
    public void Panel(){
        this.setPreferredSize(new Dimension(1250, 845));
        this.setLayout(null);
        this.addMouseListener(new ma());
        this.addMouseMotionListener(new ma());
    }

    /**
     * Ilosc kwadratow, ktora ma zostac wygenerowana
     */
    static int ileKwadratow;
    /**
     * Lista kolorow, ktorego koloru maja zostac wygenerowane kwadraty
     */
    static ArrayList<Color> kolory = new ArrayList();
    /**
     * Lista kolorow poszczegolnych kwadratow
     */
    //static ArrayList<Rectangle> kwadraty = new ArrayList();
    static ArrayList<Rectangle> kwadraty = new ArrayList<Rectangle>();
    /**
     * Wysokosc kwadratow
     */
    static int kwadHeight = 70;
    /**
     * Szerokosc kwadratow
     */
    static int kwadWidth = 70;

    //Generacja wspolrzednych kwadratow
    //Zaden z kwadratow nie moze na siebie nachodzic

    /**
     * Metoda generuje koordynaty kwadratow w polu gry
     */
    public void GenerujKoordynaty() {

        Poziomy poziomy = new Poziomy();                        //wczytywanie poziomu, w ktorym ma nastapic generacja kwadratow
        poziomy.Poziomy();

        Rectangle[] temp = new Rectangle[ileKwadratow];         //tymczasowa tablica do porownywanie wspolrzednych poszczegolnych kwadratow

        for (int i = 0; i < temp.length; i++) {

            Random r = new Random();

            int losujKolor = r.nextInt(4);               //losowanie kolorow poszczegolnych kwadratow

            if(losujKolor == 0)
                kolory.add(poziomy.dostepneKolory.get(0));
            else if (losujKolor == 1)
                kolory.add(poziomy.dostepneKolory.get(1));
            else if (losujKolor == 2)
                kolory.add(poziomy.dostepneKolory.get(2));
            else if (losujKolor == 3)
                kolory.add(poziomy.dostepneKolory.get(3));

            temp[i] = new Rectangle();
                                                                //Generacja wstepnych wspolrzednych kwadratu
            temp[i].x = 20 + r.nextInt(1110);             //Przedzial x = <20, 1130>
            temp[i].y = 20 + r.nextInt(505);              //Przedzial y = <20, 505>

            int czyOK = 0;                                    //Jezeli czyOK == ileKwadratow, oznacza to, ze zaden
            boolean czyNachodza = true;                       //na siebie nie nachodzi

            if (i > 0){
                while (czyNachodza) {

                    for (int j = 0; j < i; j++) {

                        boolean pion = true;
                        boolean poziom = true;

                        if ((temp[i].x < temp[j].x - (kwadWidth + 20) || temp[i].x > temp[j].x + (kwadWidth + 20)))
                            poziom = false;
                        if ((temp[i].y < temp[j].y - (kwadHeight + 20) || temp[i].y > temp[j].y + (kwadHeight + 20)))
                            pion = false;
                        if (!pion || !poziom)
                            czyOK++;

                        if(j == i - 1){
                            if(czyOK == i)
                                czyNachodza = false;
                            else{                                               //Jezeli kwadraty na siebie nachodza, nastepuje generacja nowych wspolrzednych
                                temp[i].x = 20 + r.nextInt(1110);
                                temp[i].y = 20 + r.nextInt(505);
                                j = -1;
                                czyOK = 0;
                            }
                        }
                    }
                }
            }
            kwadraty.add(temp[i]);
        }
    }

    static Rectangle r = new Rectangle();

    /**
     * Jezeli true rysowane jest tlo
     */
    boolean czyRysowacTlo = true;
    /**
     * Jezeli true, to rysuje TitleScreen.jpg (tylko przy pierwszym wlaczeniu)
     */
    boolean czyPierwszyRaz = true;

    /**
     * nanosi tla oraz kwadraty
     * @param g element do narysowania;
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(czyPierwszyRaz){
            Poziomy.tlo = new ImageIcon("images/TitleScreen.jpg");
            czyPierwszyRaz = false;
        }

        Image tlo2 = Poziomy.tlo.getImage();
        g.drawImage(tlo2, 0, 0, Okno.width, Okno.height, null);
        czyRysowacTlo = false;

        for (int i = 0; i < ileKwadratow; i++) {
            r = kwadraty.get(i);
            g.setColor(kolory.get(i));
            draw(g);
        }
    }

    /**
     * Wywoluje rysowanie pojedynczych kwadratow
     * @param g element do naniesienia
     */
    public void draw(Graphics g){
        g.fillRect(r.x, r.y, kwadWidth, kwadHeight);
    }

    /**
     * Przechowuje czy zostal nacisniety kwadrat
     */
    static boolean czyWcisnietyKwadrat = false;
    /**
     * Przechowuje czy uzytkownik wygral gre
     */
    static boolean czyWygrana = true;

    /**
     * Mouse Adapter, ktorego glownym zadaniem jest obsluga przenoszenia kwadratow na ekranie gry
     */
    public class ma extends MouseAdapter {

        /**
         * Sprawdzanie czy zostal nacisniety kwadrat
         * @param e wcisniecie myszy;
         */
        @Override
        public void mousePressed(MouseEvent e) {

            //Sprawdzanie czy nacisnieto na kwadrat
            //Jesli tak, nacisniety kwadrat i jego kolor sa przenoszone na konce list i petla jest przerywana
            for (int i = 0; i < GenerujKwadrat.ileKwadratow; i++) {
                if (e.getX() >= kwadraty.get(i).x && e.getX() <= kwadraty.get(i).x + kwadWidth){
                    if (e.getY() >= kwadraty.get(i).y && e.getY() <= kwadraty.get(i).y + kwadWidth){
                        czyWcisnietyKwadrat = true;
                        Rectangle temp = kwadraty.get(i);
                        kwadraty.remove(i);
                        kwadraty.add(temp);
                        Color c = kolory.get(i);
                        kolory.remove(i);
                        kolory.add(c);
                        i = ileKwadratow;
                    }
                }
            }
        }

        /**
         * Jezeli wcisniety zostal kwadrat, zostaje on przeciagniety
         * @param e przciaganie myszy;
         */
        //Przenoszenie ostatniego kwadratu z listy
        @Override
        public void mouseDragged(MouseEvent e) {
            if(czyWcisnietyKwadrat) {
                int x = e.getX();
                int y = e.getY();
                kwadraty.get(ileKwadratow - 1).setLocation(x, y);
                repaint();

            }
        }

        /**
         * Jezeli zostal upuszczony kwadrat, sprawdza czy jest w poprawnym kontenerze
         * @param e puszczenie przycisku myszy;
         */
        //Po puszczeniu przycisku sprwdzane jest czy kwadrat zostal upuszczony w kontenerze
        public void mouseReleased(MouseEvent e){
            if(czyWcisnietyKwadrat){
                CzyKwadratwKontenerze(e.getX(), e.getY());
                czyWcisnietyKwadrat = false;
            }
        }

        /**
         * Sprawdza czy kwadrat zostal umieszczony w jakimkolwiek kontenerze
         * @param ex wspolrzedna x kursora;
         * @param ey wspolrzedna y kursora;
         */
        //Metoda sprawdza czy kwadrat zostal upuszczony w kontenerze
        public void CzyKwadratwKontenerze(int ex, int ey){
            if (ey > 645){
                int ktoryKontener;

                if (ex >= 10 && ex <= 310){
                    ktoryKontener = 0;
                    CzyKwadratwKontenerzePom(ktoryKontener);
                }
                else if (ex >= 320 && ex <= 620){
                    ktoryKontener = 1;
                    CzyKwadratwKontenerzePom(ktoryKontener);
                }

                else if (ex >= 630 && ex <= 930){
                    ktoryKontener = 2;
                    CzyKwadratwKontenerzePom(ktoryKontener);
                }
                else if (ex >= 940 && ex <= 1240){
                    ktoryKontener = 3;
                    CzyKwadratwKontenerzePom(ktoryKontener);
                }

                //Jezeli zostal upuszczony ostatni kwadrat sprawdzane jest czy poziom zostal wygrany/przegrany
                if (ileKwadratow < 1) {
                    SprawdzenieWygranej();
                }
            }
        }

        /**
         * Metoda sprawdza czy kwadrat zostal umieszczony w poprawnym kontenerze
         * @param ktoryKontener kontener, w ktorym zostal umieszczony kwadrat;
         */
        //Metoda sprawdzajaca czy kwadrat zostal umieszczony w POPRAWNYM kontenerze
        //Jesli nie, poziom zostaje zakonczony
        void CzyKwadratwKontenerzePom(int ktoryKontener){
            if (kolory.get(ileKwadratow - 1).equals(Poziomy.dostepneKolory.get(ktoryKontener))) {
                kwadraty.remove(ileKwadratow - 1);
                kolory.remove(ileKwadratow - 1);
                ileKwadratow--;

                zd.play("sounds/kwadrat_w_kon.wav");

                czyWygrana = true;
            }
            else {
                GenerujKwadrat.kwadraty.removeAll(GenerujKwadrat.kwadraty);
                zd.play("sounds/przegrana.wav");
                ileKwadratow = 0;
                czyWygrana = false;
                SprawdzenieWygranej();
                repaint();
            }
        }
    }

    /**
     * Odtwarza dzwieki w grze
     */
    ZagrajDzwiek zd = new ZagrajDzwiek();

    /**
     * Sprawdza czy uzytkownik wygral poziom
     */
    public void SprawdzenieWygranej(){

        GenerujKwadrat.kwadraty.removeAll(GenerujKwadrat.kwadraty);
        GenerujKwadrat.kolory.removeAll(GenerujKwadrat.kolory);
        Poziomy.dostepneKolory.removeAll(Poziomy.dostepneKolory);

        if (czyWygrana) {
            Poziomy.ktoryPoziom++;

            if (Poziomy.ktoryPoziom <= Poziomy.maxPoziom){
                zd.play("sounds/przejscie_poziomu.wav");
                Okno.poziomObecny.setText("OBECNY POZIOM: " + Poziomy.ktoryPoziom);
            }

            Okno.gra.repaint();

            Okno.gra.GenerujKoordynaty();

        } else if (!czyWygrana) {
            Okno.start.setEnabled(true);
            Okno.poziom.setEnabled(true);
            Okno.stop.setEnabled(false);
            Okno.wyjscie.setEnabled(true);
            CountdownTimer.czas = 0;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            Okno.poziomObecny.setText("OBECNY POZIOM: " + Okno.poziomPoczatkowy);

            ileKwadratow = 0;
            czyWygrana = false;
        }
    }
}