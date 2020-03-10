package busroster;


public class Driver {
    
    private int startLine;
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
    
}
