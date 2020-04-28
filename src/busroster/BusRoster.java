package busroster;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class BusRoster {
        
    public static void main(String[] args) {        
        DB myDataBase = new DB();                                               
        Calendar inputDate  = null;                                             


        String driverEmpNum = "3210";
        inputDate = new GregorianCalendar(2020,1,2);                           

        Swapper swaps = new Swapper(driverEmpNum, inputDate, myDataBase);
        if (swaps.doesItNeedASwap()){
            swaps.setSwaps();
            swaps.showThePossibilities();
        } else System.out.println("Szabadnap");
        
        System.out.println();
    }
}


