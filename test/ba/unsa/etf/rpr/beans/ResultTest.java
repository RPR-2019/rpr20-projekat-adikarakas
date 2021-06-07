package ba.unsa.etf.rpr.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void constructor() {
        Club c1 = new Club("Chelsea");
        Club c2 = new Club("Arsenal");
        Result r = new Result(c1, c2, 1, 0);
        assertAll(
                () -> assertEquals(1, r.getHomeTeamScore()),
                () -> assertEquals(0, r.getAwayTeamScore()),
                () -> assertEquals(c1, r.getHomeTeam()),
                () -> assertEquals(c2, r.getAwayTeam())
        );
    }

    @Test
    void output() {
        Club c1 = new Club ("Chelsea");
        Club c2 = new Club ("Arsenal");
        Result r1 = new Result (c1, c2, 1, 0);
        Result r2 = new Result (c1, c2, 5, 4);
        Result r3 = new Result (c2, c1, 0, 0);
        assertAll(
                () -> assertEquals("Chelsea 1 - 0 Arsenal", r1.toString()),
                () -> assertEquals("Chelsea 5 - 4 Arsenal", r2.toString()),
                () -> assertEquals("Arsenal 0 - 0 Chelsea", r3.toString())
        );
    }

    @Test
    void getterAndSetter() {
        Club c1 = new Club("Chelsea");
        Club c2 = new Club("Liverpool");
        Club c3 = new Club("Arsenal");
        Result r = new Result(c1, c2, 1, 0);
        r.setHomeTeam(c3);
        r.setAwayTeam(c1);
        r.setHomeTeamScore(2);
        r.setAwayTeamScore(2);
        assertAll(
                () -> assertEquals(c3, r.getHomeTeam()),
                () -> assertEquals(c1, r.getAwayTeam()),
                () -> assertEquals(2, r.getHomeTeamScore()),
                () -> assertEquals(2, r.getAwayTeamScore())
        );
    }

}