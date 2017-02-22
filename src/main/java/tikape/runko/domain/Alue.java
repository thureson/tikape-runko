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
    
    public Alue(int id, String alue){
        this.id = id;
        this.alue = alue;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getAlue(){
        return this.alue;
    }
    
//    mby toString()
    
}
