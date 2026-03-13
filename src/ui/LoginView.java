package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Rol;
import service.AuthService;

public class LoginView {

    private AuthService authService = new AuthService();

    public LoginView(Stage stage) {

        Label titlu = new Label("Caffeine Manager - Login");

        TextField tfUser = new TextField();
        tfUser.setPromptText("Username");

        PasswordField tfPass = new PasswordField();
        tfPass.setPromptText("Parolă");

        Label mesaj = new Label();

        Button btnLogin = new Button("Login");

        btnLogin.setOnAction(e -> {

            String username = tfUser.getText();
            String password = tfPass.getText();

            boolean ok = authService.login(username, password);

            if (!ok) {
                mesaj.setText("Date greșite!");
                return;
            }

            if (authService.getRol() == Rol.ADMIN) {
                new AdminView().show(stage);
            } else {
                new AngajatView().show(stage);
            }
        });

        VBox root = new VBox(15, titlu, tfUser, tfPass, btnLogin, mesaj);
        root.setPadding(new Insets(30));

        Scene scena = new Scene(root, 400, 250);

        stage.setTitle("Login");
        stage.setScene(scena);
        stage.show();
    }
}
