package ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produs;
import model.Comanda;
import service.ProdusService;
import service.ComandaService;

import java.util.Optional;

public class AdminView {

    private ProdusService produsService = new ProdusService();
    private TableView<Produs> tabel = new TableView<>();
    private ObservableList<Produs> listaProduse;

    public void show(Stage stage) {

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        TableColumn<Produs, Number> colNr = new TableColumn<>("Nr");
        colNr.setCellValueFactory(col ->
                new ReadOnlyObjectWrapper<>(
                        tabel.getItems().indexOf(col.getValue()) + 1
                )
        );

        TableColumn<Produs, String> colNume = new TableColumn<>("Denumire");
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Produs, Double> colPret = new TableColumn<>("Preț");
        colPret.setCellValueFactory(new PropertyValueFactory<>("pret"));

        TableColumn<Produs, Integer> colCant = new TableColumn<>("Cantitate");
        colCant.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        tabel.getColumns().addAll(colNr, colNume, colPret, colCant);

        refresh();

        TextField tfNume = new TextField();
        tfNume.setPromptText("Denumire");

        TextField tfPret = new TextField();
        tfPret.setPromptText("Preț");

        TextField tfCant = new TextField();
        tfCant.setPromptText("Cantitate");

        Button btnAdauga = new Button("Adaugă");
        Button btnSterge = new Button("Șterge selectat");
        Button btnIstoric = new Button("Istoric comenzi");
        Button btnLogout = new Button("Logout");

        btnAdauga.setOnAction(e -> {
            try {
                String nume = tfNume.getText();
                double pret = Double.parseDouble(tfPret.getText());
                int cant = Integer.parseInt(tfCant.getText());

                produsService.adaugaSauActualizeaza(nume, pret, cant);
                refresh();

                tfNume.clear();
                tfPret.clear();
                tfCant.clear();

            } catch (Exception ex) {
                alerta("Date invalide!");
            }
        });

        btnSterge.setOnAction(e -> {

            Produs selectat = tabel.getSelectionModel().getSelectedItem();
            if (selectat == null) {
                alerta("Selectează un produs!");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Sigur vrei să ștergi produsul?");
            Optional<ButtonType> rezultat = alert.showAndWait();

            if (rezultat.isPresent() && rezultat.get() == ButtonType.OK) {
                produsService.stergeProdus(selectat.getId());
                refresh();
            }
        });

        btnIstoric.setOnAction(e -> deschideIstoric());

        btnLogout.setOnAction(e -> new LoginView(stage));

        HBox form = new HBox(10, tfNume, tfPret, tfCant, btnAdauga, btnSterge);

        root.getChildren().addAll(tabel, form, btnIstoric, btnLogout);

        Scene scena = new Scene(root, 900, 550);

        stage.setTitle("Admin");
        stage.setScene(scena);
        stage.show();
    }

    private void refresh() {
        listaProduse = FXCollections.observableArrayList(produsService.getProduse());
        tabel.setItems(listaProduse);
    }

    private void alerta(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(mesaj);
        alert.showAndWait();
    }

    private void deschideIstoric() {

        Stage stageIstoric = new Stage();

        TableView<Comanda> tabelComenzi = new TableView<>();

        TableColumn<Comanda, Integer> colId =
                new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Comanda, Integer> colClient =
                new TableColumn<>("Client ID");
        colClient.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        TableColumn<Comanda, Double> colTotal =
                new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<Comanda, java.time.LocalDate> colData =
                new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabelComenzi.getColumns().addAll(colId, colClient, colTotal, colData);

        ComandaService comandaService = new ComandaService();

        tabelComenzi.setItems(
                FXCollections.observableArrayList(
                        comandaService.getComenzi()
                )
        );

        VBox root = new VBox(10, tabelComenzi);
        root.setPadding(new Insets(20));

        Scene scena = new Scene(root, 700, 400);

        stageIstoric.setTitle("Istoric Comenzi");
        stageIstoric.setScene(scena);
        stageIstoric.show();
    }
}
