package model;

import java.time.LocalDate;

public class Comanda {

    private int id;
    private int clientId;
    private double total;
    private LocalDate data;


    public Comanda(int clientId, double total) {
        this.clientId = clientId;
        this.total = total;
        this.data = LocalDate.now();
    }

    public Comanda(int id, int clientId, double total, LocalDate data) {
        this.id = id;
        this.clientId = clientId;
        this.total = total;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public double getTotal() {
        return total;
    }

    public LocalDate getData() {
        return data;
    }
}
