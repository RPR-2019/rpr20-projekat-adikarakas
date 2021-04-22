package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.util.Date;

public class Player {
    public String name;
    public String surname;
    public LocalDate birth;
    public Position position;
    public String nationality;

    public Player(String name, String surname, LocalDate birth, String nationality, Position position) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.position = position;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return (name + " " + surname);
    }
}
