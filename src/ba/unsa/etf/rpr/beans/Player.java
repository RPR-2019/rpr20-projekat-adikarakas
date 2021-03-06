package ba.unsa.etf.rpr.beans;

import ba.unsa.etf.rpr.other.WrongPositionException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Player implements Serializable {
    private int id;
    private String name;
    private String surname;
    private LocalDate birth;
    private String nationality;
    private Club club;
    private String position;

    public Player(String name, String surname, LocalDate birth, String nationality) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.nationality = nationality;
        this.club=null;
        this.position="";
        this.id=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (!position.equals(this.getClass().getSimpleName())) throw new WrongPositionException("Wrong position set");
        this.position = position;
    }

    @Override
    public String toString() {
        if (name.isBlank()) return surname;
        else if (surname.isBlank()) return name;
        else return (name + " " + surname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                Objects.equals(name, player.name) &&
                Objects.equals(surname, player.surname) &&
                Objects.equals(birth, player.birth) &&
                Objects.equals(nationality, player.nationality) &&
                Objects.equals(club, player.club) &&
                Objects.equals(position, player.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birth, nationality, club, position);
    }
}
