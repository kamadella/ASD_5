package com.company;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;


//sasiedzi wierzcholkow
class Somsiedzi {
    public int wierzcholek;
    public int waga;

    public Somsiedzi(int wierzcholek, int waga) {
        this.wierzcholek = wierzcholek;
        this.waga = waga;
    }
    @Override
    public String toString() {
        return "Somsiad [wierzcholek=" + wierzcholek + ", waga=" + waga + "]";
    }
}



//tu porownywanie dla kolejki priorytetowej zeby mniejszy indeks szedl pierwszy
class HipermiastaComparator implements Comparator<Hipermiasta> {
    public int compare(Hipermiasta h1, Hipermiasta h2) {
        if (h1.waga < h2.waga)
            return -1;
        else if (h1.waga > h2.waga)
            return 1;
        return 0;
    }
}


//to sa wierzcholki grafu
    class Hipermiasta {
    public int wierzcholek;
    public int ile_tuneli_uzylismy;
    public int waga;

    public Hipermiasta(int wierzcholek, int ile_tuneli_uzylismy, int waga) {
        this.wierzcholek = wierzcholek;
        this.ile_tuneli_uzylismy = ile_tuneli_uzylismy;
        this.waga = waga;
    }

    @Override
    public String toString() {
        return "Hipermiasto [wierzcholek=" + wierzcholek + ", ilosc tuneli=" + ile_tuneli_uzylismy + ", waga=" + waga + "]";
    }
}



//-------------------------------------------------ZADANIE---------------------------------------------------
public class Zadanie5 {
    int liczba_hipermiast, liczba_korytarzy, liczba_tuneli;
    ArrayList< ArrayList<Somsiedzi> > lista_incydencji;
    int[][] d; //Tablica odleglosci d
    int[][] p; //Tablica poprzedników


    Zadanie5() {
        Odczyt_Pliku();
        //Wypisywanie_Sasiadow();
        long start = System.currentTimeMillis(); // zaczyna mierzyć czas
        znajdowanie_Trasy();
        Zapis_do_Pliczku();
        long stop = System.currentTimeMillis(); // zatrzymuje pomiar czasu
        System.out.println("\nCzas wykonania:" + (stop - start) + " milisekund");
    }


    //-------------------------------------------------ODCZYT Z PLIKU--------------------------------------------------
    private void Odczyt_Pliku(){
        int m1, m2, waga;

        try{
            File pliczek_wejsciowy = new File("sredni1_2_in.txt");
            Scanner odczyt_z_pliku = new Scanner(pliczek_wejsciowy);

            this.liczba_hipermiast = odczyt_z_pliku.nextInt(); //liczba wierzchołków
            this.liczba_korytarzy = odczyt_z_pliku.nextInt(); //liczba korytarzy (połączeń)
            this.liczba_tuneli = odczyt_z_pliku.nextInt();
            lista_incydencji = new ArrayList<>(liczba_hipermiast);

            for (int i = 0; i < liczba_hipermiast; i++) {
                lista_incydencji.add(new ArrayList<>());
            }

            for (int i=0; i<liczba_korytarzy; i++){
                m1 = odczyt_z_pliku.nextInt();       //1 miasto
                m2 = odczyt_z_pliku.nextInt();       //2 miasto
                waga = odczyt_z_pliku.nextInt();    //waga
                //zakladam że graf nie skierowany wiec musze dodać do miasta1 miasto2, a dla miasta2 dopisać miasto1
                lista_incydencji.get(m1 - 1).add(new Somsiedzi(m2 - 1, waga));
                lista_incydencji.get(m2 - 1).add(new Somsiedzi(m1 - 1, waga));
            }

            //System.out.println(liczba_hipermiast +" "+ liczba_korytarzy +" "+ liczba_tuneli);
            odczyt_z_pliku.close();
        } catch (FileNotFoundException fne){
            System.out.println("Podano zły pliczek - nie widzę go :(");
            System.exit(-1);
        }
    }


    //-------------------------------------------------WYPISYWANIE SASIADOW------------------------------------------------
    private void Wypisywanie_Sasiadow(){
        // pamietaj że hipermiasta zaczynają się od 0 a nie od 1!
        for(int i=0; i<liczba_hipermiast ; i++){
            for (Somsiedzi sasiad  : lista_incydencji.get(i)) {
                System.out.println( i +" "+ sasiad.wierzcholek +" "+ sasiad.waga);
            }
        }
    }


    //-------------------------------------------------ZNAJDOWANIE TRASY--------------------------------------------------
    private void znajdowanie_Trasy(){
        d = new int[liczba_tuneli + 1][liczba_hipermiast]; //Tablica odleglosci d
        p = new int[liczba_tuneli + 1][liczba_hipermiast]; //Tablica poprzednikow p
        PriorityQueue<Hipermiasta> kolejka_priorytetowa = new PriorityQueue<Hipermiasta>(new HipermiastaComparator()); //Kolejka priorytetowa


        for (int i = 0; i <= liczba_tuneli; i++) { //dla tablicy drogi
            for (int j = 0; j < liczba_hipermiast; j++) {
                if (j == 0) { d[i][j] = 0; }//zero w pierwszej kolumnie
                else { d[i][j] = Integer.MAX_VALUE; } //nieskonczonosc w pozostalych
            }
        }

        Hipermiasta hipermiasto1 = new Hipermiasta(0, 0, 0);
        kolejka_priorytetowa.add(hipermiasto1); //Wierzchołek 1 od ktorego zaczynamy

        while (!kolejka_priorytetowa.isEmpty()) { //Dopóki jest jakiś wierzchołek w kolejce

            Hipermiasta hipermiasto = kolejka_priorytetowa.poll(); //zdejmuje wierzchołek nieodwiedzony o najmniejszej odległości d

            //System.out.println(hipermiasto);

            for (Somsiedzi sasiad  : lista_incydencji.get(hipermiasto.wierzcholek)) { //Dla kazdego hipermiasta sprawdzamy sąsiadow
                //System.out.println(sasiad); //obczajka somsiadow

                //Dijkstra strona 13 WYKLAD 10
                if (d[hipermiasto.ile_tuneli_uzylismy][hipermiasto.wierzcholek] + sasiad.waga < d[hipermiasto.ile_tuneli_uzylismy][sasiad.wierzcholek] && sasiad.waga != 0) {
                    d[hipermiasto.ile_tuneli_uzylismy][sasiad.wierzcholek] = d[hipermiasto.ile_tuneli_uzylismy][hipermiasto.wierzcholek] + sasiad.waga;
                    int nowa_waga = d[hipermiasto.ile_tuneli_uzylismy][sasiad.wierzcholek];

                    p[hipermiasto.ile_tuneli_uzylismy][sasiad.wierzcholek] = hipermiasto.wierzcholek;

                    Hipermiasta temp = new Hipermiasta(sasiad.wierzcholek, hipermiasto.ile_tuneli_uzylismy, nowa_waga);

                    if (!kolejka_priorytetowa.contains(temp)) kolejka_priorytetowa.add(temp);
                }

                //W przypadku tunelu (waga 0), jezeli jeszcze mozemy go uzyc
                else if (sasiad.waga == 0 && hipermiasto.ile_tuneli_uzylismy < liczba_tuneli) {
                    //sprawdzam czy sie oplaca wziecie tunelu
                    if (d[hipermiasto.ile_tuneli_uzylismy][hipermiasto.wierzcholek] < d[hipermiasto.ile_tuneli_uzylismy + 1][sasiad.wierzcholek]) {
                        //+1 bo wykorzystujemy tunel
                        d[hipermiasto.ile_tuneli_uzylismy + 1][sasiad.wierzcholek] = d[hipermiasto.ile_tuneli_uzylismy][hipermiasto.wierzcholek];
                        int nowa_waga = d[hipermiasto.ile_tuneli_uzylismy + 1][sasiad.wierzcholek];

                        //-1 żeby szło to później odczytać
                        p[hipermiasto.ile_tuneli_uzylismy + 1][sasiad.wierzcholek] = hipermiasto.wierzcholek * (-1);

                        Hipermiasta temp = new Hipermiasta(sasiad.wierzcholek, hipermiasto.ile_tuneli_uzylismy + 1, nowa_waga);

                        //dodanie na kolejke
                        if (!kolejka_priorytetowa.contains(temp)) kolejka_priorytetowa.add(temp);
                    }
                }
            }
        }
    }



    //-------------------------------------------------ZAPIS DO PLIKU--------------------------------------------------
    private void Zapis_do_Pliczku(){
        /*
        // wypisywanie drogi
        System.out.println();
        System.out.println("Droga");
        for(int i =0; i<=liczba_tuneli; i++){
            for (int j=0;j<liczba_hipermiast;j++){
                System.out.printf("%12d",d[i][j]);
            }
            System.out.println();
        } System.out.println();

        // wypisywanie poprzednikow
        System.out.println("Poprzednicy");
        for(int i =0; i<=liczba_tuneli; i++){
            for (int j=0;j<liczba_hipermiast;j++){
                System.out.printf("%12d",p[i][j] +1);
            }
            System.out.println();
        } System.out.println();
        */


        int najkrotsza_droga = d[liczba_tuneli][liczba_hipermiast - 1]; //bierzemy wynik z ostatniej koolumny
        int linia = liczba_tuneli;

        String trasa = String.valueOf(liczba_hipermiast);
        int poprzednik = p[linia][liczba_hipermiast - 1];

        do{
            if (poprzednik < 0){
                linia = linia -1; //uzylismy tutaj tunelu wiec trzeba zejsc o poziom nizej
                poprzednik = poprzednik *(-1);
            }
            trasa = poprzednik + 1 + " " + trasa;
            poprzednik = p[linia][poprzednik];

            if (poprzednik == 0){
                trasa = poprzednik+1 + " " + trasa;
            }
        }while (poprzednik !=0);


        System.out.println(najkrotsza_droga);
        System.out.println(trasa);

        try {
            PrintWriter zapis_do_pliku = new PrintWriter("out.txt");
            zapis_do_pliku.println(najkrotsza_droga);
            zapis_do_pliku.println(trasa);
            zapis_do_pliku.close();
        } catch (IOException x) {
            System.out.println("blad zapisu wyniku- próbuj dalej");
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        Zadanie5 obiekt = new Zadanie5();
    }
}
