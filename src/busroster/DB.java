package busroster;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    public DB() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            //System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        // Megnézzük, létezik-e a DRIVERS adattábla. Ha nem, létrehozzuk.
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "DRIVERS", null);
            if(!rs.next())
            {
             createStatement.execute("create table drivers(Name varchar(20), Employeenumber varchar(20), Startline int)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
          }

        // Megnézzük, létezik-e a DUTYS adattábla. Ha nem, létrehozzuk.
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "DUTYS", null);
            if(!rs.next())
            {
             createStatement.execute("create table dutys(dutynumber varchar(8), starttime time, finishtime time)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az dutys adattábla létrehozásakor.");
            System.out.println(""+ex);
        }
        
        // Megnézzük, létezik-e a ROTALINE adattábla. Ha nem, létrehozzuk.
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "ROTALINE", null);
            if(!rs.next())
            {
             createStatement.execute("create table rotaline(rotalineumber varchar(2), sundaydutynumber varchar(6),"
                     + "mondaydutynumber varchar(6), tuesdaydutynumber varchar(6), wednesdaydutynumber varchar(6),"
                     + "thursdaydutynumber varchar(6), fridaydutynumber varchar(6), saturdaydutynumber varchar(6))");
             //String dutyNumber, LocalTime startTime, LocalTime finishTime
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az rotaline adattábla létrehozásakor.");
            System.out.println(""+ex);
        }        
    }
    
    // atad egy arralist objektumot a peldanyositott soforokkel
    public ArrayList<Driver> getAllDrivers(){
        String sql = "select * from drivers";
        ArrayList<Driver> drivers = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            drivers = new ArrayList<>();
            
            while (rs.next()){
                Driver actualDriver = new Driver(rs.getString("Name"),rs.getString("Employeenumber"), rs.getInt("Startline"));
                drivers.add(actualDriver);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a driverek kiolvasásakor");
            System.out.println(""+ex);
        }
      return drivers;
    }
    // a dutys tablabol visszaad minden dutyt egz arraylist objektumba
    public ArrayList<Duty> getAllDutys(){
        String sql = "select * from dutys";
        ArrayList<Duty> duties =  new ArrayList<>();
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()){
                String dutyNumber = rs.getString("Dutynumber");
                Time starttime = rs.getTime("Starttime");
                Time finishtime = rs.getTime("Finishtime");
                duties.add(new Duty(dutyNumber, starttime.toLocalTime(), finishtime.toLocalTime()));
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a driverek kiolvasásakor");
            System.out.println(""+ex);
        }
    return duties;
    }
    // a rotalines tablabol visszaad minden rotalinet egy arraylist objektumba
    public ArrayList<RotaLine> getAllRotaLines(){
        String sql = "select * from ROTALINE";
        ArrayList<RotaLine> rotaLines =  new ArrayList<>();
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()){ 
                ArrayList<String> rotaLine = new ArrayList<>();
                int rotaLineNumber = rs.getInt("rotalineumber");
                rotaLine.add(rs.getString("sundaydutynumber")); rotaLine.add(rs.getString("mondaydutynumber"));
                rotaLine.add(rs.getString("tuesdaydutynumber")); rotaLine.add(rs.getString("wednesdaydutynumber"));
                String thu = rs.getString("thursdaydutynumber"); rotaLine.add(rs.getString("fridaydutynumber"));
                rotaLine.add(rs.getString("saturdaydutynumber")); 
                RotaLine rotaLine1 = new RotaLine(rotaLineNumber, rotaLine);
                rotaLines.add(rotaLine1);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a rotalines kiolvasásakor");
            System.out.println(""+ex);
        }
    return rotaLines;
    }

    
    //********************************************
    //AZ ADATBAZIS KEZELESEHEZ SZUKSEGES METODUSOK
    //********************************************
    // sofor hozzaadasa az adattablahoz (sofor objektum altal)
    public void addDriver(Driver driver){
      try {
        String sql = "insert into drivers values (?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, driver.getName());
        preparedStatement.setString(2, driver.getEmployeeNumber());
        preparedStatement.setInt(3, driver.getStartLine());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a driver hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    // feltolti s soforok tablat, a filebol, az atadando parameter, a fileban talalhato soforok szama
    public void driversUpload(int numsOfDrivers){
            try {
          Scanner lemezOlvaso = new Scanner(new File("drivers.txt"));
                   Driver driver;		
                for (int i = 0; i < numsOfDrivers; i++){
                    driver = new Driver(lemezOlvaso.next(), 
                            lemezOlvaso.next(), lemezOlvaso.nextInt());
                    addDriver(driver);
                }
        lemezOlvaso.close();
                   
            }
        catch (FileNotFoundException ex) {
                        System.out.println("Valami gond van a soforok filejaval.." + ex);                   }
    }
    // feltolti s soforok tablat, a filebol, az atadando parameter, a fileban talalhato soforok szama
    public void dutysUpload(int numsOfDutys){
            try {
          Scanner lemezOlvaso = new Scanner(new File("dutysForDB.txt"));
                   Duty duty;		
                for (int i = 0; i < numsOfDutys; i++){
                    duty = new Duty(lemezOlvaso.next(), 
                           LocalTime.parse(lemezOlvaso.next() , DateTimeFormatter.ISO_TIME),LocalTime.parse(lemezOlvaso.next(), 
            DateTimeFormatter.ISO_TIME));
                            
                    addDuty(duty);
                }
        lemezOlvaso.close();
                   
            }
        catch (FileNotFoundException ex) {
                        System.out.println("Valami gond van a soforok filejaval.." + ex);                   }
    }
    //hozzaad a dutys tablahoz egy duty objektubombol adatokat
    public void addDuty(Duty duty){
      try {
        String sql = "insert into dutys values (?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, duty.getDutyNumber());
        preparedStatement.setString(2, duty.getStartTime().toString());
        preparedStatement.setString(3, duty.getFinishTime().toString());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a duty hozzáadásakor");
            System.out.println(""+ex);
        }
    } 
/*
    // feltolt filebol a megadott rotalinesszam alapján a fajlban, adatokat a rotalines tablaba
    public void rotaLinesUpload(int numsOfRotaLines){
            try {
          Scanner lemezOlvaso = new Scanner(new File("nirota.txt"));
                   RotaLine rotaLine;
                   for (int i = 0; i < numsOfRotaLines; i++){
                    rotaLine = new RotaLine((i+1) , 
                            lemezOlvaso.next(), lemezOlvaso.next(), lemezOlvaso.next(),
                            lemezOlvaso.next(), lemezOlvaso.next(), lemezOlvaso.next(),
                            lemezOlvaso.next());
                    addRotaLine(rotaLine);
                }
        lemezOlvaso.close();
                   
            }
        catch (FileNotFoundException ex) {
                        System.out.println("Valami gond van a soforok filejaval.." + ex);                   }
    }

    //egy adott rotaline objektum adatait tolti fel a rotalines adattáblába
    public void addRotaLine(RotaLine rotaLine){
      try {
        String sql = "insert into rotaline values (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, rotaLine.getRotaLine());
        preparedStatement.setString(2, rotaLine.getSundayDn());
        preparedStatement.setString(3, rotaLine.getMondayDn());        
        preparedStatement.setString(4, rotaLine.getTuesdayDn());
        preparedStatement.setString(5, rotaLine.getWednesdayDn());
        preparedStatement.setString(6, rotaLine.getThursdayDn());
        preparedStatement.setString(7, rotaLine.getFridayDn());
        preparedStatement.setString(8, rotaLine.getSaturdayDn());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a rotaline hozzáadásakor");
            System.out.println(""+ex);
        }
    }
*/
    // az osszes adat kiiratasa a sofor tablabol a konzolra
    public void showAllDrivers(){
        String sql = "select * from drivers";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()){
                String name = rs.getString("Name");
                String employeeNumber = rs.getString("Employeenumber");
                int startLine = rs.getInt("Startline");
                System.out.println(startLine + " | " + employeeNumber + " | " + name);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a driverek kiolvasásakor");
            System.out.println(""+ex);
        }
    }
    // kirja a konzolra az osszes sort a rotaline tablabol
    public void showAllRotaLines(){
        String sql = "select * from rotaline";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()){
                int rotaLine = rs.getInt("rotalineumber");
                String sunday = rs.getString("sundaydutynumber");
                String monday = rs.getString("mondaydutynumber");
                String tuesday = rs.getString("tuesdaydutynumber");
                String wednesday = rs.getString("wednesdaydutynumber");
                String thursday = rs.getString("thursdaydutynumber");
                String friday = rs.getString("fridaydutynumber");
                String saturday = rs.getString("saturdaydutynumber");
                System.out.println(rotaLine + " | " + sunday+ " | " +  monday + " | " + tuesday
                        + " | " + wednesday + " | " + thursday + " | " + friday
                        + " | " + saturday);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a rotalines kiolvasásakor");
            System.out.println(""+ex);
        }
    }
    // a dutys tablabol kiirja az adatokat a konzolra
    public void showAllDutys(){
        String sql = "select * from dutys";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()){
                String Dutynumber = rs.getString("Dutynumber");
                String starttime = rs.getString("Starttime");
                String finishtime = rs.getString("Finishtime");
                System.out.println(Dutynumber + " | " + starttime + " | " + finishtime);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a driverek kiolvasásakor");
            System.out.println(""+ex);
        }
    }
    
    //..
    public void kozvetlenParancs(String sql){
        try {
            createStatement.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Nem futott le a kozvetlen parancs " + ex);
        }
    }
    //**********************************************************************
    //**********************************************************************
}