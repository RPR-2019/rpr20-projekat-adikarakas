package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    private LeagueDAO dao;

    @Override
    public void start(Stage primaryStage) throws Exception{
    //    if (!dao.getInstance().isCreated()) {
        if (dao.getInstance().clubs().size()==0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
            loader.setController(new StartController());
            Parent root = loader.load();
            primaryStage.setTitle("Create league");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.show();
        }
//        else if (dao.getInstance().isCreated() && !dao.getInstance().isStarted()) {
        else if (dao.getInstance().clubs().size()!=0 && dao.getInstance().fixtures().size()==0 && dao.getInstance().results().size()==0) {  // treba dodati i matches
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"));
            loader.setController(new PreseasonController(4)); // treba vratiti na dao.getInstance().getNumberOfClubs()
            Parent root = loader.load();
            primaryStage.setTitle("Preseason");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.show();
        }
        else if (dao.getInstance().results().size()!=0 || dao.getInstance().fixtures().size()!=0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/season.fxml"));
            loader.setController(new SeasonController());
            Parent root = loader.load();
            primaryStage.setTitle("Preseason");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
