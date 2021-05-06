package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class ClubOnTable implements Comparable<ClubOnTable> {
    private Club club;
    private String position;
    private int played, wins, draws, losses, goalsScored, goalsConceded, goalDifference, points;
    private ArrayList<Match> matches;

    public ClubOnTable(Club club, ArrayList<Match> matches) {
        this.club = club;
        this.matches = matches;
        this.played =0;
        this.wins=0;
        this.draws=0;
        this.losses=0;
        this.goalsScored=0;
        this.goalsConceded=0;
        this.goalDifference=0;
        this.points=0;
        this.position="";
        if (matches!=null) {
            for (int i = 0; i < matches.size(); i++) {
                if (club.equals(matches.get(i).getHomeTeam()) || club.equals(matches.get(i).getAwayTeam())) {
                    this.played++;
                    if (club.equals(matches.get(i).getHomeTeam())) {
                        this.goalsScored += matches.get(i).getHomeTeamGoals().size();
                        this.goalsConceded += matches.get(i).getAwayTeamGoals().size();
                        if (matches.get(i).getHomeTeamGoals().size() > matches.get(i).getAwayTeamGoals().size())
                            this.wins++;
                        else if (matches.get(i).getHomeTeamGoals().size() == matches.get(i).getAwayTeamGoals().size())
                            this.draws++;
                        else this.losses++;
                    } else {
                        this.goalsScored += matches.get(i).getAwayTeamGoals().size();
                        this.goalsConceded += matches.get(i).getHomeTeamGoals().size();
                        if (matches.get(i).getHomeTeamGoals().size() < matches.get(i).getAwayTeamGoals().size())
                            this.wins++;
                        else if (matches.get(i).getHomeTeamGoals().size() == matches.get(i).getAwayTeamGoals().size())
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

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
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
