package ba.unsa.etf.rpr.beans;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsTest {

    @Test
    public void constructor() {
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Club c = new Club("Arsenal");
        PlayerStats ps = new PlayerStats(def, c, 2);
        assertAll(
                () -> assertEquals(def, ps.getName()),
                () -> assertEquals(c, ps.getClub()),
                () -> assertEquals(2, ps.getStat()),
                () -> assertEquals("", ps.getRank())
        );
    }

    @Test
    public void getterAndSetter() {
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Club c = new Club("Arsenal");
        Defender def2 = new Defender("Siniša", "Saničanin", LocalDate.parse("1992-02-12"), "Bosna i Hercegovina");
        Club c2 = new Club("Liverpool");
        PlayerStats ps = new PlayerStats(def, c, 2);
        ps.setClub(c2);
        ps.setName(def2);
        ps.setStat(1);
        ps.setRank("1.");
        assertAll(
                () -> assertEquals("1.", ps.getRank()),
                () -> assertEquals(def2, ps.getName()),
                () -> assertEquals(c2, ps.getClub()),
                () -> assertEquals(1, ps.getStat())
        );
    }

}