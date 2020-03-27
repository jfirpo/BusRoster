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

    ArrayList<Duty> duties = new ArrayList<>();   
    public DutyRepository(){
    
    }

    public void setDuties(ArrayList<Duty> duties) {
        this.duties = duties;
    }

    public ArrayList<Duty> getDuties() {
        return duties;
    }
    
        public DutyRepository(DB db){
            for (int i = 1; i < 18; i++) duties.add(db.getAllDutys());
    }



    
}
