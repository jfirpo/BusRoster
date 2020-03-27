package busroster;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class BusRoster {
        
    public static void main(String[] args) {        
        DB myDataBase = new DB();                                               // adatbázis kapcsolat osztály példánya: myDataBase                                                   
        Calendar inputDate  = null;                                             // kiválasztott dátum példánya - üres
        Driver driver = new Driver();                                           // sofőr osztály példánya - üres
        //driver = myDataBase.getDriver(driver.getEmployeeNumber());            // a bekérő sofőr azpnpsítása
        //inputDate = setInputDate();                                           // a kerdeses datum bekerese
        //teszteleshez bemeno adatok fixen. sofor es celdatum
        driver = myDataBase.getDriver("3210");                                  // tesztsofőr - kiválasztás direktben
        inputDate = new GregorianCalendar(2020,1,2);                            // tesztdátum kiválasztás direktben  ,.,.
        SwapRepository swaps = new SwapRepository(driver, inputDate, myDataBase);

        if (swaps.doesItNeedASwap()){
            swaps.setSwaps();
            swaps.showThePossibilities();
        } else System.out.println("Szabadnap");
    }
}


