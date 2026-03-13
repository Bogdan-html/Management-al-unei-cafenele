package db;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:cafe.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection c = getConnection();
             Statement st = c.createStatement()) {

            // USERS
            st.execute("""
                CREATE TABLE IF NOT EXISTS users(
                    username TEXT PRIMARY KEY,
                    parola TEXT,
                    rol TEXT
                )
            """);

            // PRODUSE
            st.execute("""
                CREATE TABLE IF NOT EXISTS produse(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nume TEXT UNIQUE,
                    pret REAL,
                    categorie TEXT,
                    cantitate INTEGER
                )
            """);

            // CLIENTI
            st.execute("""
                CREATE TABLE IF NOT EXISTS clienti(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nume TEXT,
                    telefon TEXT
                )
            """);

            // COMENZI
            st.execute("""
                CREATE TABLE IF NOT EXISTS comenzi(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    client_id INTEGER,
                    total REAL,
                    data TEXT
                )
            """);

            // ITEME COMANDA
            st.execute("""
                CREATE TABLE IF NOT EXISTS iteme_comanda(
                    comanda_id INTEGER,
                    produs_id INTEGER,
                    cantitate INTEGER
                )
            """);

            //INSERARE USERI DEFAULT
            st.execute("""
                INSERT OR IGNORE INTO users VALUES
                ('admin','12345','ADMIN'),
                ('angajat','123QWE','ANGAJAT')
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
