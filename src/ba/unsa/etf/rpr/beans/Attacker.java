package ba.unsa.etf.rpr.beans;

import java.time.LocalDate;

public class Attacker extends Player {
    public Attacker(String name, String surname, LocalDate birth, String nationality) {
        super(name, surname, birth, nationality);
    }
}
