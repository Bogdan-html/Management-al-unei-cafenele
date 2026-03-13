package service;

import db.Database;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientService {

    public void adaugaClient(Client c) {
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO clienti(nume, telefon) VALUES (?, ?)")) {

            ps.setString(1, c.getNume());
            ps.setString(2, c.getTelefon());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getClienti() {
        List<Client> lista = new ArrayList<>();

        try (Connection con = Database.getConnection();
             ResultSet rs = con.createStatement()
                     .executeQuery("SELECT * FROM clienti")) {

            while (rs.next()) {
                lista.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("telefon")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Client getClientById(int id) {
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM clienti WHERE id=?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("telefon")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
