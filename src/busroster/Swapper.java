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
    private ArrayList<RotaLine> rotaLines;
    int actualLine;
    int dayOfInputWeek;                        
    private Calendar inputDate;    

    private Swapper(){
    }
    
    public Swapper(String driverEmpNum, Calendar inputDate, DB db){
        swaps = new ArrayList<>();
        this.drivers = db.getAllDrivers();
        this.rotaLines = db.getAllRotaLines();    
        this.inputDate = inputDate;
        this.actualLine = countOfActualLine(inputDate, getDriverByEmployeeNumber(driverEmpNum));             
        this.dayOfInputWeek = inputDate.get(GregorianCalendar.DAY_OF_WEEK);
        
    }
        
    public boolean doesItNeedASwap(){    
        return (!isRestDay(actualLine, dayOfInputWeek));}
    
    public void setSwaps(){
        ArrayList<Integer> dayOffOnActual  = rotaLines.get(actualLine).getDayOffDays();       
        ArrayList<Integer>  toCheckSwapLines = dayOffList(actualLine, dayOfInputWeek); 
        int possibleSwapDay, possibleSwapLine;
        String dutyNumberOnpossibleSwapDay, possibleSwapperDriverName;
        Driver driverOfPossibleSwapLine;
            for(int i = 0; i < toCheckSwapLines.size(); i++){
                driverOfPossibleSwapLine = whoWillWorkOnThePossibleSwapLine(1+toCheckSwapLines.get(i), inputDate);
                for (int j = 0; j<2;j++){
                    if (!rotaLines.get(toCheckSwapLines.get(i)).getDayOffDays().contains(dayOffOnActual.get(j))){
                        possibleSwapLine = rotaLines.get(toCheckSwapLines.get(i)).getRotaLine();
                        possibleSwapDay = dayOffOnActual.get(j);
                        dutyNumberOnpossibleSwapDay = rotaLines.get(toCheckSwapLines.get(i)).getDutyNumberFromDayNumber(dayOffOnActual.get(j));
                        possibleSwapperDriverName = driverOfPossibleSwapLine.getName();
                        this.swaps.add(new Swap(possibleSwapLine, possibleSwapDay, dutyNumberOnpossibleSwapDay, possibleSwapperDriverName));
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

    public ArrayList<Integer> dayOffList(int actualLine, int dayOfWeek){
        ArrayList<Integer> isDayOff = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            if (actualLine == 9) actualLine = 0;
            if (rotaLines.get(actualLine).getDayOffDays().contains(dayOfWeek)) 
                isDayOff.add(actualLine);
            actualLine++;
        }
    return isDayOff;
    }

    public boolean isRestDay(int rotaLine, int dayOfWeek){
        boolean isRestDay = false;
        if (rotaLines.get(--rotaLine).dayOff(dayOfWeek)) isRestDay = true;
    return isRestDay;}
    
    public RotaLine getRotaLine(int rotaLine){
        RotaLine searchedRotaLine = null;
        for(int i = 0; i < rotaLines.size(); i++){
            if (this.rotaLines.get(i).getRotaLine() == rotaLine)
                searchedRotaLine = rotaLines.get(i);
        }    
    return searchedRotaLine;}

    public int countOfActualLine(Calendar inputDate, Driver driver){                                
	int actualLine = driver.getStartLine();
        for (int i = 1; i <= countDifferenceOfWeeks(inputDate); i++) {
            actualLine++;
		if (actualLine == 10)
                    actualLine = 1;		
	}	
        return actualLine;
    }

    private long countDifferenceOfWeeks(Calendar inputDate) {
        GregorianCalendar rotaStart = RotaLine.getSTARTDATE();
        Instant d1i = Instant.ofEpochMilli(rotaStart.getTimeInMillis());
        Instant d2i = Instant.ofEpochMilli(inputDate.getTimeInMillis());
        LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());                
        return ChronoUnit.WEEKS.between(startDate, endDate);
    }    
}
