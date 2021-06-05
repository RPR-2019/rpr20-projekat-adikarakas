package ba.unsa.etf.rpr.beans;

import com.mysql.cj.exceptions.WrongArgumentException;

import java.io.Serializable;

public class Result implements Serializable {
    private Club homeTeam;
    private Club awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int id;

    public Result(Club homeTeam, Club awayTeam, int homeTeamScore, int awayTeamScore) {
        setHomeTeam(homeTeam);
        setAwayTeam(awayTeam);
        setHomeTeamScore(homeTeamScore);
        setAwayTeamScore(awayTeamScore);
    }

    public Club getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Club homeTeam) {
        if (homeTeam.equals(this.awayTeam)) throw new WrongArgumentException("One team can't be home and away at same time");
        this.homeTeam = homeTeam;
    }

    public Club getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Club awayTeam) {
        if (awayTeam.equals(this.homeTeam)) throw new WrongArgumentException("One team can't be home and away at same time");
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        if (homeTeamScore<0) throw new IllegalArgumentException("Score can't be negative");
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        if (awayTeamScore<0) throw new IllegalArgumentException("Score can't be negative");
        this.awayTeamScore = awayTeamScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeTeamScore + " - " + awayTeamScore + " " + awayTeam;
    }
}
