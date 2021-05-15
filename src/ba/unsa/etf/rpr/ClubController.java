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
    private LeagueDAO dao;
    public Button okButton, cancelButton;
    public TextField nameField, nicknameField, mascotField, stadiumField, managerField;
    public ColorPicker colorPicker;
    public ChoiceBox<Player> captainChoice;
    public ListView<Player> playersLv;

    ClubController(Club c) {
        this.currentClub=c;
    }

    @FXML
    public void initialize() {
        if (this.currentClub!=null) {
            if (!dao.getInstance().playersInClub(this.currentClub).isEmpty()) {
                ObservableList<Player> players = FXCollections.observableArrayList();
                for (int i = 0; i < dao.getInstance().playersInClub(this.currentClub).size(); i++) {
                    players.add(dao.getInstance().playersInClub(this.currentClub).get(i));
                }
                captainChoice.setItems(players);
                playersLv.setItems(players);
                playersLv.refresh();
            }


            nameField.setText(this.currentClub.getName());
            nicknameField.setText(this.currentClub.getNickname());
            mascotField.setText(this.currentClub.getMascot());
            stadiumField.setText(this.currentClub.getStadium());
            managerField.setText(this.currentClub.getManager());
            captainChoice.setValue(this.currentClub.getCaptain());
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
                currentClub.removePlayer(temp);
                playersLv.getItems().remove(temp);
                dao.getInstance().deletePlayer(temp);
            }
        }
    }

    public void okPressed (ActionEvent actionEvent) {
        if (nameField.getText().trim().length()==0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Morate dati ime klubu");
            alert.showAndWait();
        }
        else {
            if (this.currentClub==null) {
                for (int i=0; i<dao.getInstance().clubs().size(); i++) {
                    if (dao.getInstance().clubs().get(i).getName().equals(nameField.getText().trim())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Greška");
                        alert.setHeaderText("Neispravni podaci");
                        alert.setContentText("Klub sa ovim imenom već postoji");
                        alert.showAndWait();
                        return;
                    }
                }
                Club c = new Club(nameField.getText());
                c.setNickname(nicknameField.getText());
                c.setStadium(stadiumField.getText());
                c.setMascot(mascotField.getText());
                c.setManager(managerField.getText());
                c.setCaptain(captainChoice.getValue());
                c.setColor(colorPicker.getValue());
                dao.getInstance().addClub(c);
                FxRobot robot = new FxRobot();
                ListView clubsLv = robot.lookup("#clubsLv").queryAs(ListView.class);
                clubsLv.getItems().add(c);
            }
            else {
                dao.getInstance().deleteClub(this.currentClub.getName());
                this.currentClub.setNickname(nicknameField.getText());
                this.currentClub.setStadium(stadiumField.getText());
                this.currentClub.setMascot(mascotField.getText());
                this.currentClub.setManager(managerField.getText());
                this.currentClub.setCaptain(captainChoice.getValue());
                this.currentClub.setColor(colorPicker.getValue());
                dao.getInstance().addClub(this.currentClub);
         //       dao.getInstance().editClub(this.currentClub);
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
