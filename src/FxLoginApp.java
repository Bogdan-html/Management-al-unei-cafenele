import javafx.application.Application;
import javafx.stage.Stage;
import db.Database;
import ui.LoginView;

public class FxLoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Database.init();

        new LoginView(primaryStage);

    }

    public static void main(String[] args) {
        launch();
    }
}
