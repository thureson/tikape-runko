/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author thureson
 */
public class Alue {
    
    private int id;
    private String alue;
    private int mr;
    private String time;
    
    public Alue(int id, String alue, int mr, String time){
        this.id = id;
        this.alue = alue;
        this.mr = mr;
        this.time = time;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getAlue(){
        return this.alue;
    }
    
    public int getMr(){
        return this.mr;
    }
    
    public String getTime(){
        return this.time;
    }
}
