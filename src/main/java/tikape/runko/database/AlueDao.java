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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");        
        String alue = rs.getString("alue");
        Integer mr = rs.getInt("mr");
        String time = rs.getString("time");

        Alue o = new Alue(id, alue, mr, time);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Alue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");     
            String alue = rs.getString("alue");
            Integer mr = rs.getInt("mr");
            String time = rs.getString("time");
            
            alueet.add(new Alue(id, alue, mr, time));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }
    
    public List<Alue> findAllByTime() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY time DESC");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");     
            String alue = rs.getString("alue");
            Integer mr = rs.getInt("mr");
            String time = rs.getString("time");
            
            alueet.add(new Alue(id, alue, mr, time));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }
    
    public List<Alue> findAllByName() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY alue ASC");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");     
            String alue = rs.getString("alue");
            Integer mr = rs.getInt("mr");
            String time = rs.getString("time");
            
            alueet.add(new Alue(id, alue, mr, time));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Alue WHERE id = ?");
        stmt.setObject(1, key);
        stmt.execute();

        stmt.close();
        connection.close();
    }
    
    public void lisaa(String nimi, int mr, String time) throws Exception {
        this.lisaa(this.haeMaara() +1, nimi, mr, time);
    }
    
    public void lisaa(int id, String nimi, int mr, String time) throws Exception {
        
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue(id, alue, mr, time) VALUES(?, ?, ?, ?)");
        stmt.setObject(1, id);
        stmt.setObject(2, nimi);
        stmt.setObject(3, mr);
        stmt.setObject(4, time);
        stmt.execute();

        stmt.close();
        connection.close();
    }
    
    public int haeMaara() throws Exception {
        Connection connection = database.getConnection();                
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS mm FROM Alue");

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
    
    public void paivita(String title) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE alue = ?");
        stmt.setObject(1, title);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return;
        }

        Integer id = rs.getInt("id");      
        String alue = rs.getString("alue");
        
        rs.close();
        stmt.close();
        connection.close();
        
        Connection connection2 = database.getConnection();
        PreparedStatement stmt2 = connection2.prepareStatement("SELECT maara AS kk FROM Lanka WHERE alue = ?");
        stmt2.setObject(1, title);

        ResultSet rs2 = stmt2.executeQuery();
        Integer maara = 0;
        while (rs2.next()) {
            maara += rs2.getInt("kk");
            
        }

        
        rs2.close();
        stmt2.close();
        connection2.close();
        
        Connection connection3 = database.getConnection();
        PreparedStatement stmt3 = connection3.prepareStatement("SELECT aika FROM Lanka WHERE alue = ? AND aika != '-' ORDER BY aika DESC LIMIT 1");
        stmt3.setObject(1, title);
        
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
            this.lisaa(id, alue, maara, aika);
        } catch (Exception e){
            System.out.println("lisaaAlueBug");
        }

    }
    
    public boolean sisaltaa(String title) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT alue FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<String> alueet = new ArrayList<>();
        while (rs.next()) {
            String alue = rs.getString("alue");

            
            alueet.add(alue);
        }

        rs.close();
        stmt.close();
        connection.close();

        if (alueet.contains(title)){
            return true;
        } else {
            return false;
        }
        
    }

}