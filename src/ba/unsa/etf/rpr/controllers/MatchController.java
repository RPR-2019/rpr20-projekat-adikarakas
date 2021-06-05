package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.*;
import ba.unsa.etf.rpr.other.ClubOnTable;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MatchController {

    private Club homeClub;
    private Club awayClub;
    public TextField homeTeamScore;
    public TextField awayTeamScore;
    public ListView<Goal> homeTeamGoals = new ListView<>();
    public ListView<Goal> awayTeamGoals = new ListView<>();
    public Button cancelButton;
    public Button finishButton;
    public TextField homeTeamName;
    public TextField awayTeamName;
    private List<Player> homePlayers;
    private List<Player> awayPlayers;
    private final ObservableList<Goal> homeClubGoals = FXCollections.observableArrayList();
    private final ObservableList<Goal> awayClubGoals = FXCollections.observableArrayList();
    private LeagueDAO dao;
    private ObservableList<ClubOnTable> clubsOnTable;

    MatchController (Club c1, Club c2, List<Player> p1, List<Player> p2, ObservableList<ClubOnTable> clubsOnTable) {
        dao=LeagueDAO.getInstance();
        this.homeClub=c1;
        this.awayClub=c2;
        this.homePlayers=p1;
        this.awayPlayers=p2;
        this.clubsOnTable=clubsOnTable;
    }

    @FXML
    public void initialize() {
        homeTeamName.setText(this.homeClub.getName());
        awayTeamName.setText(this.awayClub.getName());
        homeClubGoals.sort((o1, o2) -> o1.getMinute() - o2.getMinute());
        awayClubGoals.sort((o1, o2) -> o1.getMinute() - o2.getMinute());
        homeTeamGoals.setItems(homeClubGoals);
        awayTeamGoals.setItems(awayClubGoals);
        homeTeamScore.setText(String.valueOf(homeTeamGoals.getItems().size()));
        awayTeamScore.setText(String.valueOf(awayTeamGoals.getItems().size()));
    }

    public void addGoalHome() {
        Stage stage = new Stage();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/goal.fxml"), bundle);
            GoalController goalController = new GoalController(this.homePlayers, this.homeClubGoals, true); // dodati drugi argument
            loader.setController(goalController);
            Parent root = loader.load();
            stage.setTitle("Goal");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void addGoalAway() {
        Stage stage = new Stage();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/goal.fxml"), bundle);
            GoalController goalController = new GoalController(this.awayPlayers, this.awayClubGoals, false); // dodati drugi argument
            loader.setController(goalController);
            Parent root = loader.load();
            stage.setTitle("Goal");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void finishPressed() {
        ArrayList<Goal> hcG = new ArrayList<>(this.homeClubGoals);
        ArrayList<Goal> acG = new ArrayList<>(this.awayClubGoals);

        // upisivanje rezultata i golova
        Result r = new Result(this.homeClub, this.awayClub, hcG.size(), acG.size());
        dao.addResult(r);

        FxRobot robot = new FxRobot();
        ListView<Result> resultsList= robot.lookup("#resultsList").queryAs(ListView.class);
        resultsList.getItems().add(r);

        Result res = dao.findResult(r.getHomeTeam(), r.getAwayTeam());
        hcG.forEach(k -> k.setResult(res));
        hcG.forEach(k -> dao.addGoal(k));
        acG.forEach(k -> k.setResult(res));
        acG.forEach(k -> dao.addGoal(k));

        // izbacujemo kolo jer se utakmica odigrala
        Fixture fixture = dao.findFixture(homeClub.getName(), awayClub.getName());
        if (fixture!=null) {
            FxRobot robot2 = new FxRobot();
            ListView<Fixture> fixturesList = robot2.lookup("#fixturesList").queryAs(ListView.class);
            fixturesList.getItems().remove(fixture);
            dao.deleteFixture(this.homeClub.getName(), this.awayClub.getName());
        }

        Stats stat = new Stats();
        // sređivanje statistike
        for (int i=0; i<this.homePlayers.size(); i++) {
            if (this.homePlayers.get(i) instanceof Goalkeeper && awayClubGoals.isEmpty()) {
                stat.setId(this.homePlayers.get(i).getId());
                stat.setAppearances(dao.findStat(stat.getId()).getAppearances());
                stat.setCleanSheets(dao.findStat(stat.getId()).getCleanSheets() + 1);
                dao.editStat(stat);
            }
            stat.setId(this.homePlayers.get(i).getId());
            stat.setAppearances(dao.findStat(stat.getId()).getAppearances() + 1);
            stat.setCleanSheets(dao.findStat(stat.getId()).getCleanSheets());
            dao.editStat(stat);
        }

        for (int i=0; i<this.awayPlayers.size(); i++) {
            if (this.awayPlayers.get(i) instanceof Goalkeeper && homeClubGoals.isEmpty()) {
                stat.setId(this.awayPlayers.get(i).getId());
                stat.setAppearances(dao.findStat(stat.getId()).getAppearances());
                stat.setCleanSheets(dao.findStat(stat.getId()).getCleanSheets() + 1);
                dao.editStat(stat);
            }
            stat.setId(this.awayPlayers.get(i).getId());
            stat.setAppearances(dao.findStat(stat.getId()).getAppearances() + 1);
            stat.setCleanSheets(dao.findStat(stat.getId()).getCleanSheets());
            dao.editStat(stat);
        }


        // ažuriranje tabele
        FxRobot robot3 = new FxRobot();
        TableView<ClubOnTable> tableViewTable= robot3.lookup("#tableViewTable").queryAs(TableView.class);

        this.clubsOnTable.clear();
        tableViewTable.setItems(null);
        dao.clubs().forEach(k -> this.clubsOnTable.add(new ClubOnTable(k, dao.results())));
        this.clubsOnTable.sort((o1, o2) -> o2.compareTo(o1));
        for (int i=0; i<dao.clubs().size(); i++) {
            this.clubsOnTable.get(i).setPosition((i+1) + ".");
        }
        tableViewTable.setItems(this.clubsOnTable);
        tableViewTable.sort();

        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }


    public void cancelPressed() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
