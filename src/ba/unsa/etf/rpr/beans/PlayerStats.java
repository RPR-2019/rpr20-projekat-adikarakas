package ba.unsa.etf.rpr.beans;

public class PlayerStats {
    private String rank;
    private Player name;
    private Club club;
    private int stat;

    public PlayerStats (Player name, Club club, int stat) {
        this.name = name;
        this.club = club;
        setStat(stat);
        this.rank="";
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Player getName() {
        return name;
    }

    public void setName(Player name) {
        this.name = name;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
