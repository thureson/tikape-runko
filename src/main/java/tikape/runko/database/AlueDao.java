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

        Alue o = new Alue(id, alue, mr);

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
            
            alueet.add(new Alue(id, alue, mr));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void lisaa(String nimi, int mr) throws Exception {
        
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue(id, alue, mr) VALUES(?, ?, ?)");
        stmt.setObject(1, haeMaara());
        stmt.setObject(2, nimi);
        stmt.setObject(3, mr);
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

}