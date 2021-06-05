package ba.unsa.etf.rpr.beans;

import java.io.Serializable;

public class Stats implements Serializable {
    private int id;
    private int appearances;
    private int cleanSheets;

    public Stats(int id) {
        this.id = id;
        this.appearances = 0;
        this.cleanSheets = 0;
    }

    public Stats() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppearances() {
        return appearances;
    }

    public void setAppearances(int apperances) {
        this.appearances = apperances;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(int cleanSheets) {
        this.cleanSheets = cleanSheets;
    }
}
