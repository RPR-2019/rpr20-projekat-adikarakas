package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MatchController {

    private Club homeClub;
    private Club awayClub;
    public TextField homeTeamScore;
    public TextField awayTeamScore;
    public ListView<Goal> homeTeamGoals = new ListView();
    public ListView<Goal> awayTeamGoals = new ListView();
    public Button cancelButton;
    public Button finishButton;

    MatchController (Club c1, Club c2) {
        this.homeClub=c1;
        this.awayClub=c2;
    }

    @FXML
    public void initialize() {
        homeTeamScore.setText(String.valueOf(homeTeamGoals.getItems().size())); // vjerovatno će trebati
        awayTeamScore.setText(String.valueOf(awayTeamGoals.getItems().size())); // dodati neki listener
//        homeTeamGoals.setItems();
  //      awayTeamGoals.setItems();
    }

    public void addGoalHome(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/goal.fxml"));
            GoalController goalController = new GoalController(this.homeClub.getPlayers(), null); // dodati drugi argument
            loader.setController(goalController);
            root = loader.load();
            stage.setTitle("Goal");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGoalAway(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/goal.fxml"));
            GoalController goalController = new GoalController(this.awayClub.getPlayers(), null); // dodati drugi argument
            loader.setController(goalController);
            root = loader.load();
            stage.setTitle("Goal");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finishPressed(ActionEvent actionEvent) {


        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }


    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
