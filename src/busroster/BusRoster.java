package busroster;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class BusRoster {
    
    public static Calendar inputDate  = null;
    public static Driver driver = null;
    public static int dayOfInputWeek, actualLine;
    public static ArrayList<Integer> possibleSwapLine = new ArrayList<>();
    public static ArrayList<Integer> dayOffOnActual = new ArrayList<>();
        
    public static void main(String[] args) {
        
        DB myDataBase = new DB();                                                   
        //driver = myDataBase.getDriver(BusRoster.setDriver());                 // driver bekerese es peldanyositasa
        //inputDate = setInputDate();                                           // a kerdeses datum bekerese

        //teszteleshez bemeno adatok fixen. sofor es celdatum
        driver = myDataBase.getDriver("3210"); 
        inputDate = new GregorianCalendar(2020,1,2);

        actualLine = BusRoster.countOfActualLine(driver.getStartLine(),inputDate);  // a celdatumkor aktualis sorunk
        dayOffOnActual = getDayOffOnActual(myDataBase);                             // a celheten levo szabadnapjaink
        dayOfInputWeek = inputDate.get(GregorianCalendar.DAY_OF_WEEK);              // a celdatum napja (1-7)
        possibleSwapLine = getPossibleSwapLine(myDataBase);                         // a celdatumon levo hetsorszamok, amin aznap szabadnap van
        
        showPossibleSwaps(myDataBase);                                              // Kiirja a cserelehetosegeket

        
        
        
     
    }
    
    public static boolean dayOff(int actualLine, int dayOfInputWeek, DB myDataBase){
        boolean dayOff =false;
        String theDay = myDataBase.getDutyNumber(actualLine, dayOfInputWeek);
        if ("1001".equals(theDay)) dayOff = true;
        
        return dayOff;
    }

    public static String setDriver(){
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Employee numbers: 3210-3218");
                System.out.println("Kerem az employee number-t: ");
                String employeeNumber = keyboard.next();
    return employeeNumber;}

    public static Calendar setInputDate(){
                    // datum bekerese
		Scanner keyboard = new Scanner(System.in);
		int year, month, day;
		System.out.println("Ev(xxxx): ");
		year =  keyboard.nextInt();
		System.out.println("Honap(1-12): ");
		month =  keyboard.nextInt();
		System.out.println("Nap: ");
		day =  keyboard.nextInt();
		keyboard.close();
		month--; //a honap indexeelese vegett..	
                Calendar inputDate = new GregorianCalendar(year, month, day);
                return inputDate;

    
    
    }    

    public static int countOfActualLine(int driverStartLine, Calendar inputDate){                
                Instant d1i = Instant.ofEpochMilli(new GregorianCalendar(2020, 0, 5).getTimeInMillis());
                Instant d2i = Instant.ofEpochMilli(inputDate.getTimeInMillis());
                LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
                LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());
                long weeksOfDifference =  ChronoUnit.WEEKS.between(startDate, endDate);

                                    // a megadott datum hetenek kiszamolasa
		actualLine = driverStartLine; // kijavitani, hogy tenylegesen valaszthato legyen, s sofor beazonositasa utan, erteket adni neki
		
                for (int i = 1; i <= weeksOfDifference; i++) {
			actualLine++;
			if (actualLine>=10) {
				actualLine = 1;
			}
		}	
		//megalapitja, hogy az adott naptari nap, milyen napra esik a heten
		// a napot kivinni!!!!
                
                System.out.println(" (contOfActualLine/BUSROSTER)  Az adott het sorszama: "+actualLine);

                

return actualLine;
}
    
    public static int getDriverOfThisLine(int rotaLine, Calendar inputDate){                
                Instant d1i = Instant.ofEpochMilli(new GregorianCalendar(2020, 0, 5).getTimeInMillis());
                Instant d2i = Instant.ofEpochMilli(inputDate.getTimeInMillis());
                LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
                LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());
                long weeksOfDifference =  ChronoUnit.WEEKS.between(startDate, endDate);
 
                for (int i = 1; i <= weeksOfDifference; i++) {
			rotaLine--;
			if (rotaLine <= 0) {
				rotaLine = 9;
			}
		}	
	        // rotaline a keresett sofor startline mezeje
                

                

return rotaLine;
}

    public static ArrayList<Integer> getPossibleSwapLine(DB myDataBase){
            
        // megnezi, hogy a valasztott napon, amennyiben munkanap, mely egyeb
        // sorokon van aznapra szabadnap, majd megvizsgalja, hogy van e olyan
        // szabadnapja a keresest vegzo sofornek, amire cserelheto a kivalsztott nap
        
        if(!dayOff(actualLine, dayOfInputWeek, myDataBase)){
            
            System.out.println("Munkanap");
            int check = actualLine;
            
            for (int i = 1; i < 9; i++){            
                if (dayOff(++check, dayOfInputWeek, myDataBase)){
                    System.out.println(myDataBase.getDutyNumber(check, dayOfInputWeek));
                    possibleSwapLine.add(check);                         
                }
                if (check > 8) check = 0;    
            }    
        } else {
            System.out.println("Szabadnapod van aznap.");
        }
        // possibleSwapLine - nevu ArrayList kollekcioba Integer formatumban
        // eltarolja a tobbi soron levo szabadnapot, az adott datumon
        //megnezi, hogy melyek a szabadnapjai a keresett datum hetenek
        //a csere megvalosithatosaga miatti vizsgalathoz kell
    return possibleSwapLine;
    }

    public static ArrayList<Integer> getDayOffOnActual(DB myDataBase){
        
        for (int i = 1; i < 8; i++){
            if (dayOfInputWeek != i) {
                if(dayOff(actualLine, i , myDataBase)){
                    dayOffOnActual.add(i);
                }
            }        
        }        
       return dayOffOnActual;}
    
    public static void showPossibleSwaps(DB myDataBase){
            int numOfPossibile = 0;
            String day = ""; 
            for (int i = 0; i < possibleSwapLine.size(); i++){                          // a lehetseges csere sorok szamanak megfeleloen fut le
            for (int j = 0; j < dayOffOnActual.size(); j++){                        // annyiszor megy vegig, ahany szabadnapom van
                if (!dayOff(possibleSwapLine.get(i), dayOffOnActual.get(j), myDataBase)){ // ha a cserelendo soron a szabadnapomon, munkanana[ van, akkor belep a ciklusba
                    //amire lehet: possibleSwapLine i=ik sor, dayOffOnActual j-ik nap
                    //akivel lehet: inputDate - napjan a possibleSwapLine i-ik soran levo sofor
                    if (dayOffOnActual.get(j) == 1) day = "vasarnap"; if (dayOffOnActual.get(j) == 2) day = "hetfo"; 
                    if (dayOffOnActual.get(j) == 3) day = "kedd"; if (dayOffOnActual.get(j) == 4) day = "szerda";
                    if (dayOffOnActual.get(j) == 5) day = "csutortok"; if (dayOffOnActual.get(j) == 6) day = "pentek";
                    if (dayOffOnActual.get(j) == 7) day = "szombat";    // ezt megcsinalom rendesen, ne vedd bele az erekelesbe, tudom, hogy proszto 
                    System.out.println(++numOfPossibile + ". lehetoseg: ");
                    System.out.println("Sofor neve: " + 
                            myDataBase.getDriver(getDriverOfThisLine(possibleSwapLine.get(i) , inputDate)).getName());
                    System.out.println("Nap: "  + day);
                    System.out.println("Duty: " + myDataBase.getDutyNumber(possibleSwapLine.get(i), dayOffOnActual.get(j)));
                     System.out.println("********************");
                }
            }
        } 
    
    }
}
    
