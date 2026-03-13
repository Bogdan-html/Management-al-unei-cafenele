package model;

public class ItemComanda {
    private Produs produs;
    private int cantitate;

    public ItemComanda(Produs produs, int cantitate) {
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public double getSubtotal() {
        return produs.getPret() * cantitate;
    }

    @Override
    public String toString() {
        return produs.getNume() + " x" + cantitate + " = " + getSubtotal() + " lei";
    }
}
