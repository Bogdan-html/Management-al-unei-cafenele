package service;

import db.Database;
import model.Comanda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComandaService {


    public int creeazaComanda(int clientId, double total) {

        try (Connection con = Database.getConnection()) {

            String sql = "INSERT INTO comenzi(client_id, total, data) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, clientId);
            ps.setDouble(2, total);


            ps.setString(3, java.time.LocalDate.now().toString());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    // adaugă produs în comandă
    public void adaugaProdus(int idComanda, int idProdus, int cantitate) {

        try (Connection con = Database.getConnection()) {

            String sql =
                    "INSERT INTO iteme_comanda(comanda_id, produs_id, cantitate) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idComanda);
            ps.setInt(2, idProdus);
            ps.setInt(3, cantitate);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // istoric comenzi
    public List<Comanda> getComenzi() {

        List<Comanda> list = new ArrayList<>();

        try (Connection con = Database.getConnection();
             ResultSet rs = con.createStatement()
                     .executeQuery("SELECT * FROM comenzi ORDER BY id DESC")) {

            while (rs.next()) {

                String dataText = rs.getString("data");

                list.add(new Comanda(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getDouble("total"),
                        java.time.LocalDate.parse(dataText)
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
