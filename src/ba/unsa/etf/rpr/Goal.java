package ba.unsa.etf.rpr;

public class Goal {
    private Player scorer;
    private Player assistent;
    private GoalType goalType;
    private GoalSituation goalSituation;
    private GoalDistance goalDistance;
    private int minute;

    public Goal(Player scorer, Player assistent, int minute, GoalType goalType, GoalSituation goalSituation, GoalDistance goalDistance) {
        this.scorer = scorer;
        this.assistent = assistent;
        this.minute = minute;
        this.goalType = goalType;
        this.goalSituation = goalSituation;
        this.goalDistance = goalDistance;
    }

    public Goal(Player scorer, int minute, GoalType goalType, GoalSituation goalSituation, GoalDistance goalDistance) {
        this.scorer = scorer;
        this.assistent = null;
        this.minute = minute;
        this.goalType = goalType;
        this.goalSituation = goalSituation;
        this.goalDistance = goalDistance;
    }

    public Player getScorer() {
        return scorer;
    }

    public void setScorer(Player scorer) {
        this.scorer = scorer;
    }

    public Player getAssistent() {
        return assistent;
    }

    public void setAssistent(Player assistent) {
        this.assistent = assistent;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public GoalSituation getGoalSituation() {
        return goalSituation;
    }

    public void setGoalSituation(GoalSituation goalSituation) {
        this.goalSituation = goalSituation;
    }

    public GoalDistance getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(GoalDistance goalDistance) {
        this.goalDistance = goalDistance;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        if (assistent!=null) return minute + "' " + scorer.getName().charAt(0) + ". " + scorer.getSurname() + " (" + assistent.getName().charAt(0) + ". " + assistent.getSurname() + ")";
        else return minute + "' " + scorer.getName().charAt(0) + ". " + scorer.getSurname();
    }
}
