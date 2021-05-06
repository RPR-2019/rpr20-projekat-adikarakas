package ba.unsa.etf.rpr;

import java.time.LocalDate;


public class Player {
    public String name;
    public String surname;
    public LocalDate birth;
    public String nationality;

    public Player(String name, String surname, LocalDate birth, String nationality) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        if (name.trim().isEmpty()) return surname;
        else if (surname.trim().isEmpty()) return name;
        else return (name + " " + surname);
    }
}
