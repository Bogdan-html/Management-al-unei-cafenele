package service;

import db.Database;
import model.Rol;

import java.sql.*;

public class AuthService {

    private Rol rolCurent;
    private boolean logat = false;

    public boolean login(String user, String pass) {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT rol FROM users WHERE username=? AND parola=?")) {

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rolCurent = Rol.valueOf(rs.getString("rol"));
                logat = true;
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Rol getRol() {
        return rolCurent;
    }

    public void logout() {
        rolCurent = null;
        logat = false;
    }

    public boolean esteLogat() {
        return logat;
    }
}
