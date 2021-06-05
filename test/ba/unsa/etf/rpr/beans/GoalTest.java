package ba.unsa.etf.rpr.beans;

import ba.unsa.etf.rpr.other.GoalDistance;
import ba.unsa.etf.rpr.other.GoalSituation;
import ba.unsa.etf.rpr.other.GoalType;
import com.mysql.cj.exceptions.WrongArgumentException;
import org.assertj.core.internal.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GoalTest {

    @Test
    public void constructor() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-07-03"), "Bosna i Hercegovina");
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Goal goal1 = new Goal(def, gk, 54, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.INSIDEBOX);
        Goal goal2 = new Goal(def, 59, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.OUTSIDEBOX);
        assertAll(
                () -> assertEquals(def, goal1.getScorer()),
                () -> assertEquals(gk, goal1.getAssistent()),
                () -> assertEquals(54, goal1.getMinute()),
                () -> assertEquals(GoalType.LEFTFOOT, goal1.getGoalType()),
                () -> assertEquals(GoalSituation.OPENPLAY, goal1.getGoalSituation()),
                () -> assertEquals(GoalDistance.INSIDEBOX, goal1.getGoalDistance()),
                () -> assertNull(goal2.getAssistent())
        );
    }

    @Test
    public void stringTest() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-07-03"), "Bosna i Hercegovina");
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Attacker att = new Attacker("Richarlison", "", LocalDate.parse("1997-07-07"), "Brazil");
        Midfielder mid = new Midfielder("", "Willian", LocalDate.parse("1989-05-02"), "Brazil");
        Goal goal1 = new Goal(def, gk, 54, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.INSIDEBOX);
        Goal goal2 = new Goal(def, 59, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.OUTSIDEBOX);
        Goal goal3 = new Goal(att, mid, 1, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.INSIDEBOX);
        assertAll(
                () -> assertEquals("54' S. Kolašinac (A. Begović)", goal1.toString()),
                () -> assertEquals("59' S. Kolašinac", goal2.toString()),
                () -> assertEquals("1' Richarlison (Willian)", goal3.toString()));
    }

    @Test
    public void getterAndSetter() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-07-03"), "Bosna i Hercegovina");
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Midfielder mid = new Midfielder("", "Willian", LocalDate.parse("1989-05-02"), "Brazil");
        Goal goal1 = new Goal(def, gk, 54, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.INSIDEBOX);
        goal1.setScorer(mid);
        goal1.setAssistent(null);
        goal1.setMinute(85);
        goal1.setGoalSituation(GoalSituation.PENALTY);
        goal1.setGoalDistance(GoalDistance.INSIDEBOX);
        goal1.setGoalType(GoalType.HEADER);
    }

    @Test
    public void exceptions() {
        Goalkeeper gk = new Goalkeeper("Asmir", "Begović", LocalDate.parse("1987-07-03"), "Bosna i Hercegovina");
        Defender def = new Defender("Sead", "Kolašinac", LocalDate.parse("1995-03-11"), "Bosna i Hercegovina");
        Midfielder mid = new Midfielder("", "Willian", LocalDate.parse("1989-05-02"), "Brazil");
        Goal goal1 = new Goal(def, gk, 54, GoalType.LEFTFOOT, GoalSituation.OPENPLAY, GoalDistance.INSIDEBOX);
        assertThrows(IllegalArgumentException.class, () -> goal1.setMinute(123), "Minute should be between 0 and 90");
        assertThrows(IllegalArgumentException.class, () -> goal1.setMinute(-3), "Minute should be between 0 and 90");
        assertThrows(WrongArgumentException.class, () -> goal1.setGoalSituation(GoalSituation.FREEKICK), "You can get assist only for open play goals");
        assertThrows(WrongArgumentException.class, () -> goal1.setGoalSituation(GoalSituation.PENALTY), "You can get assist only for open play goals");
        goal1.setAssistent(null);
        goal1.setGoalSituation(GoalSituation.PENALTY);
        assertThrows(WrongArgumentException.class, () -> goal1.setGoalDistance(GoalDistance.OUTSIDEBOX), "You can't score penalty from outside box");
        Goal goal2 = new Goal(mid, 22, GoalType.LEFTFOOT, GoalSituation.FREEKICK, GoalDistance.OUTSIDEBOX);
        assertThrows(WrongArgumentException.class, () -> goal2.setGoalDistance(GoalDistance.INSIDEBOX), "You can't score free kick from inside box");
    }

}