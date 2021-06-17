package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.beans.PlayerStats;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
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
import static org.junit.jupiter.api.Assertions.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

@ExtendWith(ApplicationExtension.class)
class StatTest {
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
    }

    @Test
    void statTest(FxRobot robot) throws SQLException {
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
        robot.clickOn("#head");
        robot.clickOn("#insideBox");
        robot.clickOn("#openPlay");
        robot.clickOn("#okButton");


        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalHomeTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#leftFoot");
        robot.clickOn("#outsideBox");
        robot.clickOn("#freeKick");
        robot.clickOn("#okButton");


        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalHomeTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.DOWN);
        robot.release(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        cb1 = robot.lookup("#goalScorer").queryAs(ChoiceBox.class);
        String third = cb1.getSelectionModel().getSelectedItem().toString();
        robot.clickOn("#rightFoot");
        robot.clickOn("#insideBox");
        robot.clickOn("#penalty");
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalHomeTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#goalScorer");
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#head");
        robot.clickOn("#insideBox");
        robot.clickOn("#openPlay");
        robot.clickOn("#okButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#addGoalHomeTeam");

        robot.lookup("#goalScorer").tryQuery().isPresent();
        robot.clickOn("#cancelGoalButton");

        robot.lookup("#addGoalHomeTeam").tryQuery().isPresent();
        robot.clickOn("#finishButton");

        robot.lookup("#addGameButton").tryQuery().isPresent();
        robot.clickOn("#statsButton");

        TableView<PlayerStats> goals = robot.lookup("#tableViewGoals").queryAs(TableView.class);
        TableView<PlayerStats> assists = robot.lookup("#tableViewAssists").queryAs(TableView.class);
        TableView<PlayerStats> cleanSheets = robot.lookup("#tableViewCleanSheets").queryAs(TableView.class);
        TableView<PlayerStats> inside = robot.lookup("#tableViewInside").queryAs(TableView.class);
        TableView<PlayerStats> outside = robot.lookup("#tableViewOutside").queryAs(TableView.class);
        TableView<PlayerStats> right = robot.lookup("#tableViewRight").queryAs(TableView.class);
        TableView<PlayerStats> left = robot.lookup("#tableViewLeft").queryAs(TableView.class);
        TableView<PlayerStats> head = robot.lookup("#tableViewHead").queryAs(TableView.class);
        TableView<PlayerStats> open = robot.lookup("#tableViewOpen").queryAs(TableView.class);
        TableView<PlayerStats> penalty = robot.lookup("#tableViewPenalties").queryAs(TableView.class);
        TableView<PlayerStats> freeKick = robot.lookup("#tableViewFreeKicks").queryAs(TableView.class);


        assertAll(
                () -> assertEquals(first, goals.getItems().get(0).getName().toString()),
                () -> assertEquals(second, assists.getItems().get(0).getName().toString()),
                () -> assertEquals("Alisson Becker", cleanSheets.getItems().get(0).getName().toString()),
                () -> assertEquals(first, inside.getItems().get(0).getName().toString()),
                () -> assertEquals(second, outside.getItems().get(0).getName().toString()),
                () -> assertEquals(third, right.getItems().get(0).getName().toString()),
                () -> assertEquals(second, left.getItems().get(0).getName().toString()),
                () -> assertEquals(first, head.getItems().get(0).getName().toString()),
                () -> assertEquals(first, open.getItems().get(0).getName().toString()),
                () -> assertEquals(third, penalty.getItems().get(0).getName().toString()),
                () -> assertEquals(second, freeKick.getItems().get(0).getName().toString())
        );
        dao.resetBaseToDefault();
    }
}
