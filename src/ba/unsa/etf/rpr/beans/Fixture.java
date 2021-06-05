package ba.unsa.etf.rpr.beans;

import com.mysql.cj.exceptions.WrongArgumentException;

import java.io.Serializable;
import java.util.Objects;

public class Fixture implements Serializable {
    private Club homeTeam;
    private Club awayTeam;

    public Fixture(Club homeTeam, Club awayTeam) {
        setHomeTeam(homeTeam);
        setAwayTeam(awayTeam);
    }

    public Club getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Club homeTeam) {
        if (homeTeam.equals(this.awayTeam)) throw new WrongArgumentException("One club can't be home and away team at same time");
        this.homeTeam = homeTeam;
    }

    public Club getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Club awayTeam) {
        if (awayTeam.equals(this.homeTeam)) throw new WrongArgumentException("One club can't be home and away team at same time");
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

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }
}
