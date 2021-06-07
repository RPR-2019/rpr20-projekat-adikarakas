package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Goal;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class GoalControllerTest {
    LeagueDAO dao = LeagueDAO.getInstance();
    Stage theStage;
    GoalController ctrl;
    public Button addClubButton;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/goal.fxml"), bundle);
        ArrayList<Player> players = new ArrayList<>();
        players.add(dao.playersInClub(dao.clubs().get(0)).get(0));
        players.add(dao.playersInClub(dao.clubs().get(0)).get(1));
        players.add(dao.playersInClub(dao.clubs().get(0)).get(2));
        ObservableList<Goal> goals = FXCollections.observableArrayList();
        ctrl = new GoalController(players, goals, true);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Goal");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @Test
    void emptyGoal(FxRobot robot) {
        robot.clickOn("#okButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Morate odrediti strijelca"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void noGoalParameters(FxRobot robot) {
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#assistProvider");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#okButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Niste dobro unijeli podatke"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void samePerson(FxRobot robot) {
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#assistProvider");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#openPlay");
        robot.clickOn("#okButton");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Asistent i strijelac ne mogu biti ista osoba"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void assistProblems (FxRobot robot) {
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#assistProvider");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#penalty");
        robot.clickOn("#okButton");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Kod penala i slobodnih udaraca se ne dodjeljuje asistencija"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#freeKick");
        robot.clickOn("#okButton");

        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Kod penala i slobodnih udaraca se ne dodjeljuje asistencija"));
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void penaltyOutsideBox (FxRobot robot) {
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#rightFoot");
        robot.clickOn("#outsideBox");
        robot.clickOn("#penalty");
        robot.clickOn("#okButton");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Penal se izvodi sa 11 metara"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void freeKickInsideBox (FxRobot robot) {
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#freeKick");
        robot.clickOn("#okButton");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Slobodan udarac se izvodi izvan šesnaesterca"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

}