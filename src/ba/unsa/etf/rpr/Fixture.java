package ba.unsa.etf.rpr;

import java.util.Objects;

public class Fixture {
    private Club homeTeam, awayTeam;

    public Fixture(Club homeTeam, Club awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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

    @Override
    public String toString() {
        return homeTeam + " vs " + awayTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fixture fixture = (Fixture) o;
        return Objects.equals(homeTeam, fixture.homeTeam) &&
                Objects.equals(awayTeam, fixture.awayTeam);
    }

}
