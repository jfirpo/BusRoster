package busroster;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Driver {
    
    private int startLine, actualLine;
    private String name;
    private String employeeNumber;

    public Driver(){
        
    }
    
    public Driver(String name, String employeeNumber, int startLine){
        this.startLine = startLine;
        this.name = name;
        this.employeeNumber = employeeNumber;
    }
    
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    public int getStartLine() {
        return startLine;
    }
    
    public int countOfActualLine(Calendar inputDate){                
                GregorianCalendar rotaStart;
                rotaStart = RotaLine.getSTARTDATE();
                Instant d1i = Instant.ofEpochMilli(rotaStart.getTimeInMillis());
                Instant d2i = Instant.ofEpochMilli(inputDate.getTimeInMillis());
                LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
                LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());
                long weeksOfDifference =  ChronoUnit.WEEKS.between(startDate, endDate);
                                    // a megadott datum hetenek kiszamolasa
		actualLine = this.startLine;		
                for (int i = 1; i <= weeksOfDifference; i++) {
			actualLine++;
			if (actualLine>=10) {
				actualLine = 1;
			}
		}	
		//megalapitja, hogy az adott naptari nap, milyen napra esik a heten
return actualLine;
}
        
    public void setEmpNumKeyb(){
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Employee numbers: 3210-3218");
                System.out.println("Kerem az employee number-t: ");
                String employeeNumbe2 = keyboard.next();
                this.employeeNumber = employeeNumbe2;
    }
}
