package ba.unsa.etf.rpr.beans;

import ba.unsa.etf.rpr.other.GoalDistance;
import ba.unsa.etf.rpr.other.GoalSituation;
import ba.unsa.etf.rpr.other.GoalType;
import com.mysql.cj.exceptions.WrongArgumentException;

import java.io.Serializable;

public class Goal implements Serializable {
    private Player scorer;
    private Player assistent;
    private GoalType goalType;
    private GoalSituation goalSituation;
    private GoalDistance goalDistance;
    private int minute;
    private Result result;
    
    private static final String SAMEPERSONMESSAGE = "Scorer and assistent shouldn't be same person";
    private static final String OUTSIDEPENALTYMESSAGE = "You can't scorer penalty from outside box";
    private static final String INSIDEMESSAGE = "You can't score free kick from inside box";
    private static final String ASSISTMESSAGE = "You can get assist only for open play goals";

    public Goal(Player scorer, Player assistent, int minute, GoalType goalType, GoalSituation goalSituation, GoalDistance goalDistance) {
        if (minute < 0 || minute > 90) throw new IllegalArgumentException("Minute should be between 0 and 90!");
        if (scorer.equals(assistent)) throw new WrongArgumentException(SAMEPERSONMESSAGE);
        if (!goalSituation.equals(GoalSituation.OPENPLAY) && assistent!=null) throw new WrongArgumentException(ASSISTMESSAGE);
        if (goalSituation.equals(GoalSituation.PENALTY) && goalDistance.equals(GoalDistance.OUTSIDEBOX)) throw new WrongArgumentException(OUTSIDEPENALTYMESSAGE);
        if (goalSituation.equals(GoalSituation.FREEKICK) && goalDistance.equals(GoalDistance.INSIDEBOX)) throw new WrongArgumentException(INSIDEMESSAGE);
        this.scorer = scorer;
        this.assistent = assistent;
        this.minute = minute;
        this.goalType = goalType;
        this.goalSituation = goalSituation;
        this.goalDistance = goalDistance;
    }

    public Goal(Player scorer, int minute, GoalType goalType, GoalSituation goalSituation, GoalDistance goalDistance) {
        if (minute < 0 || minute > 90) throw new IllegalArgumentException("Minute should be between 0 and 90!");
        if (goalSituation.equals(GoalSituation.PENALTY) && goalDistance.equals(GoalDistance.OUTSIDEBOX)) throw new WrongArgumentException(OUTSIDEPENALTYMESSAGE);
        if (goalSituation.equals(GoalSituation.FREEKICK) && goalDistance.equals(GoalDistance.INSIDEBOX)) throw new WrongArgumentException(INSIDEMESSAGE);
        this.scorer=scorer;
        this.assistent = null;
        this.minute=minute;
        this.goalType = goalType;
        this.goalSituation=goalSituation;
        this.goalDistance=goalDistance;
    }

    public Player getScorer() {
        return scorer;
    }

    public void setScorer(Player scorer) {
        if (this.scorer.equals(assistent)) throw new WrongArgumentException(SAMEPERSONMESSAGE);
        this.scorer = scorer;
    }

    public Player getAssistent() {
        return assistent;
    }

    public void setAssistent(Player assistent) {
        if (this.scorer.equals(assistent)) throw new WrongArgumentException(SAMEPERSONMESSAGE);
        if (!goalSituation.equals(GoalSituation.OPENPLAY) && assistent!=null) throw new WrongArgumentException(ASSISTMESSAGE);
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
        if (goalSituation.equals(GoalSituation.PENALTY) && goalDistance.equals(GoalDistance.OUTSIDEBOX)) throw new WrongArgumentException(OUTSIDEPENALTYMESSAGE);
        if (!goalSituation.equals(GoalSituation.OPENPLAY) && assistent!=null) throw new WrongArgumentException(ASSISTMESSAGE);
        if (goalSituation.equals(GoalSituation.FREEKICK) && goalDistance.equals(GoalDistance.INSIDEBOX)) throw new WrongArgumentException(INSIDEMESSAGE);
        this.goalSituation = goalSituation;
    }

    public GoalDistance getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(GoalDistance goalDistance) {
        if (goalSituation.equals(GoalSituation.PENALTY) && goalDistance.equals(GoalDistance.OUTSIDEBOX)) throw new WrongArgumentException(OUTSIDEPENALTYMESSAGE);
        if (goalSituation.equals(GoalSituation.FREEKICK) && goalDistance.equals(GoalDistance.INSIDEBOX)) throw new WrongArgumentException(INSIDEMESSAGE);
        this.goalDistance = goalDistance;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        if (minute > 90 || minute < 0) throw new IllegalArgumentException("Minute should be between 0 and 90");
        this.minute = minute;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    @Override
    public String toString() {
        String scorerName = scorer.getName();
        String scorerSurname = scorer.getSurname();
        StringBuilder goal = new StringBuilder();
        goal.append(minute).append("' ");
        if (scorerName.isBlank()) goal.append(scorerSurname);
        else if (scorerSurname.isBlank()) goal.append(scorerName);
        else goal.append(scorerName.charAt(0)).append(". ").append(scorerSurname);
        if (assistent!=null) {
            goal.append(" (");
            if (assistent.getName().isBlank()) goal.append(assistent.getSurname());
            else if (assistent.getSurname().isBlank()) goal.append(assistent.getName());
            else goal.append(assistent.getName().charAt(0)).append(". ").append(assistent.getSurname());
            goal.append(")");
        }
        return goal.toString();
    }
}
