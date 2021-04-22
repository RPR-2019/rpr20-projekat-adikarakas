package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

public class ClubController {
    private Club currentClub;
    private League league;
    public Button okButton, cancelButton;
    public TextField nameField, nicknameField, mascotField, stadiumField;
    public ColorPicker colorPicker;
    public ChoiceBox<String> managerChoice;

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
            nameField.setText(this.currentClub.getName());
            nicknameField.setText(this.currentClub.getNickname());
            mascotField.setText(this.currentClub.getMascot());
            stadiumField.setText(this.currentClub.getStadium());
            managerChoice.setValue(this.currentClub.getManager());
            colorPicker.setValue(this.currentClub.getColor());
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
