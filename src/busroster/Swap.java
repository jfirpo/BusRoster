package busroster;

public class Swap {

    private int possibleSwapLine, possibleSwapDay;
    private String dutyNumberOnpossibleSwapDay, possibleSwapperDriverName;

    public Swap(){
    }
    
    public Swap(int possibleSwapLine, int possibleSwapDay, String dutyNumberOnpossibleSwapDay, String possibleSwapperDriverName){
        this.dutyNumberOnpossibleSwapDay = dutyNumberOnpossibleSwapDay;
        this.possibleSwapDay = possibleSwapDay;
        this.possibleSwapLine = possibleSwapLine;
        this.possibleSwapperDriverName = possibleSwapperDriverName;        
    }

    public int getPossibleSwapLine() {
        return possibleSwapLine;
    }

    public int getPossibleSwapDay() {
        return possibleSwapDay;
    }

    public String getDutyNumberOnpossibleSwapDay() {
        return dutyNumberOnpossibleSwapDay;
    }

    public String getPossibleSwapperDriverName() {
        return possibleSwapperDriverName;
    }

    public void setPossibleSwapLine(int possibleSwapLine) {
        this.possibleSwapLine = possibleSwapLine;
    }

    public void setPossibleSwapDay(int possibleSwapDay) {
        this.possibleSwapDay = possibleSwapDay;
    }

    public void setDutyNumberOnpossibleSwapDay(String dutyNumberOnpossibleSwapDay) {
        this.dutyNumberOnpossibleSwapDay = dutyNumberOnpossibleSwapDay;
    }

    public void setPossibleSwapperDriverName(String possibleSwapperDriverName) {
        this.possibleSwapperDriverName = possibleSwapperDriverName;
    }
}
