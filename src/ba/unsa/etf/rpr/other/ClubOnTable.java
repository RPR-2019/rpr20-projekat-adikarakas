package ba.unsa.etf.rpr.other;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Result;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ClubOnTable implements Comparable<ClubOnTable> {
    private Club club;
    private String position;
    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;
    private int goalDifference;
    private int points;
    private List<Result> results;

    public ClubOnTable(Club club, List<Result> results) {
        this.club = club;
        this.results = results;
        this.played =0;
        this.wins=0;
        this.draws=0;
        this.losses=0;
        this.goalsScored=0;
        this.goalsConceded=0;
        this.goalDifference=0;
        this.points=0;
        this.position="";
        if (results!=null) {
            IntStream.range(0, results.size()).forEach(i -> {
                if (club.equals(results.get(i).getHomeTeam())) {
                    setForHomeTeam(results.get(i));
                } else if (club.equals(results.get(i).getAwayTeam())) {
                    setForAwayTeam(results.get(i));
                }
            });
            this.goalDifference = this.goalsScored - this.goalsConceded;
            this.points = this.wins * 3 + this.draws;
        }
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Result> getresults() {
        return results;
    }

    public void setresults(List<Result> results) {
        this.results = results;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setForHomeTeam (Result r) {
        this.played++;
        this.goalsScored += r.getHomeTeamScore();
        this.goalsConceded += r.getAwayTeamScore();
        if (r.getHomeTeamScore() > r.getAwayTeamScore()) this.wins++;
        else if (r.getHomeTeamScore() == r.getAwayTeamScore()) this.draws++;
        else this.losses++;
    }

    public void setForAwayTeam (Result r) {
        this.played++;
        this.goalsScored += r.getAwayTeamScore();
        this.goalsConceded += r.getHomeTeamScore();
        if (r.getHomeTeamScore() < r.getAwayTeamScore()) this.wins++;
        else if (r.getHomeTeamScore() == r.getAwayTeamScore()) this.draws++;
        else this.losses++;
    }

    @Override
    public int compareTo(ClubOnTable c) {
        if (this.getPoints()>c.getPoints()) return 1;
        else if (this.getPoints()==c.getPoints()) {
            if (this.getGoalDifference()>c.getGoalDifference()) return 1;
            else if (this.getGoalDifference()==c.getGoalDifference()) {
                if (this.getGoalsScored()>c.getGoalsScored()) return 1;
                else if (this.getGoalsScored()==c.getGoalsScored()) {
                    if (this.getWins()>c.getWins()) return 1;
                    else if (this.getWins()==c.getWins()) return (-1) * this.getClub().getName().compareTo(c.getClub().getName());
                }
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubOnTable that = (ClubOnTable) o;
        return played == that.played &&
                wins == that.wins &&
                draws == that.draws &&
                losses == that.losses &&
                goalsScored == that.goalsScored &&
                goalsConceded == that.goalsConceded &&
                goalDifference == that.goalDifference &&
                points == that.points &&
                Objects.equals(club, that.club) &&
                Objects.equals(position, that.position) &&
                Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(club, position, played, wins, draws, losses, goalsScored, goalsConceded, goalDifference, points, results);
    }
}
