package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
            alert.showAndWait();
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
        Club temp = clubsLv.getSelectionModel().getSelectedItem();
        if (temp!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda");
            alert.setContentText("Jeste li sigurni da želite izbrisati klub " + temp.getName() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                league.removeClub(temp);
                clubsLv.getItems().remove(temp);
            }
        }
    }

    public void addPlayerPressed (ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"));
        PlayerController ctrl = new PlayerController(clubsLv.getSelectionModel().getSelectedItem(), null);
        fxmlLoader.setController(ctrl);
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setMinHeight(150);
        stage.setMinWidth(400);
        stage.setTitle("Player");
        stage.setScene(scene);
        stage.show();
    }

    public void startPressed (ActionEvent actionEvent) {
        if (league.getClubs().size()!=this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Liga nije popunjena");
            alert.setContentText("Ne možete pokrenuti novu sezonu dok se liga ne popuni");
            alert.showAndWait();
        }
        else {
            for (int i = 0; i < league.getNumberOfClubs(); i++) {
                Club temp = league.getClubs().get(i);
                if (temp.getPlayers().size() < 15) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Nedovoljno igrača");
                    alert.setContentText("Klub " + league.getClubs().get(i) + " ima manje od 15 igrača.");
                    alert.showAndWait();
                    break;
                }
                ArrayList<Player> goalkeepers = new ArrayList<>();
                ArrayList<Player> defenders = new ArrayList<>();
                ArrayList<Player> midfielders = new ArrayList<>();
                ArrayList<Player> attackers = new ArrayList<>();
                for (int j = 0; j < temp.getPlayers().size(); j++) {
                    if (temp.getPlayers().get(j) instanceof Goalkeeper) goalkeepers.add(temp.getPlayers().get(j));
                    else if (temp.getPlayers().get(j) instanceof Defender) defenders.add(temp.getPlayers().get(j));
                    else if (temp.getPlayers().get(j) instanceof Midfielder) midfielders.add(temp.getPlayers().get(j));
                    else attackers.add(temp.getPlayers().get(j));
                }
                if (goalkeepers.size() < 1 || defenders.size() < 4 || midfielders.size() < 3 || attackers.size() < 3) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Nedovoljno igrača");
                    alert.setContentText("Klub " + league.getClubs().get(i) + " nema dovoljno igrača po pozicijama.");
                    alert.showAndWait();
                    break;
                }
            }
        }
    }
}
