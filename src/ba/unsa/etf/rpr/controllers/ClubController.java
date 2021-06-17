package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.other.LeagueDAO;
import ba.unsa.etf.rpr.other.PrintReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.testfx.api.FxRobot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ClubController {
    private final Club currentClub;
    private final LeagueDAO dao;
    public Button addPlayerButton;
    public Button removePlayerButton;
    public Button okButton;
    public Button cancelButton;
    public Button tbExit;
    public Button tbPrint;
    public Button tbHelp;
    public TextField nameField;
    public TextField nicknameField;
    public TextField mascotField;
    public TextField stadiumField;
    public TextField managerField;
    public ColorPicker colorPicker;
    public ChoiceBox<Player> captainChoice;
    public ListView<Player> playersLv;

    ClubController(Club c) {
        dao=LeagueDAO.getInstance();
        this.currentClub=c;
    }

    @FXML
    public void initialize() {
        if (this.currentClub!=null) {
            if (!dao.playersInClub(this.currentClub).isEmpty()) {
                ObservableList<Player> captains = FXCollections.observableArrayList(dao.playersInClub(this.currentClub));
                ObservableList<Player> players = FXCollections.observableArrayList();
                players.addAll(dao.playersInClub(this.currentClub));
                playersLv.setItems(players);
                playersLv.refresh();
                captainChoice.setItems(captains);
                captainChoice.getItems().add(null);
            }
            if (Locale.getDefault().equals(new Locale("en", "EN"))) {
                addPlayerButton.setTooltip(new Tooltip("Add new player"));
                removePlayerButton.setTooltip(new Tooltip("Remove selected player"));
                playersLv.setTooltip(new Tooltip("Clubs must have at least 15 players"));
            }
            else if (Locale.getDefault().equals(new Locale("bs", "BA"))) {
                addPlayerButton.setTooltip(new Tooltip("Dodaj novog igrača"));
                removePlayerButton.setTooltip(new Tooltip("Izbaci odabranog igrača"));
                playersLv.setTooltip(new Tooltip("Klubovi moraju imati najmanje 15 igrača"));
            }
            nameField.setText(this.currentClub.getName());
            nicknameField.setText(this.currentClub.getNickname());
            mascotField.setText(this.currentClub.getMascot());
            stadiumField.setText(this.currentClub.getStadium());
            managerField.setText(this.currentClub.getManager());
            captainChoice.setValue(this.currentClub.getCaptain());
            colorPicker.setValue(this.currentClub.getColor());

            nameField.setEditable(false);
        }
        else {
            removePlayerButton.setVisible(false);
            addPlayerButton.setVisible(false);
        }
        if (Locale.getDefault().equals(new Locale("en", "EN"))) {
            tbExit.setTooltip(new Tooltip("Exit"));
            tbPrint.setTooltip(new Tooltip("Print squad list"));
            tbHelp.setTooltip(new Tooltip("Open helper"));
            okButton.setTooltip(new Tooltip("Save changes"));
        }
        else if (Locale.getDefault().equals(new Locale("bs", "BA"))) {
            tbExit.setTooltip(new Tooltip("Izlaz"));
            tbPrint.setTooltip(new Tooltip("Ispiši listu igrača"));
            tbHelp.setTooltip(new Tooltip("Otvori pomoć"));
            okButton.setTooltip(new Tooltip("Spasi promjene"));
        }
    }

    public void addPlayer () throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"), bundle);
        PlayerController ctrl = new PlayerController(this.currentClub);
        fxmlLoader.setController(ctrl);
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setMinHeight(150);
        stage.setMinWidth(400);
        stage.setTitle("Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void removePlayer () {
        Player temp = playersLv.getSelectionModel().getSelectedItem();
        if (temp!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete " + temp.getName() + " " + temp.getSurname() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                currentClub.getPlayers().remove(temp);
                playersLv.getItems().remove(temp);
                dao.deletePlayer(temp);
            }
        }
    }

    public void print() {
        try {
            PrintReport pr = new PrintReport(this.currentClub);
            pr.showReport(dao.getConn());
        } catch (JRException e1) {
            e1.getMessage();
        }
    }

    public void help() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Help");
        alert.setHeaderText("Club window helper");
        StringBuilder message = new StringBuilder();

        if (Locale.getDefault().equals(new Locale("bs", "BA"))) {
            message.append("Prilikom kreiranja novog kluba dovoljno je samo unijeti ime. ");
            message.append("Ime kluba ne smije biti prazno i mora biti jedinstveno u ligi.\n\n");

            message.append("Sezona ne može početi dok ne odredite menadžera i kapitena ekipe.\n\n");
            message.append("Klub mora imati minimalno 15 igrača i to:\n\n");
            message.append("- minimalno jednog golmana,\n");
            message.append("- minimalno 4 odbrambena igrača,\n");
            message.append("- minimalno 3 igrača sredine terena,\n");
            message.append("- minimalno 3 napadača.\n\n");

            message.append("Igrače ne možete dodavati odmah prilikom kreiranja kluba. ");
            message.append("Morate sačuvati promjene, vratiti se na početnu stranicu i odabrati opciju za uređenje. ");
            message.append("Listu igrača sa njihovim podacima možete dobiti pritiskom na ikonu za ispis. ");
            message.append("Podatke o igračima ne možete mijenjati, možete ih samo obrisati i ponovo kreirati.");
        }

        else if (Locale.getDefault().equals(new Locale("en", "EN"))) {
            message.append("To create a new club it's just enough to fill only a name field. ");
            message.append("Name shouldn't be blank and must be unique.\n\n");

            message.append("Season couldn't start before you choose captains and managers for every club.\n\n");
            message.append("Clubs must have at least 15 players and:\n\n");
            message.append("- minimum of 1 goalkeeper,\n");
            message.append("- minimum of 4 defenders,\n");
            message.append("- minimum of 3 midfielders,\n");
            message.append("- minimum of 3 attackers.\n\n");

            message.append("Players couldn't be added while creating a club. ");
            message.append("You should save changes first, go back to the homepage and select an editing option. ");
            message.append("Squad list could be printed by clicking at the print icon. ");
            message.append("You can't edit players, you can only delete them and create again.");
        }

        alert.setContentText(message.toString());

        alert.showAndWait();
    }

    public void exit() {
        Stage stage = (Stage) tbExit.getScene().getWindow();
        stage.close();
    }

    public void okPressed () {
        if (nameField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect data");
            alert.setContentText("You must give a name to the club");
            alert.showAndWait();
            nameField.requestFocus();
            nameField.getStyleClass().add("popunjenoKakoNeTreba");
        }
        else {
            if (this.currentClub==null) {
                boolean mistake=false;
                ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
                for (Club club : clubs) {
                    if (club.getName().equals(nameField.getText().trim())) mistake = true;
                }
                if (mistake) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Incorrect data");
                    alert.setContentText("Club with this name already exists");
                    alert.showAndWait();
                    nameField.requestFocus();
                    nameField.getStyleClass().add("popunjenoKakoNeTreba");
                    return;
                }
                Club c = new Club(nameField.getText());
                c.setNickname(nicknameField.getText());
                c.setStadium(stadiumField.getText());
                c.setMascot(mascotField.getText());
                c.setManager(managerField.getText());
                c.setCaptain(captainChoice.getValue());
                c.setColor(colorPicker.getValue());
                dao.addClub(c);
                FxRobot robot = new FxRobot();
                ListView<Club> clubsLv = robot.lookup("#clubsLv").queryAs(ListView.class);
                clubsLv.getItems().add(c);
            }
            else {
                dao.deleteClub(this.currentClub.getName());
                this.currentClub.setNickname(nicknameField.getText());
                this.currentClub.setStadium(stadiumField.getText());
                this.currentClub.setMascot(mascotField.getText());
                this.currentClub.setManager(managerField.getText());
                this.currentClub.setCaptain(captainChoice.getValue());
                this.currentClub.setColor(colorPicker.getValue());
                dao.addClub(this.currentClub);
            }
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelPressed () {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
