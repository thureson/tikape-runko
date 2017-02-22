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
public class Lanka {
    
    private int id;
    private String otsikko;
    private int alue;
    
    public Lanka(int id, String otsikko, int alue){
        this.id = id;
        this.otsikko = otsikko;
        this.alue = alue;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getOtsikko(){
        return this.otsikko;
    }
    
    public int getAlue(){
        return this.alue;
    }
}
