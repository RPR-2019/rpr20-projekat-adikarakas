package ba.unsa.etf.rpr.other;

import ba.unsa.etf.rpr.controllers.PreseasonController;
import ba.unsa.etf.rpr.controllers.SeasonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    private LeagueDAO dao = LeagueDAO.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        if (("Bosanski").equals(dao.readLanguage())) Locale.setDefault(new Locale("bs", "BA"));
        else if (("English").equals(dao.readLanguage())) Locale.setDefault(new Locale("en", "EN"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        if (dao.fixtures().isEmpty() && dao.results().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"), bundle);
            loader.setController(new PreseasonController());
            Parent root = loader.load();
            primaryStage.setTitle("Preseason");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.setMinHeight(150);
            primaryStage.setMinWidth(248);
            primaryStage.show();
        }
       else if (!dao.results().isEmpty() || !dao.fixtures().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/season.fxml"), bundle);
            loader.setController(new SeasonController());
            Parent root = loader.load();
            primaryStage.setTitle("Season");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(1000);
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
