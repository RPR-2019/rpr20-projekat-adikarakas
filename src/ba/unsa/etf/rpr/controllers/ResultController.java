package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Goal;
import ba.unsa.etf.rpr.beans.Result;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResultController {
    private final Club homeClub;
    private final Club awayClub;
    public TextField homeTeamScore;
    public TextField awayTeamScore;
    public ListView<Goal> homeTeamGoals = new ListView<>();
    public ListView<Goal> awayTeamGoals = new ListView<>();
    public Button cancelButton;
    public TextField homeTeamName;
    public TextField awayTeamName;
    private final LeagueDAO dao;
    private final Result r;

    ResultController(Result r) {
        dao= LeagueDAO.getInstance();
        this.r=r;
        this.homeClub=r.getHomeTeam();
        this.awayClub=r.getAwayTeam();
    }

    public void initialize() {
        if (this.r != null) {
            homeTeamName.setText(this.homeClub.getName());
            awayTeamName.setText(this.awayClub.getName());
            homeTeamGoals.setItems(dao.homeTeamGoalsCollecting(this.r));
            awayTeamGoals.setItems(dao.awayTeamGoalsCollecting(this.r));
            homeTeamScore.setText(String.valueOf(homeTeamGoals.getItems().size()));
            awayTeamScore.setText(String.valueOf(awayTeamGoals.getItems().size()));
        }
    }

    public void cancelPressed() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
