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
        Integer viesteja = rs.getInt("viesteja");

        Lanka o = new Lanka(id, otsikko, alue, viesteja);

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
            Integer viesteja = rs.getInt("viesteja");

            langat.add(new Lanka(id, otsikko, alue, viesteja));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void lisaa(String otsikko, String alue, int viesteja) throws Exception {
        
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Lanka(id, otsikko, alue, viesteja) VALUES(?, ?, ?, ?)");
        stmt.setObject(1, haeMaara() +1);
        stmt.setObject(2, otsikko);
        stmt.setObject(3, alue);
        stmt.setObject(4, viesteja);
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
    
    public Lanka etsiOtsikolla(String otsikkoo) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE otsikko = ?");
        stmt.setObject(1, otsikkoo);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");        
        String alue = rs.getString("alue");
        Integer viesteja = rs.getInt("viesteja");
        
        Lanka o = new Lanka(id, otsikko, alue, viesteja);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

}
