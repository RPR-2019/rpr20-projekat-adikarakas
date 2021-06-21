package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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
class PreseasonControllerTest {
    LeagueDAO dao = LeagueDAO.getInstance();
    Stage theStage;
    PreseasonController ctrl;
    public Button addClubButton;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"), bundle);
        ctrl = new PreseasonController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Preseason");
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
    void testListView(FxRobot robot) throws SQLException {
        ListView<Club> listView = robot.lookup("#clubsLv").queryAs(ListView.class);
        assertEquals(4, listView.getItems().size());
        dao.resetBaseToDefault();
    }

    @Test
    void deleteClub(FxRobot robot) throws SQLException {
        robot.clickOn("Everton");
        robot.clickOn("#deleteClubButton");

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        assertEquals(3, dao.clubs().size());
        dao.resetBaseToDefault();
    }

    @Test
    void addClub(FxRobot robot) throws SQLException {
        robot.clickOn("#addClubButton");

        robot.lookup("#nameField").tryQuery().isPresent();

        robot.clickOn("#nameField");
        robot.write("Southampton");
        robot.clickOn("#okButton");

        assertEquals(5, dao.clubs().size());

        boolean find = false;
        for(Club club : dao.clubs()) {
            if (("Southampton").equals(club.getName())) {
                find = true;
                break;
            }
        }
        assertTrue(find);
        dao.resetBaseToDefault();
    }

    @Test
    void emptyClub(FxRobot robot) throws SQLException {
        robot.clickOn("#addClubButton");
        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#okButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("You must give a name to the club"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#tbExit");
        dao.resetBaseToDefault();
    }

    @Test
    void addClubOverflow(FxRobot robot) throws SQLException {
        for (int i=0; i<26; i++) {
           dao.addClub(new Club(("Southampton" + (i+1))));
        }

        robot.clickOn("#addClubButton");

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("You can't add new clubs"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        dao.resetBaseToDefault();
    }

    @Test
    void startWithOddNumberOfClubs(FxRobot robot) throws SQLException {
        robot.clickOn("Everton");
        robot.clickOn("#deleteClubButton");

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#startButton");
        DialogPane dialogPane2 = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane2.lookupAll("League must contain even number of clubs"));
        okButton = (Button) dialogPane2.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        dao.resetBaseToDefault();
    }

    @Test
    void startWithInsufficientNumberOfClubs(FxRobot robot) throws SQLException {
        robot.clickOn("Everton");
        robot.clickOn("#deleteClubButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("Chelsea");
        robot.clickOn("#deleteClubButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("Arsenal");
        robot.clickOn("#deleteClubButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("Liverpool");
        robot.clickOn("#deleteClubButton");
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#startButton");
        DialogPane dialogPane2 = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane2.lookupAll("League must contain at least 2 clubs"));

        okButton = (Button) dialogPane2.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        dao.resetBaseToDefault();
    }

    @Test
    void startWithoutFullSquads (FxRobot robot) throws SQLException {
        robot.clickOn("Everton");
        robot.clickOn("#editClubButton");

        robot.lookup("#nameField").tryQuery().isPresent();

        robot.clickOn("Allan");
        robot.clickOn("#removePlayerButton");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#okButton");

        robot.lookup("#addClubButton").tryQuery().isPresent();
        robot.clickOn("#startButton");
        DialogPane dialogPane2 = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane2.lookupAll("Club Everton have less than 15 players."));

        okButton = (Button) dialogPane2.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        dao.resetBaseToDefault();
    }

    @Test
    void startWithoutFullSquadsByPositions (FxRobot robot) throws SQLException {
        robot.clickOn("Chelsea");
        robot.clickOn("#editClubButton");

        robot.lookup("#nameField").tryQuery().isPresent();

        robot.clickOn("Edouard Mendy");
        robot.clickOn("#removePlayerButton");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("Kepa Arrizabalaga");
        robot.clickOn("#removePlayerButton");

        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#okButton");

        robot.lookup("#addClubButton").tryQuery().isPresent();
        robot.clickOn("#startButton");
        DialogPane dialogPane2 = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane2.lookupAll("Club Chelsea don't have enough players by position."));

        okButton = (Button) dialogPane2.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        dao.resetBaseToDefault();
    }

    @Test
    void startWithoutManager(FxRobot robot) throws SQLException {
        robot.clickOn("Chelsea");
        robot.clickOn("#editClubButton");
        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#managerField");
        robot.eraseText(13);
        robot.clickOn("#okButton");
        robot.lookup("#addClubButton").tryQuery().isPresent();
        robot.clickOn("#startButton");
        DialogPane dialogPane2 = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane2.lookupAll("Club Chelsea didn't hire a manager."));
        Button okButton = (Button) dialogPane2.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        dao.resetBaseToDefault();
    }

    @Test
    void clubThatExists(FxRobot robot) throws SQLException {
        robot.clickOn("#addClubButton");
        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#nameField");
        robot.write("Chelsea");
        robot.clickOn("#okButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Club with this name already exists"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        robot.clickOn("#cancelButton");
        dao.resetBaseToDefault();
    }

    @Test
    void addingPlayers(FxRobot robot) throws SQLException {
        robot.clickOn("Chelsea");
        robot.clickOn("#editClubButton");

        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#addPlayerButton");

        robot.lookup("#surnameField").tryQuery().isPresent();
        robot.clickOn("#saveButton");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("You haven't filled all fields"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#firstNameField");
        robot.write("Wilfredo");
        robot.clickOn("#surnameField");
        robot.write("Caballero");
        robot.clickOn("#choicePosition");
        robot.clickOn("Goalkeeper");
        robot.clickOn("#choiceNationality");
        robot.clickOn("Argentina");
        DatePicker dp = robot.lookup("#dateIdentity").queryAs(DatePicker.class);
        robot.moveTo(dp);
        robot.moveBy(70, 0);
        robot.press(MouseButton.PRIMARY);
        robot.release(MouseButton.PRIMARY);
        robot.press(KeyCode.UP);
        robot.release(KeyCode.UP);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#saveButton");

        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#addPlayerButton");

        robot.lookup("#surnameField").tryQuery().isPresent();
        robot.clickOn("#firstNameField");
        robot.write("Emerson");
        robot.clickOn("#surnameField");
        robot.write("Palmieri");
        robot.clickOn("#choicePosition");
        robot.clickOn("Defender");
        robot.clickOn("#choiceNationality");
        robot.clickOn("Argentina");
        dp = robot.lookup("#dateIdentity").queryAs(DatePicker.class);
        robot.moveTo(dp);
        robot.moveBy(70, 0);
        robot.press(MouseButton.PRIMARY);
        robot.release(MouseButton.PRIMARY);
        robot.press(KeyCode.UP);
        robot.release(KeyCode.UP);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#saveButton");


        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#addPlayerButton");

        robot.lookup("#surnameField").tryQuery().isPresent();
        robot.clickOn("#firstNameField");
        robot.write("Billy");
        robot.clickOn("#surnameField");
        robot.write("Gilmour");
        robot.clickOn("#choicePosition");
        robot.clickOn("Midfielder");
        robot.clickOn("#choiceNationality");
        robot.clickOn("Argentina");
        dp = robot.lookup("#dateIdentity").queryAs(DatePicker.class);
        robot.moveTo(dp);
        robot.moveBy(70, 0);
        robot.press(MouseButton.PRIMARY);
        robot.release(MouseButton.PRIMARY);
        robot.press(KeyCode.UP);
        robot.release(KeyCode.UP);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#saveButton");

        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#addPlayerButton");

        robot.lookup("#surnameField").tryQuery().isPresent();
        robot.clickOn("#cancelPlayerButton");


        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#addPlayerButton");

        robot.lookup("#surnameField").tryQuery().isPresent();
        robot.clickOn("#firstNameField");
        robot.write("Callum");
        robot.clickOn("#surnameField");
        robot.write("Odoi");
        robot.clickOn("#choicePosition");
        robot.clickOn("Attacker");
        robot.clickOn("#choiceNationality");
        robot.clickOn("Argentina");
        dp = robot.lookup("#dateIdentity").queryAs(DatePicker.class);
        robot.moveTo(dp);
        robot.moveBy(70, 0);
        robot.press(MouseButton.PRIMARY);
        robot.release(MouseButton.PRIMARY);
        robot.press(KeyCode.UP);
        robot.release(KeyCode.UP);
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#saveButton");

        robot.lookup("#nameField").tryQuery().isPresent();
        ListView<Player> lv = robot.lookup("#playersLv").queryAs(ListView.class);
        assertEquals(21, lv.getItems().size());
        robot.clickOn("#cancelButton");

        dao.resetBaseToDefault();
    }

    @Test
    void helpTest(FxRobot robot) throws SQLException {
        robot.clickOn("#addClubButton");
        robot.lookup("#nameField").tryQuery().isPresent();
        robot.clickOn("#tbHelp");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Club window helper"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#tbExit");
        dao.resetBaseToDefault();
    }
}
