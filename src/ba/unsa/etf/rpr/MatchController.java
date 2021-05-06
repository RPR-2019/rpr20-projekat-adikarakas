package ba.unsa.etf.rpr;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Comparator;

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
    public TextField homeTeamName;
    public TextField awayTeamName;
    private ArrayList<Player> homePlayers, awayPlayers;
    private ObservableList<Goal> homeClubGoals = FXCollections.observableArrayList();
    private ObservableList<Goal> awayClubGoals = FXCollections.observableArrayList();
    private League league;
    private ObservableList<ClubOnTable> clubsOnTable;

    MatchController (League l, Club c1, Club c2, ArrayList<Player> p1, ArrayList<Player> p2, ObservableList<ClubOnTable> clubsOnTable) {
        this.homeClub=c1;
        this.awayClub=c2;
        this.homePlayers=p1;
        this.awayPlayers=p2;
        this.league=l;
        this.clubsOnTable=clubsOnTable;
    }

    @FXML
    public void initialize() {
        homeTeamName.setText(this.homeClub.getName());
        awayTeamName.setText(this.awayClub.getName());
        homeClubGoals.sort(new Comparator<Goal>() {
            @Override
            public int compare(Goal o1, Goal o2) {
                return o1.getMinute()-o2.getMinute();
            }
        });
        awayClubGoals.sort(new Comparator<Goal>() {
            @Override
            public int compare(Goal o1, Goal o2) {
                return o1.getMinute()-o2.getMinute();
            }
        });
        homeTeamGoals.setItems(homeClubGoals);
        awayTeamGoals.setItems(awayClubGoals);
        homeTeamScore.setText(String.valueOf(homeTeamGoals.getItems().size())); // vjerovatno će trebati
        awayTeamScore.setText(String.valueOf(awayTeamGoals.getItems().size())); // dodati neki listener
    }

    public void addGoalHome(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/goal.fxml"));
            GoalController goalController = new GoalController(this.homePlayers, this.homeClubGoals, true); // dodati drugi argument
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
            GoalController goalController = new GoalController(this.awayPlayers, this.awayClubGoals, false); // dodati drugi argument
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
        ArrayList<Goal> hcG = new ArrayList<>(this.homeClubGoals);
        ArrayList<Goal> acG = new ArrayList<>(this.awayClubGoals);
        Match m = new Match(this.homeClub, this.awayClub, hcG, acG);
        this.league.getMatches().add(m);
        FxRobot robot = new FxRobot();
        ListView resultsList= robot.lookup("#resultsList").queryAs(ListView.class);
        resultsList.getItems().add(m);
        for (int i=0; i<this.league.getFixtures().size(); i++) {
            if (this.league.getFixtures().get(i).getHomeTeam().equals(this.homeClub) && this.league.getFixtures().get(i).getAwayTeam().equals(this.awayClub)) {
                FxRobot robot2 = new FxRobot();
                ListView fixturesList= robot2.lookup("#fixturesList").queryAs(ListView.class);
                fixturesList.getItems().remove(this.league.getFixtures().get(i));
                this.league.getFixtures().remove(this.league.getFixtures().get(i));
            }
        }

        FxRobot robot3 = new FxRobot();
        TableView tableViewTable= robot3.lookup("#tableViewTable").queryAs(TableView.class);

        this.clubsOnTable.clear();
        tableViewTable.setItems(null);
        for (int i=0; i<this.league.getNumberOfClubs(); i++) {
            this.clubsOnTable.add(new ClubOnTable(this.league.getClubs().get(i), this.league.getMatches()));
        }
        this.clubsOnTable.sort(new Comparator<ClubOnTable>() {
            @Override
            public int compare(ClubOnTable o1, ClubOnTable o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i=0; i<this.league.getNumberOfClubs(); i++) {
            this.clubsOnTable.get(i).setPosition((i+1) + ".");
        }
        tableViewTable.setItems(this.clubsOnTable);
        tableViewTable.sort();

        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }


    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
