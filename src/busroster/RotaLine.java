package busroster;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class RotaLine{
    private static final GregorianCalendar STARTDATE = new GregorianCalendar(2020, 0, 5);
    private ArrayList<String> rotaLine;
    private int rotaLineNumber;
    private final String DAYOFFDUTYNUMBER = "1001";
    
    public RotaLine(){
    }
    
    public RotaLine(int rotaLineNumber, ArrayList<String> rotaLine){
        this.rotaLine = rotaLine;
        this.rotaLineNumber = rotaLineNumber;
    }
    
    public static GregorianCalendar getSTARTDATE() {
        return STARTDATE;
    }
    
    public ArrayList<Integer> getDayOffDays(){
        ArrayList<Integer> dayOffDays = new ArrayList<>();
        int dayNumber = 0;
        for (String dutyNumberOfDay : rotaLine){
            dayNumber++;
            if (dutyNumberOfDay.equals(DAYOFFDUTYNUMBER))
                    dayOffDays.add(dayNumber);                
        }
        return dayOffDays;
    }

    public boolean dayOff(int dayOfWeek){
        boolean dayOff = false;
        if (rotaLine.get(dayOfWeek-1).equals(DAYOFFDUTYNUMBER))
            dayOff = true;
        return dayOff;
    }

    public String getDutyNumberFromDayNumber(int day){                
        return rotaLine.get(day-1);
    }
    
    public int getRotaLine() {
        return rotaLineNumber;
    }    
}
