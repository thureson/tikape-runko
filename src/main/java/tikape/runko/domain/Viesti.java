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
public class Viesti {
    
    private int id;
    private String teksti;
    private String lahettaja;
    private String aika; // HUOM, mahdollisesti ei String
    private int lanka;
    
    public Viesti(int id, String teksti, String lahettaja, String aika, int lanka){
        this.id = id;
        this.teksti = teksti;
        this.lahettaja = lahettaja;
        this.aika = aika;
        this.lanka = lanka;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getTeksti(){
        return this.teksti;
    }
    
    public String getLahettaja(){
        return this.lahettaja;
    }
    
    public String getAika(){
        return this.aika;
    }
    
    public int getLanka(){
        return this.lanka;
    }
}
