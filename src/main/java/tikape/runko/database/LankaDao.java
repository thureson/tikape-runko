/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lanka;

public class LankaDao implements Dao<Lanka, Integer> {

    private Database database;

    public LankaDao(Database database) {
        this.database = database;
    }

    @Override
    public Lanka findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");        
        String alue = rs.getString("alue");
        Integer maara = rs.getInt("maara");
        String aika = rs.getString("aika");

        Lanka o = new Lanka(id, otsikko, alue, maara, aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Lanka> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka");

        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");        
            String alue = rs.getString("alue");
            Integer maara = rs.getInt("maara");
            String aika = rs.getString("aika");
            
            langat.add(new Lanka(id, otsikko, alue, maara, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
    public List<Lanka> findAllByAika() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka ORDER BY aika DESC");

        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");        
            String alue = rs.getString("alue");
            Integer maara = rs.getInt("maara");
            String aika = rs.getString("aika");
            
            langat.add(new Lanka(id, otsikko, alue, maara, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Lanka WHERE id = ?");
        stmt.setObject(1, key);
        stmt.execute();

        stmt.close();
        connection.close();
    }
    
    public void lisaa(String otsikko, String alue, int maara, String aika) throws Exception {
        this.lisaa(this.haeMaara()+1, otsikko, alue, maara, aika);
    }
    
    public void lisaa(int id, String otsikko, String alue, int maara, String aika) throws Exception {
        
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Lanka(id, otsikko, alue, maara, aika) VALUES(?, ?, ?, ?, ?)");
        stmt.setObject(1, id);
        stmt.setObject(2, otsikko);
        stmt.setObject(3, alue);
        stmt.setObject(4, maara);
        stmt.setObject(5, aika);
        stmt.execute();

        stmt.close();
        connection.close();
    }
    
    public int haeMaara() throws Exception {
        Connection connection = database.getConnection();                
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS mm FROM Lanka");

        ResultSet rs = stmt.executeQuery();
        int maara = 555;
        
        if (rs.next()){
            maara = rs.getInt("mm");
        } else {
            maara = 1000;
        }
        

        rs.close();
        stmt.close();
        connection.close();

        return maara;
    }
    
    public List<Lanka> etsiViestienMaara(String otsikkoo) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Lanka = ?");
        stmt.setObject(1, otsikkoo);

        
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");        
            String alue = rs.getString("alue");
            Integer maara = rs.getInt("maara");
            String aika = rs.getString("aika");
            
            langat.add(new Lanka(id, otsikko, alue, maara, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
    public void paivita(String title, String aihe) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE otsikko = ? AND alue = ?");
        stmt.setObject(1, title);
        stmt.setObject(2, aihe);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");        
        String alue = rs.getString("alue");
        
        rs.close();
        stmt.close();
        connection.close();
        
        Connection connection2 = database.getConnection();
        PreparedStatement stmt2 = connection2.prepareStatement("SELECT COUNT(*) AS ff FROM Viesti WHERE lanka = ? AND alue = ?");
        stmt2.setObject(1, title);
        stmt2.setObject(2, aihe);

        ResultSet rs2 = stmt2.executeQuery();
        boolean hasOne2 = rs2.next();
        if (!hasOne2) {
            return;
        }

        Integer maara = rs2.getInt("ff");
        
        rs2.close();
        stmt2.close();
        connection2.close();
        
        Connection connection3 = database.getConnection();
        PreparedStatement stmt3 = connection3.prepareStatement("SELECT aika FROM Viesti WHERE lanka = ? AND alue = ? ORDER BY id DESC LIMIT 1");
        stmt3.setObject(1, title);
        stmt3.setObject(2, aihe);
        
        ResultSet rs3 = stmt3.executeQuery();
        boolean hasOne3 = rs3.next();
        if (!hasOne3) {
            return;
        }
        
        String aika = rs3.getString("aika");

        rs3.close();
        stmt3.close();
        connection3.close();
        
        try {
            this.delete(id);
            this.lisaa(id, otsikko, alue, maara, aika);
        } catch (Exception e){
            System.out.println("lisaaBug");
        }
    }
    
    public boolean sisaltaa(String title, String aihe) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT otsikko FROM Lanka WHERE alue = ?");
        stmt.setObject(1, aihe);
        
        ResultSet rs = stmt.executeQuery();
        List<String> langat = new ArrayList<>();
        while (rs.next()) {
            String lanka = rs.getString("otsikko");

            
            langat.add(lanka);
        }

        rs.close();
        stmt.close();
        connection.close();

        if (langat.contains(title)){
            return true;
        } else {
            return false;
        }
        
    }

}
