package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Player;
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
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class SeasonProcessTest {
    LeagueDAO dao = LeagueDAO.getInstance();
    Stage theStage;
    PreseasonController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"), bundle);
        ctrl = new PreseasonController();
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
        dao.resetBaseToDefault();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void wholeProcess(FxRobot robot) throws SQLException {
        dao.createStats();
        robot.lookup("#startButton").tryQuery().isPresent();

        for (int i=0; i<5; i++) {
            robot.press(KeyCode.TAB);
            robot.release(KeyCode.TAB);
        }

        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        assertEquals("en", Locale.getDefault().getLanguage());

        robot.press(KeyCode.UP);
        robot.release(KeyCode.UP);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.lookup("#languageButton").tryQuery().isPresent();
        assertEquals("bs", Locale.getDefault().getLanguage());

        for (int i=0; i<4; i++) {
            robot.press(KeyCode.TAB);
            robot.release(KeyCode.TAB);
        }

        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.lookup("#addGameButton").tryQuery().isPresent();

        for (int i=0; i<8; i++) {
            robot.press(KeyCode.TAB);
            robot.release(KeyCode.TAB);
        }
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        assertEquals("bs", Locale.getDefault().getLanguage());

        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.lookup("#languageButton").tryQuery().isPresent();
        assertEquals("en", Locale.getDefault().getLanguage());

        robot.clickOn("#addGameButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("All fixtures have been added"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        ListView lv = robot.lookup("#fixturesList").queryAs(ListView.class);
        robot.clickOn(lv.getItems().get(0).toString());
        robot.clickOn("#playGameButton");

        robot.lookup("#homePlayerChoiceBox9").tryQuery().isPresent();
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalHomeTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        ChoiceBox<Player> cb1 = robot.lookup("#goalScorer").queryAs(ChoiceBox.class);
        String first = cb1.getSelectionModel().getSelectedItem().toString();
        robot.clickOn("#assistProvider");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        ChoiceBox<Player> cb2 = robot.lookup("#assistProvider").queryAs(ChoiceBox.class);
        String second = cb2.getSelectionModel().getSelectedItem().toString();
        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#openPlay");
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#finishButton");

        for (int i=0; i<11; i++) {
            robot.lookup("#addGameButton").tryQuery().isPresent();
            robot.press(KeyCode.ENTER);
            robot.release(KeyCode.ENTER);

            robot.lookup("#homePlayerChoiceBox9").tryQuery().isPresent();
            robot.press(KeyCode.ENTER);
            robot.release(KeyCode.ENTER);

            robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
            robot.press(KeyCode.ENTER);
            robot.release(KeyCode.ENTER);
        }

        robot.lookup("#playGameButton").tryQuery().isPresent();
        robot.clickOn("#finishSeasonButton");
        robot.lookup("#finishAndDeleteButton").tryQuery().isPresent();
        assertAll(
                () -> assertEquals("Liverpool", robot.lookup("#championsLabel").queryAs(Label.class).getText()),
                () -> assertEquals("Jurgen Klopp", robot.lookup("#managerLabel").queryAs(Label.class).getText()),
                () -> assertEquals("Jordan Henderson", robot.lookup("#captainLabel").queryAs(Label.class).getText()),
                () -> assertEquals(first + " (1)", robot.lookup("#scorerLabel").queryAs(Label.class).getText()),
                () -> assertEquals(second + " (1)", robot.lookup("#assistentLabel").queryAs(Label.class).getText()),
                () -> assertEquals("Bernd Leno (6)", robot.lookup("#goalkeeperLabel").queryAs(Label.class).getText())
        );
        robot.clickOn("#finishButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        robot.press(KeyCode.RIGHT);
        robot.release(KeyCode.RIGHT);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        dao.resetBaseToDefault();
    }

    @Test
    void wholeProcessAndDelete(FxRobot robot) throws SQLException {
        dao.createStats();
        robot.clickOn("#startButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.lookup("#addGameButton").tryQuery().isPresent();
        ListView lv = robot.lookup("#fixturesList").queryAs(ListView.class);
        robot.clickOn(lv.getItems().get(0).toString());
        robot.clickOn("#playGameButton");

        robot.lookup("#homePlayerChoiceBox9").tryQuery().isPresent();
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalHomeTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        ChoiceBox<Player> cb1 = robot.lookup("#goalScorer").queryAs(ChoiceBox.class);
        String first = cb1.getSelectionModel().getSelectedItem().toString();
        robot.clickOn("#assistProvider");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        ChoiceBox<Player> cb2 = robot.lookup("#assistProvider").queryAs(ChoiceBox.class);
        String second = cb2.getSelectionModel().getSelectedItem().toString();
        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#openPlay");
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#finishButton");

        for (int i=0; i<11; i++) {
            robot.lookup("#addGameButton").tryQuery().isPresent();
            robot.press(KeyCode.ENTER);
            robot.release(KeyCode.ENTER);

            robot.lookup("#homePlayerChoiceBox9").tryQuery().isPresent();
            robot.press(KeyCode.ENTER);
            robot.release(KeyCode.ENTER);

            robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
            robot.press(KeyCode.ENTER);
            robot.release(KeyCode.ENTER);
        }

        robot.lookup("#playGameButton").tryQuery().isPresent();
        robot.clickOn("#finishSeasonButton");
        robot.lookup("#finishAndDeleteButton").tryQuery().isPresent();
        assertAll(
                () -> assertEquals("Liverpool", robot.lookup("#championsLabel").queryAs(Label.class).getText()),
                () -> assertEquals("Jurgen Klopp", robot.lookup("#managerLabel").queryAs(Label.class).getText()),
                () -> assertEquals("Jordan Henderson", robot.lookup("#captainLabel").queryAs(Label.class).getText()),
                () -> assertEquals(first + " (1)", robot.lookup("#scorerLabel").queryAs(Label.class).getText()),
                () -> assertEquals(second + " (1)", robot.lookup("#assistentLabel").queryAs(Label.class).getText()),
                () -> assertEquals("Bernd Leno (6)", robot.lookup("#goalkeeperLabel").queryAs(Label.class).getText())
        );
        robot.clickOn("#finishButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        dao.resetBaseToDefault();
    }
}