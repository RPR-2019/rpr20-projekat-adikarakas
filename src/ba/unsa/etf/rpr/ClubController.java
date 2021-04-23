package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ClubController {
    private Club currentClub;
    private League league;
    public Button okButton, cancelButton;
    public TextField nameField, nicknameField, mascotField, stadiumField;
    public ColorPicker colorPicker;
    public ChoiceBox<String> managerChoice;
    public ListView<Player> playersLv;

    ClubController(League l, Club c) {
        this.currentClub=c;
        this.league=l;
    }

    @FXML
    public void initialize() {
        ObservableList<String> managers = FXCollections.observableArrayList();
        for (int i=0; i<this.league.getManagers().size(); i++) {
            managers.add(this.league.getManagers().get(i));
        }
        managerChoice.setItems(managers);

        if (this.currentClub!=null) {
            if (!this.currentClub.getPlayers().isEmpty()) {
                ObservableList<Player> players = FXCollections.observableArrayList();
                for (int i = 0; i < this.currentClub.getPlayers().size(); i++) {
                    players.add(this.currentClub.getPlayers().get(i));
                }
                playersLv.setItems(players);
            }

            nameField.setText(this.currentClub.getName());
            nicknameField.setText(this.currentClub.getNickname());
            mascotField.setText(this.currentClub.getMascot());
            stadiumField.setText(this.currentClub.getStadium());
            managerChoice.setValue(this.currentClub.getManager());
            colorPicker.setValue(this.currentClub.getColor());
        }
    }

    public void addPlayer (ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"));
        PlayerController ctrl = new PlayerController(this.currentClub, null);
        fxmlLoader.setController(ctrl);
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setMinHeight(150);
        stage.setMinWidth(400);
        stage.setTitle("Player");
        stage.setScene(scene);
        stage.show();
    }

    public void removePlayer (ActionEvent actionEvent) {
        Player temp = playersLv.getSelectionModel().getSelectedItem();
        if (temp!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda");
            alert.setContentText("Jeste li sigurni da želite izbrisati igrača " + temp.getName() + " " + temp.getSurname() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                currentClub.addPlayer(temp);
                playersLv.getItems().remove(temp);
            }
        }
    }

    public void okPressed (ActionEvent actionEvent) {
        if (nameField.getText().trim().length()==0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Morate dati ime klubu");
        }
        else {
            if (this.currentClub==null) {
                Club c = new Club(nameField.getText());
                c.setNickname(nicknameField.getText());
                c.setStadium(stadiumField.getText());
                c.setMascot(mascotField.getText());
                c.setManager(managerChoice.getValue());
                c.setColor(colorPicker.getValue());
                this.league.addClub(c);
                FxRobot robot = new FxRobot();
                ListView clubsLv = robot.lookup("#clubsLv").queryAs(ListView.class);
                clubsLv.getItems().add(c);
            }
            else {
                this.currentClub.setNickname(nicknameField.getText());
                this.currentClub.setStadium(stadiumField.getText());
                this.currentClub.setMascot(mascotField.getText());
                this.currentClub.setManager(managerChoice.getValue());
                this.currentClub.setColor(colorPicker.getValue());
            }


            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelPressed (ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
