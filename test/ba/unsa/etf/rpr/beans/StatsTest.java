package ba.unsa.etf.rpr.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    @Test
    void constructor() {
        Stats s = new Stats(1);
        assertAll(
                () -> assertEquals(1, s.getId()),
                () -> assertEquals(0, s.getAppearances()),
                () -> assertEquals(0, s.getCleanSheets())
        );
    }

    @Test
    void getterAndSetter() {
        Stats s = new Stats();
        s.setId(1);
        s.setAppearances(2);
        s.setCleanSheets(1);
        assertAll(
                () -> assertEquals(1, s.getId()),
                () -> assertEquals(2, s.getAppearances()),
                () -> assertEquals(1, s.getCleanSheets())
        );
    }

}