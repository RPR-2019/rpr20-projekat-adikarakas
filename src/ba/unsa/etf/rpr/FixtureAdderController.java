package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;


public class FixtureAdderController {
    public Button confirmButton, cancelButton;
    public ChoiceBox<Club> homeChoice, awayChoice;
    private LeagueDAO dao;

    FixtureAdderController() {
    }

    @FXML
    public void initialize() {
        ObservableList<Club> teams = FXCollections.observableArrayList();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            teams.add(dao.getInstance().clubs().get(i));
        }
        homeChoice.setItems(teams);
        awayChoice.setItems(teams);
    }

    public void confirmPressed (ActionEvent actionEvent) {
        boolean correct=true;
        if (homeChoice.getValue()==null || awayChoice.getValue()==null) correct = false;
        else if (homeChoice.getValue().equals(awayChoice.getValue())) correct = false;
        for (int i = 0; i < dao.getInstance().results().size(); i++) {
            if (dao.getInstance().results().get(i).getHomeTeam().getName().equals(homeChoice.getValue().getName()) && dao.getInstance().results().get(i).getAwayTeam().getName().equals(awayChoice.getValue().getName()))
                correct = false;
        }
        for (int i = 0; i < dao.getInstance().fixtures().size(); i++) {
            if (dao.getInstance().fixtures().get(i).getHomeTeam().getName().equals(homeChoice.getValue().getName()) && dao.getInstance().fixtures().get(i).getAwayTeam().getName().equals(awayChoice.getValue().getName()))
                correct = false;
        }
        if (correct) {
            Fixture newFixture = new Fixture(homeChoice.getValue(), awayChoice.getValue());
            dao.getInstance().addFixture(newFixture);
            FxRobot robot = new FxRobot();
            ListView fixturesList = robot.lookup("#fixturesList").queryAs(ListView.class);
            fixturesList.getItems().add(newFixture);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravan par");
            alert.setContentText("Timovi su isti ili par već postoji");
            alert.showAndWait();
        }
    }

    public void cancelPressed (ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
