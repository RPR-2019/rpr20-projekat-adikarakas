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
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.testfx.api.FxRobot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ClubController {
    private final Club currentClub;
    private LeagueDAO dao;
    public Button addPlayerButton;
    public Button removePlayerButton;
    public Button okButton;
    public Button cancelButton;
    public Button tbExit;
    public Label playersLabel;
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
    }

    public void addPlayer () throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"), bundle);
        PlayerController ctrl = new PlayerController(this.currentClub, null);
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
            alert.setTitle("Potvrda");
            alert.setContentText("Jeste li sigurni da želite izbrisati igrača " + temp.getName() + " " + temp.getSurname() + "?");
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
            e1.printStackTrace();
        }
    }

    public void help() {

    }

    public void exit() {
        Stage stage = (Stage) tbExit.getScene().getWindow();
        stage.close();
    }

    public void okPressed () {
        if (nameField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Morate dati ime klubu");
            alert.showAndWait();
            nameField.requestFocus();
            nameField.getStyleClass().add("popunjenoKakoNeTreba");
        }
        else {
            if (this.currentClub==null) {
                boolean mistake=false;
                ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
                for (int i=0; i<clubs.size(); i++) {
                    if (clubs.get(i).getName().equals(nameField.getText().trim())) mistake=true;
                }
                if (mistake) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Neispravni podaci");
                    alert.setContentText("Klub sa ovim imenom već postoji");
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
