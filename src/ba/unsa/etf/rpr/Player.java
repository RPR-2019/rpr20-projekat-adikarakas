package ba.unsa.etf.rpr;

import java.util.Date;

public class Player {
    public String name;
    public String surname;
    public Date birth;
    public Position position;

    public Player(String name, String surname, Date birth, Position position) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.position = position;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
