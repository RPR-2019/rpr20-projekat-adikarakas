package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Fixture;
import ba.unsa.etf.rpr.beans.Result;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.ArrayList;


public class FixtureAdderController {
    public Button confirmButton;
    public Button cancelButton;
    public ChoiceBox<Club> homeChoice;
    public ChoiceBox<Club> awayChoice;
    private LeagueDAO dao;

    public FixtureAdderController() {
        dao=LeagueDAO.getInstance();
    }

    @FXML
    public void initialize() {
        ObservableList<Club> teams = FXCollections.observableArrayList();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (int i=0; i<clubs.size(); i++) {
            teams.add(clubs.get(i));
        }
        homeChoice.setItems(teams);
        awayChoice.setItems(teams);
    }

    public void confirmPressed () {
        boolean correct=true;
        Club home = homeChoice.getValue();
        Club away = awayChoice.getValue();
        ArrayList<Result> results = new ArrayList<>(dao.results());
        ArrayList<Fixture> fixtures = new ArrayList<>(dao.fixtures());
        if (home==null || away==null || home.equals(away)) correct = false;
        else {
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getHomeTeam().getName().equals(home.getName()) && results.get(i).getAwayTeam().getName().equals(away.getName()))
                    correct = false;
            }
            for (int i = 0; i < fixtures.size(); i++) {
                if (fixtures.get(i).getHomeTeam().getName().equals(home.getName()) && fixtures.get(i).getAwayTeam().getName().equals(away.getName()))
                    correct = false;
            }
        }
        if (correct) {
            Fixture newFixture = new Fixture(home, away);
            dao.addFixture(newFixture);
            FxRobot robot = new FxRobot();
            ListView<Fixture> fixturesList = robot.lookup("#fixturesList").queryAs(ListView.class);
            fixturesList.getItems().add(newFixture);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect fixture");
            alert.setContentText("Clubs are the same or this fixture has been added before");
            alert.showAndWait();
        }
    }

    public void cancelPressed () {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
