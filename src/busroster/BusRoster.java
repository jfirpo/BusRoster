package busroster;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class BusRoster {
        
    public static void main(String[] args) {        
        DB myDataBase = new DB();                                               // adatbázis kapcsolat osztály példánya: myDataBase                                                   
        DriverRepository drivers = new DriverRepository(myDataBase);
        
        Calendar inputDate  = null;                                             // kiválasztott dátum példánya - üres

        //driver = drivers.getDriver(driver.getEmployeeNumber());            // a bekérő sofőr azpnpsítása
        //inputDate = setInputDate();                                           // a kerdeses datum bekerese
        //teszteleshez bemeno adatok fixen. sofor es celdatum
        
        inputDate = new GregorianCalendar(2020,1,2);                            // tesztdátum kiválasztás direktben  ,.,.
        SwapRepository swaps = new SwapRepository(drivers.getDriver("3210"), inputDate, myDataBase);

        if (swaps.doesItNeedASwap()){
            swaps.setSwaps();
            swaps.showThePossibilities();
        } else System.out.println("Szabadnap");
        
    }
}


