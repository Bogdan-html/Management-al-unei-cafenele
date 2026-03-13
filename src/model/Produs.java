package model;

public class Produs {

    private int id;
    private String nume;
    private double pret;
    private String categorie;
    private int cantitate;

    public Produs(int id, String nume, double pret, String categorie, int cantitate) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.categorie = categorie;
        this.cantitate = cantitate;
    }

    public Produs(String nume, double pret, String categorie, int cantitate) {
        this.nume = nume;
        this.pret = pret;
        this.categorie = categorie;
        this.cantitate = cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }


    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getCantitate() {
        return cantitate;
    }
}
