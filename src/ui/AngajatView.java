package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produs;
import service.ComandaService;
import service.ProdusService;

public class AngajatView {

    private ProdusService produsService = new ProdusService();
    private ComandaService comandaService = new ComandaService();

    private TableView<Produs> tabelProduse = new TableView<>();
    private TableView<Produs> tabelCos = new TableView<>();

    private ObservableList<Produs> listaProduse;
    private ObservableList<Produs> cos = FXCollections.observableArrayList();

    private Label totalLabel = new Label("Total: 0");

    public void show(Stage stage) {

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        configurareTabelProduse();
        configurareTabelCos();
        refreshProduse();

        TextField tfCantitate = new TextField();
        tfCantitate.setPromptText("Cantitate");

        Button btnAdauga = new Button("Adaugă în comandă");
        Button btnFinalizeaza = new Button("Finalizează comandă");
        Button btnLogout = new Button("Logout");

        btnAdauga.setOnAction(e -> {

            Produs selectat = tabelProduse.getSelectionModel().getSelectedItem();
            if (selectat == null) {
                alerta("Selectează un produs!");
                return;
            }

            int cantitate;
            try {
                cantitate = Integer.parseInt(tfCantitate.getText());
            } catch (Exception ex) {
                alerta("Cantitate invalidă!");
                return;
            }

            if (cantitate <= 0) {
                alerta("Cantitatea trebuie să fie > 0");
                return;
            }

            if (cantitate > selectat.getCantitate()) {
                alerta("Stoc insuficient!");
                return;
            }

            for (Produs p : cos) {
                if (p.getId() == selectat.getId()) {
                    p.setCantitate(p.getCantitate() + cantitate);
                    tabelCos.refresh();
                    actualizeazaTotal();
                    return;
                }
            }


            Produs produsCos = new Produs(
                    selectat.getId(),
                    selectat.getNume(),
                    selectat.getPret(),
                    selectat.getCategorie(),
                    cantitate
            );

            cos.add(produsCos);
            actualizeazaTotal();
        });

        btnFinalizeaza.setOnAction(e -> {

            if (cos.isEmpty()) {
                alerta("Coșul este gol!");
                return;
            }

            try {
                double total = calculeazaTotal();
                int idComanda = comandaService.creeazaComanda(1, total);

                for (Produs p : cos) {
                    comandaService.adaugaProdus(idComanda, p.getId(), p.getCantitate());
                    produsService.scadeStoc(p.getId(), p.getCantitate());
                }

                cos.clear();
                actualizeazaTotal();
                refreshProduse();

                alerta("Comandă salvată cu succes!");

            } catch (Exception ex) {
                ex.printStackTrace();
                alerta("Eroare la salvare comandă!");
            }
        });

        btnLogout.setOnAction(e -> new LoginView(stage));

        root.getChildren().addAll(
                new Label("Produse disponibile"),
                tabelProduse,
                tfCantitate,
                btnAdauga,
                new Label("Coș comandă"),
                tabelCos,
                totalLabel,
                btnFinalizeaza,
                btnLogout
        );

        Scene scena = new Scene(root, 900, 600);
        stage.setScene(scena);
        stage.setTitle("Angajat - Comenzi");
        stage.show();
    }

    private void configurareTabelProduse() {

        TableColumn<Produs, String> colNume = new TableColumn<>("Denumire");
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Produs, Double> colPret = new TableColumn<>("Preț");
        colPret.setCellValueFactory(new PropertyValueFactory<>("pret"));

        TableColumn<Produs, Integer> colCant = new TableColumn<>("Stoc");
        colCant.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        tabelProduse.getColumns().addAll(colNume, colPret, colCant);
    }

    private void configurareTabelCos() {

        TableColumn<Produs, String> colNume = new TableColumn<>("Produs");
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Produs, Double> colPret = new TableColumn<>("Preț");
        colPret.setCellValueFactory(new PropertyValueFactory<>("pret"));

        TableColumn<Produs, Integer> colCant = new TableColumn<>("Cantitate");
        colCant.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        tabelCos.getColumns().addAll(colNume, colPret, colCant);
        tabelCos.setItems(cos);
    }

    private void refreshProduse() {
        listaProduse = FXCollections.observableArrayList(produsService.getProduse());
        tabelProduse.setItems(listaProduse);
    }

    private void actualizeazaTotal() {
        totalLabel.setText("Total: " + calculeazaTotal());
    }

    private double calculeazaTotal() {
        return cos.stream()
                .mapToDouble(p -> p.getPret() * p.getCantitate())
                .sum();
    }

    private void alerta(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(mesaj);
        alert.showAndWait();
    }
}
