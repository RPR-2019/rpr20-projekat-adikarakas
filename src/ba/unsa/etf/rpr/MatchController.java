package ba.unsa.etf.rpr;

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
    private LeagueDAO dao;
    private ObservableList<ClubOnTable> clubsOnTable;

    MatchController (Club c1, Club c2, ArrayList<Player> p1, ArrayList<Player> p2, ObservableList<ClubOnTable> clubsOnTable) {
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
        Match m = new Match(this.homeClub, this.awayClub, hcG, acG, this.homePlayers, this.awayPlayers);

        // upisivanje rezultata i golova
        dao.getInstance().addResult(new Result(this.homeClub, this.awayClub, hcG.size(), acG.size()));
        for (int i=0; i<hcG.size(); i++) {
            dao.getInstance().addGoal(hcG.get(i));
        }
        for (int i=0; i<acG.size(); i++) {
            dao.getInstance().addGoal(acG.get(i));
        }
        FxRobot robot = new FxRobot();
        ListView resultsList= robot.lookup("#resultsList").queryAs(ListView.class);
        resultsList.getItems().add(m);


        // izbacujemo kolo jer se utakmica odigrala
        Fixture fixture = dao.getInstance().findFixture(homeClub.getName(), awayClub.getName());
        if (fixture!=null) {
            FxRobot robot2 = new FxRobot();
            ListView fixturesList = robot2.lookup("#fixturesList").queryAs(ListView.class);
            fixturesList.getItems().remove(fixture);
            dao.getInstance().deleteFixture(this.homeClub.getName(), this.awayClub.getName());
        }

        // sređivanje statistike
        if (homeClubGoals.size()==0) {
            for (int i=0; i<this.awayPlayers.size(); i++) {
                if (this.awayPlayers.get(i) instanceof Goalkeeper) {
                    Stats stat = new Stats(this.awayPlayers.get(i).getId());
                    stat.setApperances(dao.getInstance().findStat(stat.getId()).getApperances());
                    stat.setCleanSheets(dao.getInstance().findStat(stat.getId()).getCleanSheets() + 1);
                    dao.getInstance().editStat(stat);
                }
            }
        }
        if (awayClubGoals.size()==0) {
            for (int i=0; i<this.homePlayers.size(); i++) {
                if (this.homePlayers.get(i) instanceof Goalkeeper) {
                    Stats stat = new Stats(this.homePlayers.get(i).getId());
                    stat.setApperances(dao.getInstance().findStat(stat.getId()).getApperances());
                    stat.setCleanSheets(dao.getInstance().findStat(stat.getId()).getCleanSheets() + 1);
                    dao.getInstance().editStat(stat);
                }
            }
        }
        for (int i=0; i<this.homePlayers.size(); i++) {
            Stats stat = new Stats(this.homePlayers.get(i).getId());
            stat.setApperances(dao.getInstance().findStat(stat.getId()).getApperances() + 1);
            stat.setCleanSheets(dao.getInstance().findStat(stat.getId()).getCleanSheets());
            dao.getInstance().editStat(stat);
        }
        for (int i=0; i<this.awayPlayers.size(); i++) {
            Stats stat = new Stats(this.awayPlayers.get(i).getId());
            stat.setApperances(dao.getInstance().findStat(stat.getId()).getApperances() + 1);
            stat.setCleanSheets(dao.getInstance().findStat(stat.getId()).getCleanSheets());
            dao.getInstance().editStat(stat);
        }


        // ažuriranje tabele
        FxRobot robot3 = new FxRobot();
        TableView tableViewTable= robot3.lookup("#tableViewTable").queryAs(TableView.class);

        this.clubsOnTable.clear();
        tableViewTable.setItems(null);
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            this.clubsOnTable.add(new ClubOnTable(dao.getInstance().clubs().get(i), dao.getInstance().results()));
        }
        this.clubsOnTable.sort(new Comparator<ClubOnTable>() {
            @Override
            public int compare(ClubOnTable o1, ClubOnTable o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
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
