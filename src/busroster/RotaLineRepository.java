package busroster;

import java.util.ArrayList;
import java.util.Calendar;

public class RotaLineRepository {
    
    private ArrayList<RotaLine> rotaLines = new ArrayList<>();

    public RotaLineRepository(){
    }
    
    public RotaLineRepository(DB db){
        for (int i = 1; i < 10; i++) rotaLines.add(db.getRotaLine(i));
    }

    public void setRotaLines(ArrayList<RotaLine> rotaLines) {
        this.rotaLines = rotaLines;
    }

    public ArrayList<RotaLine> getRotaLines() {
        return rotaLines;
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
}
