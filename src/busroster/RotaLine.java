package busroster;

public class RotaLine {

    private String sundayDn, mondayDn, tuesdayDn, wednesdayDn, thursdayDn, fridayDn, saturdayDn;
    private int rotaLine;


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

}
