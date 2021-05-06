package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class Match {
    private Club homeTeam, awayTeam;
    private ArrayList<Goal> homeTeamGoals, awayTeamGoals;

    public Match(Club homeTeam, Club awayTeam, ArrayList<Goal> homeTeamGoals, ArrayList<Goal> awayTeamGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public Club getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Club homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Club getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Club awayTeam) {
        this.awayTeam = awayTeam;
    }

    public ArrayList<Goal> getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(ArrayList<Goal> homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public ArrayList<Goal> getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(ArrayList<Goal> awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeTeamGoals.size() + " - " + awayTeamGoals.size() + " " + awayTeam;
    }
}
