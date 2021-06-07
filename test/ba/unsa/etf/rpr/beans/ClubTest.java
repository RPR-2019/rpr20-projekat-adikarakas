package ba.unsa.etf.rpr.beans;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClubTest {

    @Test
    void addingAndRemovingPlayers() {
        Club c = new Club("Chelsea");
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-03-27"), "Bosna i Hercegovina");
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Midfielder mid = new Midfielder("Miralem", "Pjanić", LocalDate.parse("1991-04-24"), "Bosna i Hercegovina");
        Attacker att = new Attacker("Edin", "Džeko", LocalDate.parse("1986-03-22"), "Bosna i Hercegovina");
        c.getPlayers().add(gk);
        c.getPlayers().add(def);
        c.getPlayers().add(mid);
        c.getPlayers().add(att);
        c.getPlayers().remove(def);
        assertEquals(3, c.getPlayers().size());
    }

    @Test
    void constructor() {
        Club c = new Club("Chelsea");
        assertAll(
                () -> assertEquals("Chelsea", c.toString()),
                () -> assertEquals("Chelsea", c.getName()),
                () -> assertEquals("", c.getManager()),
                () -> assertEquals("", c.getMascot()),
                () -> assertEquals("", c.getNickname()),
                () -> assertEquals(Color.WHITE, c.getColor()),
                () -> assertNull(c.getCaptain()),
                () -> assertTrue(c.getPlayers().isEmpty())
        );
    }

    @Test
    void getterAndSetter() {
        Club c = new Club("Chelsea");
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-03-27"), "Bosna i Hercegovina");
        c.setManager("Thomas Tuchel");
        c.setMascot("Maskota Chelseaja");
        c.setNickname("The Pensioners");
        c.setStadium("Stamford Bridge");
        c.setColor(Color.ROYALBLUE);
        c.getPlayers().add(gk);
        c.setCaptain(gk);
        assertAll(
                () -> assertEquals("Chelsea", c.getName()),
                () -> assertEquals("Thomas Tuchel", c.getManager()),
                () -> assertEquals("Maskota Chelseaja", c.getMascot()),
                () -> assertEquals("The Pensioners", c.getNickname()),
                () -> assertEquals("Stamford Bridge", c.getStadium()),
                () -> assertEquals(Color.ROYALBLUE, c.getColor()),
                () -> assertNotNull(c.getCaptain()),
                () -> assertEquals(gk, c.getCaptain()),
                () -> assertEquals(1, c.getPlayers().size()),
                () -> assertFalse(c.getPlayers().isEmpty())
        );
    }

}