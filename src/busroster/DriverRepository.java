package busroster;
import java.util.ArrayList;
import java.util.Calendar;

public class DriverRepository {
    private ArrayList<Driver> drivers = new ArrayList<>();

    
    public DriverRepository(){
    }

    public DriverRepository(DB db){        
        drivers = db.getAllDrivers();
    }

    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }
    
    public Driver whoWillWork(int rotaLine, Calendar inputDate){
        Driver driver = drivers.get(0);
           for (int i = 0; i<drivers.size();i++){
               if(rotaLine == drivers.get(i).countOfActualLine(inputDate))
                   driver = drivers.get(i);
           }           
        return driver;}    
}
