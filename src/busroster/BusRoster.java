package busroster;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BusRoster {
        
    public static void main(String[] args) {        
        String driverEmpNum = "3210";
        Calendar inputDate = new GregorianCalendar(2020,1,2);                           
        Swapper swaps = new Swapper(driverEmpNum, inputDate);
        if (swaps.doesItNeedASwap()){
            swaps.setSwaps();
            swaps.showThePossibilities();
        } else System.out.println("Szabadnap");        
        System.out.println();
    }
}


