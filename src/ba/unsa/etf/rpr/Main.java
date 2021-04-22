package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
        loader.setController(new StartController());
        Parent root = loader.load();
        primaryStage.setTitle("Create league");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
  //      primaryStage.setTitle("Create league");
    //    primaryStage.setScene(new Scene(root, 300, 275));
      //  primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
