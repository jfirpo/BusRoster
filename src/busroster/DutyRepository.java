/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busroster;

import java.util.ArrayList;

/**
 *
 * @author Krisztián
 */
public class DutyRepository {

    private ArrayList<Duty> duties = new ArrayList<>();   

    public DutyRepository(){
    }

    public void setDuties(ArrayList<Duty> duties) {
        this.duties = duties;
    }

    public ArrayList<Duty> getDuties() {
        return duties;
    }
    
    public DutyRepository(DB db){
            this.duties = db.getAllDutys();
    }
}
