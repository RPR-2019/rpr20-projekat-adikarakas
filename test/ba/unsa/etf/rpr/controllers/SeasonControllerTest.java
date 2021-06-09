package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class SeasonControllerTest {
    LeagueDAO dao = LeagueDAO.getInstance();
    Stage theStage;
    SeasonController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/season.fxml"), bundle);
        ctrl = new SeasonController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Season");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.resetBaseToDefault();
    }

    @Test
    void addGameVariations(FxRobot robot) throws SQLException {
        robot.clickOn("#addGameButton");
        robot.lookup("#homeChoice").tryQuery().isPresent();
        robot.clickOn("#homeChoice");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#awayChoice");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#confirmButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Clubs are the same or this fixture has been added before"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        robot.clickOn("#awayChoice");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#confirmButton");

        robot.lookup("#addGameButton").tryQuery().isPresent();
        robot.clickOn("#addGameButton");
        robot.lookup("#homeChoice").tryQuery().isPresent();
        robot.clickOn("#homeChoice");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#awayChoice");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#confirmButton");
        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Clubs are the same or this fixture has been added before"));
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        robot.clickOn("#homeChoice");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#awayChoice");
        robot.press(KeyCode.UP);
        robot.release(KeyCode.UP);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#confirmButton");
        assertTrue(robot.lookup("#addGameButton").tryQuery().isPresent());
        robot.clickOn("#addGameButton");
        robot.lookup("#homeChoice").tryQuery().isPresent();
        robot.clickOn("#cancelButton");
        dao.resetBaseToDefault();
    }

    @Test
    void gameAndReportTest(FxRobot robot) throws SQLException {
        dao.createStats();
        robot.clickOn("#addGameButton");
        robot.lookup("#homeChoice").tryQuery().isPresent();
        robot.clickOn("#homeChoice");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#awayChoice");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#confirmButton");

        robot.lookup("#addGameButton").tryQuery().isPresent();

        ListView lv = robot.lookup("#fixturesList").queryAs(ListView.class);
        robot.clickOn(lv.getItems().get(0).toString());
        robot.clickOn("#playGameButton");

        robot.lookup("#homePlayerChoiceBox9").tryQuery().isPresent();
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalAwayTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#openPlay");
        robot.clickOn("#okButton");

        robot.lookup("#addGoalAwayTeam").tryQuery().isPresent();
        robot.clickOn("#finishButton");

        robot.lookup("#addGameButton").tryQuery().isPresent();
        ListView lv2 = robot.lookup("#resultsList").queryAs(ListView.class);
        robot.clickOn(lv2.getItems().get(0).toString());
        robot.clickOn("#seeReportButton");

        robot.lookup("#homeTeamGoals").tryQuery().isPresent();
        TextField tf1 = robot.lookup("#homeTeamScore").queryAs(TextField.class);
        TextField tf2 = robot.lookup("#awayTeamScore").queryAs(TextField.class);
        assertEquals(0, Integer.parseInt(tf1.getText()));
        assertEquals(1, Integer.parseInt(tf2.getText()));
        robot.clickOn("#cancelButton");
        dao.resetBaseToDefault();
    }

    @Test
    void squadProblem(FxRobot robot) throws SQLException {
        robot.clickOn("#addGameButton");
        robot.lookup("#homeChoice").tryQuery().isPresent();
        robot.clickOn("#homeChoice");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#awayChoice");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#confirmButton");

        robot.lookup("#addGameButton").tryQuery().isPresent();

        ListView lv = robot.lookup("#fixturesList").queryAs(ListView.class);
        robot.clickOn(lv.getItems().get(0).toString());
        robot.clickOn("#playGameButton");

        robot.lookup("#homePlayerChoiceBox9").tryQuery().isPresent();
        robot.clickOn("#homePlayerChoiceBox9");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#okButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("There are players selected at more than one position"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        robot.clickOn("#cancelButton");
        dao.resetBaseToDefault();
    }

    @Test
    void finishBeforeFinish(FxRobot robot) {
        robot.clickOn("#finishSeasonButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("You can't finish a season before all games have been played."));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
}