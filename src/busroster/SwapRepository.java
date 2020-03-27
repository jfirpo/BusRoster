package busroster;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class SwapRepository {
    ArrayList<Swap> swaps = new ArrayList<>();

    private int possibleSwapLine, possibleSwapDay;
    private String dutyNumberOnpossibleSwapDay, possibleSwapperDriverName;

    private RotaLineRepository rlRep;                                                   //rotalina kezelő osztály példánya
    private int dayOfInputWeek, actualLine;                                     // a kiválasztott dátum, milyen napra esik (Vasárnap-Szombat 1-7)    
    private Driver driverOfPossibleSwapLine;
    private DriverRepository drivers;
    private Calendar inputDate;
    private ArrayList<Integer> dayOffOnActual = new ArrayList<>();
    private RotaLine rl;
    
    public SwapRepository(){
    }
    
    public SwapRepository(Driver driver, Calendar inputDate, DB db){
        this.inputDate = inputDate;
        this.actualLine = driver.countOfActualLine(inputDate);                   // az azonosított sofőr, melyik soron dolgozik a választott dátumon (1-9)
        this.rlRep = new RotaLineRepository(db);          //rotalina kezelő osztály példánya
        this.rl = db.getRotaLine(actualLine);                                // a leendő sor példányosítása a vizsgálatokhoz
        this.dayOffOnActual = rl.getDayOffDays();                                    // a celheten levo szabadnapjaink            
        this.dayOfInputWeek = inputDate.get(GregorianCalendar.DAY_OF_WEEK);      // a kiválasztott dátum, milyen napra esik (Vasárnap-Szombat 1-7)
        this.drivers = new DriverRepository(db);
    }
    
    public void showThePossibilities(){
        for (int i = 0; i < swaps.size();i++){
            System.out.print("Cserenap rota sora: " + swaps.get(i).getPossibleSwapLine() + ". sor || ");
            System.out.print("Lehetseges cserenap: " + swaps.get(i).getPossibleSwapDay() + ". nap || ");
            System.out.print("Lehetseges csere duty: " + swaps.get(i).getDutyNumberOnpossibleSwapDay());
            System.out.println(" || Sofor az adott soron: " + swaps.get(i).getPossibleSwapperDriverName());
        }
    }
    
    public boolean doesItNeedASwap(){    
        return (!rlRep.isRestDay(actualLine, dayOfInputWeek));}
    
    public void setSwaps(){
                    ArrayList<Integer>  toCheckSwapLines = rlRep.dayOffList(actualLine, dayOfInputWeek);    // hetek amin szabadnap van a keresett datumon                                   
            for(int i = 0; i < toCheckSwapLines.size(); i++){
                  driverOfPossibleSwapLine = drivers.whoWillWork(1+toCheckSwapLines.get(i), inputDate);
                  for (int j = 0; j<2;j++){
                      if (!rlRep.getRotaLines().get(toCheckSwapLines.get(i)).getDayOffDays().contains(dayOffOnActual.get(j))){
                          possibleSwapLine = rlRep.getRotaLines().get(toCheckSwapLines.get(i)).getRotaLine();
                          possibleSwapDay = dayOffOnActual.get(j);
                          dutyNumberOnpossibleSwapDay = rlRep.getRotaLines().get(toCheckSwapLines.get(i)).getDutyNumberFromDayNumber(dayOffOnActual.get(j));
                          possibleSwapperDriverName = driverOfPossibleSwapLine.getName();
                          this.swaps.add(new Swap(possibleSwapLine, possibleSwapDay, dutyNumberOnpossibleSwapDay, possibleSwapperDriverName));
                      }
                   }
             }
    }
}
