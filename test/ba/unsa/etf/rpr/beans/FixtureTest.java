package ba.unsa.etf.rpr.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixtureTest {

    @Test
    public void constructor() {
        Club c1 = new Club("Chelsea");
        Club c2 = new Club("Arsenal");
        Fixture f1 = new Fixture(c1, c2);
        assertAll(
                () -> assertEquals(c1, f1.getHomeTeam()),
                () -> assertEquals(c2, f1.getAwayTeam())
        );
    }

    @Test
    public void stringTest() {
        Club c1 = new Club("Chelsea");
        Club c2 = new Club("Arsenal");
        Fixture f1 = new Fixture(c1, c2);
        assertEquals("Chelsea vs Arsenal", f1.toString());
    }

    @Test
    public void getterAndSetter() {
        Club c1 = new Club("Chelsea");
        Club c2 = new Club("Arsenal");
        Club c3 = new Club("Liverpool");
        Fixture f1 = new Fixture(c1, c2);
        f1.setHomeTeam(c3);
        f1.setAwayTeam(c1);
        assertEquals("Liverpool vs Chelsea", f1.toString());
    }

    @Test
    public void equality() {
        Club c1 = new Club("Chelsea");
        Club c2 = new Club("Arsenal");
        Fixture f1 = new Fixture(c1, c2);
        Fixture f2 = new Fixture(c2, c1);
        Fixture f3 = new Fixture(c1, c2);
        assertFalse(f1.equals(f2));
        assertTrue(f1.equals(f3));
    }

}