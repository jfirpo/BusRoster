package busroster;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Swapper {
    private ArrayList<Swap> swaps;
    private ArrayList<Driver> drivers;
    private ArrayList<RotaLine> theRota;
    private int actualLine;
    private int dayOfInputWeek;                        
    private Calendar inputDate;
    private DB myDataBase;                                               

    private Swapper(){
    }
    
    public Swapper(String driverEmpNum, Calendar inputDate){
        myDataBase = new DB();
        swaps = new ArrayList<>();
        this.drivers = myDataBase.getAllDrivers();
        this.theRota = myDataBase.getAllRotaLines();    
        this.inputDate = inputDate;
        this.actualLine = countOfActualLine(inputDate, getDriverByEmployeeNumber(driverEmpNum));             
        this.dayOfInputWeek = inputDate.get(GregorianCalendar.DAY_OF_WEEK);    
    }
        
    public boolean doesItNeedASwap(){    
        return (!isRestDay(actualLine, dayOfInputWeek));}
    
    public void setSwaps(){
        ArrayList<Integer>  possibleSwapLines = calcDayOffOnOtherLines(actualLine, dayOfInputWeek); 
        ArrayList<Integer> dayOffOnActual  = theRota.get(actualLine-1).getDayOffDays();               
        for(int i = 0; i < possibleSwapLines.size(); i++){               
            for (int j = 0; j<2;j++){
                if (!theRota.get(possibleSwapLines.get(i)).getDayOffDays().contains(dayOffOnActual.get(j))){                    
                    swaps.add(new Swap(theRota.get(possibleSwapLines.get(i)).getRotaLine(), dayOffOnActual.get(j),
                            theRota.get(possibleSwapLines.get(i)).getDutyNumberFromDayNumber(dayOffOnActual.get(j)),
                            whoWillWorkOnThePossibleSwapLine(1+possibleSwapLines.get(i), inputDate).getName()));
                }
            }
        }
    }

    public void showThePossibilities(){
        for (int i = 0; i < swaps.size();i++){
            System.out.print("Cserenap rota sora: " + swaps.get(i).getPossibleSwapLine() + ". sor || ");
            System.out.print("Lehetseges cserenap: " + swaps.get(i).getPossibleSwapDay() + ". nap || ");
            System.out.print("Lehetseges csere duty: " + swaps.get(i).getDutyNumberOnpossibleSwapDay());
            System.out.println(" || Sofor az adott soron: " + swaps.get(i).getPossibleSwapperDriverName());

        }
    }
    
    private Driver getDriverByEmployeeNumber(String empNum){            
        Driver searchedDriver = null;
        for (int i = 0; i<drivers.size();i++){
            if(empNum.equals(drivers.get(i).getEmployeeNumber()))
                searchedDriver = drivers.get(i);
        }
    return searchedDriver;
    }
    
    private Driver whoWillWorkOnThePossibleSwapLine(int rotaLine, Calendar inputDate){
        Driver searchedDriver = null;
        for (int i = 0; i<drivers.size();i++){
            if(rotaLine == countOfActualLine(inputDate, drivers.get(i)))
                searchedDriver = drivers.get(i);
        }           
        return searchedDriver;
    }    

    private ArrayList<Integer> calcDayOffOnOtherLines(int actualLine, int dayOfWeek){
        ArrayList<Integer> isDayOffonTargetDay = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            if (actualLine == 9) 
                actualLine = 0;
            if (theRota.get(actualLine).getDayOffDays().contains(dayOfWeek)) 
                isDayOffonTargetDay.add(actualLine);
            actualLine++;
        }
        return isDayOffonTargetDay;
    }

    private boolean isRestDay(int rotaLine, int dayOfWeek){
        boolean isRestDay = false;
        if (theRota.get(--rotaLine).dayOff(dayOfWeek))
            isRestDay = true;
        return isRestDay;}
    
    private RotaLine getRotaLine(int rotaLine){
        RotaLine searchedRotaLine = null;
        for(int i = 0; i < theRota.size(); i++){
            if (this.theRota.get(i).getRotaLine() == rotaLine)
                searchedRotaLine = theRota.get(i);
        }    
    return searchedRotaLine;}

    private int countOfActualLine(Calendar inputDate, Driver driver){                                
	int actualLine1 = driver.getStartLine();
        for (int i = 1; i <= countDifferenceOfWeeks(inputDate); i++) {
            actualLine1++;
		if (actualLine1 == 10)
                    actualLine1 = 1;		
	}	
        return actualLine1;
    }

    private long countDifferenceOfWeeks(Calendar inputDate) {        
        LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(RotaLine.getSTARTDATE().getTimeInMillis()), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(inputDate.getTimeInMillis()), ZoneId.systemDefault());                
        return ChronoUnit.WEEKS.between(startDate, endDate);
    }    
}
