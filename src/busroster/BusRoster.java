package busroster;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class BusRoster {
        
    public static void main(String[] args) {        
        DB myDataBase = new DB();                                               // adatbázis kapcsolat osztály példánya: myDataBase                                                      
        Calendar inputDate  = null;                                             // kiválasztott dátum példánya - üres

        //teszteleshez bemeno adatok fixen. sofor es celdatum
        String driverEmpNum = "3210";
        inputDate = new GregorianCalendar(2020,1,2);                            // tesztdátum kiválasztás direktben  ,.,.
        SwapRepository swaps = new SwapRepository(driverEmpNum, inputDate, myDataBase);

        if (swaps.doesItNeedASwap()){
            swaps.setSwaps();
            swaps.showThePossibilities();
        } else System.out.println("Szabadnap");
    }
}


