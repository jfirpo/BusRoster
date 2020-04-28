package busroster;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class RotaLine {

    private String sundayDn, mondayDn, tuesdayDn, wednesdayDn, thursdayDn, fridayDn, saturdayDn;
    private int rotaLine;
    private static final GregorianCalendar STARTDATE = new GregorianCalendar(2020, 0, 5);

    public RotaLine(){
    }

    public RotaLine(int rotaLine, String sundayDn, String mondayDn, String tuesdayDn, 
        String wednesdayDn, String thursdayDn, String fridayDn, String saturdayDn){
        this.rotaLine = rotaLine;
        this.fridayDn = fridayDn;
        this.mondayDn = mondayDn;
        this.saturdayDn = saturdayDn;
        this.sundayDn = sundayDn;
        this.thursdayDn = thursdayDn;
        this.tuesdayDn = tuesdayDn;
        this.wednesdayDn = wednesdayDn;        
    }

    public static GregorianCalendar getSTARTDATE() {
        return STARTDATE;
    }

    public String getSundayDn() {
        return sundayDn;
    }

    public String getMondayDn() {
        return mondayDn;
    }

    public String getTuesdayDn() {
        return tuesdayDn;
    }

    public String getWednesdayDn() {
        return wednesdayDn;
    }

    public String getThursdayDn() {
        return thursdayDn;
    }

    public String getFridayDn() {
        return fridayDn;
    }

    public String getSaturdayDn() {
        return saturdayDn;
    }

    public int getRotaLine() {
        return rotaLine;
    }

    public void setSundayDn(String sundayDn) {
        this.sundayDn = sundayDn;
    }

    public void setMondayDn(String mondayDn) {
        this.mondayDn = mondayDn;
    }

    public void setTuesdayDn(String tuesdayDn) {
        this.tuesdayDn = tuesdayDn;
    }

    public void setWednesdayDn(String wednesdayDn) {
        this.wednesdayDn = wednesdayDn;
    }

    public void setThursdayDn(String thursdayDn) {
        this.thursdayDn = thursdayDn;
    }

    public void setFridayDn(String fridayDn) {
        this.fridayDn = fridayDn;
    }

    public void setSaturdayDn(String saturdayDn) {
        this.saturdayDn = saturdayDn;
    }

    public void setRotaLine(int rotaLine) {
        this.rotaLine = rotaLine;
    }
    
    public ArrayList<Integer> getDayOffDays(){
        int i = 1;
        ArrayList<Integer> dayOffDays = new ArrayList<>();
        if (this.sundayDn.equals("1001")) dayOffDays.add(i);
        i++;
        if (this.mondayDn.equals("1001")) dayOffDays.add(i);
        i++;
        if (this.tuesdayDn.equals("1001")) dayOffDays.add(i);
        i++;
        if (this.wednesdayDn.equals("1001")) dayOffDays.add(i);
        i++;
        if (this.thursdayDn.equals("1001")) dayOffDays.add(i);
        i++;
        if (this.fridayDn.equals("1001")) dayOffDays.add(i);
        i++;
        if (this.saturdayDn.equals("1001")) dayOffDays.add(i);                
       return dayOffDays;
    }

    public boolean dayOff(int dayOfWeek){
        boolean dayOff = false;
            switch (dayOfWeek) {
             case 1: if (this.sundayDn.equals("1001")) dayOff = true; break;
             case 2: if (this.mondayDn.equals("1001")) dayOff = true; break;
             case 3: if (this.tuesdayDn.equals("1001")) dayOff = true; break;
             case 4: if (this.wednesdayDn.equals("1001")) dayOff = true; break;
             case 5: if (this.thursdayDn.equals("1001")) dayOff = true; break;
             case 6: if (this.fridayDn.equals("1001")) dayOff = true; break;
             case 7: if (this.saturdayDn.equals("1001")) dayOff = true; break;
            }        
            return dayOff;
    }

    public String getDutyNumberFromDayNumber(int day){
        String dutyNumber = null;
        if (day == 1) dutyNumber = this.sundayDn;
        if (day == 2) dutyNumber = this.mondayDn;
        if (day == 3) dutyNumber = this.tuesdayDn;
        if (day == 4) dutyNumber = this.wednesdayDn;
        if (day == 5) dutyNumber = this.thursdayDn;
        if (day == 6) dutyNumber = this.fridayDn;
        if (day == 7) dutyNumber = this.saturdayDn;
    return dutyNumber;}
}
