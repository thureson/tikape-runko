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
    private String alue;
    private int mr;
    
    public Lanka(int id, String otsikko, String alue, int mr){
        this.id = id;
        this.otsikko = otsikko;
        this.alue = alue;
        this.mr = mr;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getOtsikko(){
        return this.otsikko;
    }
    
    public String getAlue(){
        return this.alue;
    }
    
    public int getMr(){
        return this.mr;
    }
}
