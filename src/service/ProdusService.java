package service;

import db.Database;
import model.Produs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdusService {

    public void addProduct(String name, double price, int quantity) throws SQLException {
        Connection conn = Database.getConnection();

        String checkSql = "SELECT id, quantity FROM products WHERE name = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, name);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int oldQty = rs.getInt("quantity");
            int newQty = oldQty + quantity;

            String updateSql = "UPDATE products SET quantity = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, newQty);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
        } else {
            String insertSql = "INSERT INTO products(name, price, quantity) VALUES(?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, name);
            insertStmt.setDouble(2, price);
            insertStmt.setInt(3, quantity);
            insertStmt.executeUpdate();
        }
    }



    public List<Produs> getProduse() {
        List<Produs> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM produse")) {

            while (rs.next()) {
                list.add(new Produs(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret"),
                        rs.getString("categorie"),
                        rs.getInt("cantitate")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void scadeStoc(int produsId, int q) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE produse SET cantitate = cantitate - ? WHERE id=?")) {
            ps.setInt(1, q);
            ps.setInt(2, produsId);
            ps.executeUpdate();
        }
    }

    public void stergeProdus(int id) {
        try (Connection con = Database.getConnection();
             PreparedStatement ps =
                     con.prepareStatement("DELETE FROM produse WHERE id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adaugaSauActualizeaza(String nume, double pret, int cantitate) throws SQLException {
        Connection con = Database.getConnection();

        String sqlVerif = "SELECT id, cantitate FROM produse WHERE nume = ?";
        PreparedStatement psVerif = con.prepareStatement(sqlVerif);
        psVerif.setString(1, nume);
        ResultSet rs = psVerif.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int cantVeche = rs.getInt("cantitate");
            int cantNoua = cantVeche + cantitate;

            String sqlUpdate = "UPDATE produse SET cantitate=? WHERE id=?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, cantNoua);
            psUpdate.setInt(2, id);
            psUpdate.executeUpdate();
        } else {
            String sqlInsert = "INSERT INTO produse(nume,pret,cantitate) VALUES(?,?,?)";
            PreparedStatement psInsert = con.prepareStatement(sqlInsert);
            psInsert.setString(1, nume);
            psInsert.setDouble(2, pret);
            psInsert.setInt(3, cantitate);
            psInsert.executeUpdate();
        }
    }


    public double getPretProdus(int id) {
        try (Connection con = Database.getConnection();
             PreparedStatement ps =
                     con.prepareStatement("SELECT pret FROM produse WHERE id=?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("pret");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
