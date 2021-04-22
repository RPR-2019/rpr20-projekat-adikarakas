package ba.unsa.etf.rpr;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PreseasonController {
    public Button addClubButton, editClubButton, deleteClubButton, startButton;
    public ListView<Club> clubsLv;
    private int numberOfClubs;
    private League league;

    PreseasonController (League league, int number) {
        this.numberOfClubs=number;
        this.league=league;
    }

    @FXML
    public void initialize() {
        ObservableList<Club> list = FXCollections.observableArrayList();
        for (int i=0; i<league.getClubs().size(); i++) {
            list.add(league.getClubs().get(i));
        }
        clubsLv.setItems(list);
        clubsLv.refresh();
    }

    public void addClubPressed (ActionEvent actionEvent) throws IOException {
        clubsLv.refresh();
        if (league.getClubs().size()==this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Dostigli ste limit");
            alert.setContentText("Ne možete dodavati nove timove u ligu");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/club.fxml"));
            ClubController ctrl = new ClubController(this.league, null);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Club");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void editClubPressed (ActionEvent actionEvent) throws IOException {
        if (clubsLv.getSelectionModel().getSelectedItem()!=null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/club.fxml"));
            ClubController ctrl = new ClubController(this.league, clubsLv.getSelectionModel().getSelectedItem());
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Club");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void deleteClubPressed (ActionEvent actionEvent) {

    }

    public void startPressed (ActionEvent actionEvent) {
        if (league.getClubs().size()!=this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Liga nije popunjena");
            alert.setContentText("Ne možete pokrenuti novu sezonu dok se liga ne popuni");
        }
    }
}
