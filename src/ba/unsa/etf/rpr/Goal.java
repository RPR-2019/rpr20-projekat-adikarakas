package ba.unsa.etf.rpr;

public class Goal {
    public Club scoringTeam;
    public Player scorer;
    public Player assistent;
    public GoalType goalType;
    public GoalSituation goalSituation;
    public GoalDistance goalDistance;

    public Goal(Player scorer, Player assistent, GoalType goalType, GoalSituation goalSituation, GoalDistance goalDistance) {
        //this.scoringTeam = scoringTeam;
        this.scorer = scorer;
        this.assistent = assistent;
        this.goalType = goalType;
        this.goalSituation = goalSituation;
        this.goalDistance = goalDistance;
    }

    public Goal(Player scorer, GoalType goalType, GoalSituation goalSituation, GoalDistance goalDistance) {
        //this.scoringTeam = scoringTeam;
        this.scorer = scorer;
        this.assistent = null;
        this.goalType = goalType;
        this.goalSituation = goalSituation;
        this.goalDistance = goalDistance;
    }

    public Club getScoringTeam() {
        return scoringTeam;
    }

    public void setScoringTeam(Club scoringTeam) {
        this.scoringTeam = scoringTeam;
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
}
