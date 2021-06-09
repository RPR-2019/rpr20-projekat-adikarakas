package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Goal;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.beans.Stats;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class FinishController {
    public Label managerLabel;
    public Label championsLabel;
    public Label captainLabel;
    public Label scorerLabel;
    public Label assistentLabel;
    public Label goalkeeperLabel;

    private Club winningClub;
    private LeagueDAO dao;
    public Button cancelButton;
    public Button finishButton;
    public Button finishAndDeleteButton;

    FinishController(Club c) {
        dao=LeagueDAO.getInstance();
        this.winningClub=c;
    }

    @FXML
    public void initialize() {
        championsLabel.setText(winningClub.getName());
        managerLabel.setText(winningClub.getManager());
        captainLabel.setText(winningClub.getCaptain().toString());
        scorerLabel.setText(calculateTopScorer().getKey().toString() + " (" + calculateTopScorer().getValue() + ")");
        assistentLabel.setText(calculateTopAssistent().getKey().toString() + " (" + calculateTopAssistent().getValue() + ")");
        goalkeeperLabel.setText(calculateGoldenGlove().getKey().toString() + " (" + calculateGoldenGlove().getValue() + ")");
    }

    private Pair<Player, Integer> calculateTopScorer() {
        int max=0;
        int value;
        ArrayList<Player> players = new ArrayList<>(dao.players());
        ArrayList<Goal> goals = new ArrayList<>(dao.goals());
        Player scorer = players.get(0);
        for (int i=0; i<players.size(); i++) {
            value=0;
            for (int j=0; j<goals.size(); j++) {
                if (goals.get(j).getScorer().equals(players.get(i))) value++;
            }
            if (value>max) {
                max=value;
                scorer=players.get(i);
            }
        }
        return new Pair<>(scorer, max);
    }

    private Pair<Player, Integer> calculateTopAssistent() {
        int max=0;
        int value;
        ArrayList<Player> players = new ArrayList<>(dao.players());
        ArrayList<Goal> goals = new ArrayList<>(dao.goals());
        Player assistent = players.get(0);
        for (int i=0; i<players.size(); i++) {
            value=0;
            for (int j=0; j<goals.size(); j++) {
                if (goals.get(j).getAssistent()!=null && goals.get(j).getAssistent().equals(players.get(i))) value++;
            }
            if (value>max) {
                max=value;
                assistent=players.get(i);
            }
        }
        return new Pair<>(assistent, max);
    }

    private Pair<Player, Integer> calculateGoldenGlove() {
        ArrayList<Player> players = new ArrayList<>(dao.players());
        ArrayList<Stats> stats = new ArrayList<>(dao.stats());
        Player player = players.get(0);
        int max = stats.get(0).getCleanSheets();
        for (int i=0; i<stats.size(); i++) {
            if (stats.get(i).getCleanSheets()>max) {
                max=stats.get(i).getCleanSheets();
                player= dao.findPlayer(stats.get(i).getId());
            }
        }
        return new Pair<>(player, max);
    }

    public void finish() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to delete all clubs?");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == yes) {
            dao.deleteAllResults();
            dao.deleteAllGoals();
            dao.deleteAllStats();
            dao.deleteAllPlayers();
            dao.deleteAllClubs();
        }

        else if (result.get() == no) {
            dao.deleteAllResults();
            dao.deleteAllGoals();
            dao.deleteAllStats();
        }

        if (result.get() == yes || result.get() == no) {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"), bundle);
            PreseasonController ctrl = new PreseasonController();
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(248);
            stage.setTitle("Preseason");
            stage.setScene(scene);
            stage.show();

            Stage stage2 = (Stage) finishButton.getScene().getWindow();
            stage2.close();
        }
    }

    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
