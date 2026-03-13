package model;

public class Client {
    private int id;
    private String nume;
    private String telefon;

    public Client(int id, String nume, String telefon) {
        this.id = id;
        this.nume = nume;
        this.telefon = telefon;
    }

    public Client(String nume, String telefon) {
        this.nume = nume;
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getTelefon() {
        return telefon;
    }

    @Override
    public String toString() {
        return id + " | " + nume + " | " + telefon;
    }
}
