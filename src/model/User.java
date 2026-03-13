package model;

public class User {
    private String username;
    private String parola;
    private Rol rol;

    public User(String username, String parola, Rol rol) {
        this.username = username;
        this.parola = parola;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }

    public Rol getRol() {
        return rol;
    }
}
