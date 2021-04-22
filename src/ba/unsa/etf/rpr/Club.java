package ba.unsa.etf.rpr;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Club {
    private String name;
    private String nickname="";
    private String stadium="";
    private String mascot="";
    private Color color;
    private String manager="";
    private ArrayList<Player> players;

    public Club(String name) {
        this.name = name;
        this.players = new ArrayList<Player>(0);
        this.color=Color.WHITE;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    @Override
    public String toString() {
        return name;
    }
}
