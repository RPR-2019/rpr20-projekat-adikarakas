package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class ClubOnTable implements Comparable<ClubOnTable> {
    private Club club;
    private String position;
    private int played, wins, draws, losses, goalsScored, goalsConceded, goalDifference, points;
    private ArrayList<Result> results;

    public ClubOnTable(Club club, ArrayList<Result> results) {
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
            for (int i = 0; i < results.size(); i++) {
                if (club.equals(results.get(i).getHomeTeam()) || club.equals(results.get(i).getAwayTeam())) {
                    this.played++;
                    if (club.equals(results.get(i).getHomeTeam())) {
                        this.goalsScored += results.get(i).getHomeTeamScore();
                        this.goalsConceded += results.get(i).getAwayTeamScore();
                        if (results.get(i).getHomeTeamScore() > results.get(i).getAwayTeamScore())
                            this.wins++;
                        else if (results.get(i).getHomeTeamScore() == results.get(i).getAwayTeamScore())
                            this.draws++;
                        else this.losses++;
                    } else {
                        this.goalsScored += results.get(i).getAwayTeamScore();
                        this.goalsConceded += results.get(i).getHomeTeamScore();
                        if (results.get(i).getHomeTeamScore() < results.get(i).getAwayTeamScore())
                            this.wins++;
                        else if (results.get(i).getHomeTeamScore() == results.get(i).getAwayTeamScore())
                            this.draws++;
                        else this.losses++;
                    }
                    this.goalDifference = this.goalsScored - this.goalsConceded;
                }
            }
            this.points = this.wins * 3 + this.draws;
        }
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ArrayList<Result> getresults() {
        return results;
    }

    public void setresults(ArrayList<Result> results) {
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
}
