package ba.unsa.etf.rpr;

public class Stats {
    private int id;
    private int apperances;
    private int cleanSheets;

    public Stats(int id) {
        this.id = id;
        this.apperances = 0;
        this.cleanSheets = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApperances() {
        return apperances;
    }

    public void setApperances(int apperances) {
        this.apperances = apperances;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(int cleanSheets) {
        this.cleanSheets = cleanSheets;
    }
}
