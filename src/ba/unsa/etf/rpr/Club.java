package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class Club {
    public String name;
    public ArrayList<Player> players;
    public int wins;
    public int draws;
    public int losses;
    public int goalsScored;
    public int goalsConceded;

    public Club(String name) {
        this.name = name;
        this.players = new ArrayList<Player>(0);
        this.wins=0;
        this.draws=0;
        this.losses=0;
        this.goalsConceded=0;
        this.goalsScored=0;
    }

    public int getPoints(int wins, int draws) {
        return wins*3+draws;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

}
