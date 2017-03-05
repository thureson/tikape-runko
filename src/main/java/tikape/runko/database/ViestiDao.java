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
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String teksti = rs.getString("teksti");
        String lahettaja = rs.getString("lahettaja");
        String aika = rs.getString("aika");
        String lanka = rs.getString("lanka");
        String alue = rs.getString("alue");

        Viesti o = new Viesti(id, teksti, lahettaja, aika, lanka, alue);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teksti = rs.getString("teksti");
            String lahettaja = rs.getString("lahettaja");
            String aika = rs.getString("aika");
            String lanka = rs.getString("lanka");
            String alue = rs.getString("alue");

            viestit.add(new Viesti(id, teksti, lahettaja, aika, lanka, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> findAllReverse() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti ORDER BY id DESC");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teksti = rs.getString("teksti");
            String lahettaja = rs.getString("lahettaja");
            String aika = rs.getString("aika");
            String lanka = rs.getString("lanka");
            String alue = rs.getString("alue");

            viestit.add(new Viesti(id, teksti, lahettaja, aika, lanka, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> findTen() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teksti = rs.getString("teksti");
            String lahettaja = rs.getString("lahettaja");
            String aika = rs.getString("aika");
            String lanka = rs.getString("lanka");
            String alue = rs.getString("alue");

            viestit.add(new Viesti(id, teksti, lahettaja, aika, lanka, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void lisaa(String teksti, String lahettaja, String aika, String lanka, String alue) throws Exception {
        
        Connection connection = database.getConnection();                        

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti(id, teksti, lahettaja, aika, lanka, alue) VALUES(?, ?, ?, ?, ?, ?)");
        stmt.setObject(1, haeMaara() +1);
        stmt.setObject(2, teksti);
        stmt.setObject(3, lahettaja);
        stmt.setObject(4, aika);
        stmt.setObject(5, lanka);
        stmt.setObject(6, alue);
        stmt.execute();

        stmt.close();
        connection.close();
    }
    
    public int haeMaara() throws Exception {
        Connection connection = database.getConnection();                
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS mm FROM Viesti");

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
