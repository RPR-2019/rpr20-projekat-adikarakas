package ba.unsa.etf.rpr.beans;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void constructor() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-07-03"), "Bosna i Hercegovina");
        assertAll(
                () -> assertEquals( "Asmir", gk.getName()),
                () -> assertEquals( "Begović", gk.getSurname()),
                () -> assertEquals( "1987-07-03", gk.getBirth().toString()),
                () -> assertEquals("Bosna i Hercegovina", gk.getNationality())
        );
    }

    @Test
    public void position() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-03-27"), "Bosna i Hercegovina");
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Midfielder mid = new Midfielder("Miralem", "Pjanić", LocalDate.parse("1991-04-24"), "Bosna i Hercegovina");
        Attacker att = new Attacker("Edin", "Džeko", LocalDate.parse("1986-03-22"), "Bosna i Hercegovina");
        assertAll(
                () -> assertEquals("Goalkeeper", gk.getClass().getSimpleName()),
                () -> assertEquals("Defender", def.getClass().getSimpleName()),
                () -> assertEquals("Midfielder", mid.getClass().getSimpleName()),
                () -> assertEquals("Attacker", att.getClass().getSimpleName())
        );
    }

    @Test
    public void getAndSet() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-04-25"), "Bosna i Hercegovina");
        gk.setName("Joe");
        gk.setSurname("Hart");
        gk.setBirth(LocalDate.parse("1985-03-27"));
        gk.setNationality("England");
        assertAll(
                () -> assertEquals( "Joe", gk.getName()),
                () -> assertEquals( "Hart", gk.getSurname()),
                () -> assertEquals( "1985-03-27", gk.getBirth().toString()),
                () -> assertEquals("England", gk.getNationality())
        );
    }

    @Test
    public void stringTest() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-04-25"), "Bosna i Hercegovina");
        assertEquals("Asmir Begović", gk.toString());
    }

    @Test
    public void comparing() {
        Goalkeeper gk1 = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-04-25"), "Bosna i Hercegovina");
        Goalkeeper gk2 = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-04-25"), "Bosna i Hercegovina");
        Goalkeeper gk3 = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-04-25"), "Bosna i Hercegovina");
        Club c1 = new Club("Bournemouth");
        Club c2 = new Club("Burnley");
        gk1.setClub(c1);
        gk2.setClub(c1);
        gk3.setClub(c2);
        assertAll(
                () -> assertTrue(gk1.equals(gk2)),
                () -> assertFalse(gk2.equals(gk3))
        );
    }

}