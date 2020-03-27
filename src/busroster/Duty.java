package busroster;

import java.time.LocalTime;

public class Duty {

    private LocalTime startTime, finishTime;
    private String dutyNumber;
    private boolean dayOff;
    
    public Duty(){
    }    

    public Duty(String dutyNumber, LocalTime startTime, LocalTime finishTime ){
        this.dutyNumber = dutyNumber;
        this.finishTime = finishTime;
        this.startTime = startTime;
        if (this.dutyNumber.equals("1001")) dayOff = true;
    }    

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public void setDutyNumber(String dutyNumber) {
        this.dutyNumber = dutyNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public String getDutyNumber() {
        return dutyNumber;
    }

}
