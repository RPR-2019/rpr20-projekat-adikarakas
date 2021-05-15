package ba.unsa.etf.rpr;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class SquadController {
    public Button okButton, cancelButton;
    public ChoiceBox<Player> homePlayerChoiceBox1, homePlayerChoiceBox2, homePlayerChoiceBox3, homePlayerChoiceBox4, homePlayerChoiceBox5, homePlayerChoiceBox6, homePlayerChoiceBox7, homePlayerChoiceBox8, homePlayerChoiceBox9, homePlayerChoiceBox10, homePlayerChoiceBox11;
    public ChoiceBox<Player> awayPlayerChoiceBox1, awayPlayerChoiceBox2, awayPlayerChoiceBox3, awayPlayerChoiceBox4, awayPlayerChoiceBox5, awayPlayerChoiceBox6, awayPlayerChoiceBox7, awayPlayerChoiceBox8, awayPlayerChoiceBox9, awayPlayerChoiceBox10, awayPlayerChoiceBox11;
    private ObservableList<Player> homeGoalkeepers, homeDefenders, homeMidfielders, homeAttackers;
    private ObservableList<Player> awayGoalkeepers, awayDefenders, awayMidfielders, awayAttackers;
    private Club homeClub, awayClub;
    private LeagueDAO dao;
    private ObservableList<ClubOnTable> clubsOnTable;

    SquadController (Club home, Club away, ObservableList<ClubOnTable> clubsOnTable) {
        this.clubsOnTable=clubsOnTable;
        this.homeClub=home;
        this.awayClub=away;
        this.homeGoalkeepers = FXCollections.observableArrayList();
        this.homeDefenders = FXCollections.observableArrayList();
        this.homeMidfielders = FXCollections.observableArrayList();
        this.homeAttackers = FXCollections.observableArrayList();
        for (int i=0; i<dao.getInstance().playersInClub(home).size(); i++) {
            if (dao.getInstance().playersInClub(home).get(i) instanceof Goalkeeper) this.homeGoalkeepers.add(dao.getInstance().playersInClub(home).get(i));
            else if (dao.getInstance().playersInClub(home).get(i) instanceof Defender) this.homeDefenders.add(dao.getInstance().playersInClub(home).get(i));
            else if (dao.getInstance().playersInClub(home).get(i) instanceof Midfielder) this.homeMidfielders.add(dao.getInstance().playersInClub(home).get(i));
            else this.homeAttackers.add(dao.getInstance().playersInClub(home).get(i));
        }

        this.awayGoalkeepers = FXCollections.observableArrayList();
        this.awayDefenders = FXCollections.observableArrayList();
        this.awayMidfielders = FXCollections.observableArrayList();
        this.awayAttackers = FXCollections.observableArrayList();
        for (int i=0; i<dao.getInstance().playersInClub(away).size(); i++) {
            if (dao.getInstance().playersInClub(away).get(i) instanceof Goalkeeper) this.awayGoalkeepers.add(dao.getInstance().playersInClub(away).get(i));
            else if (dao.getInstance().playersInClub(away).get(i) instanceof Defender) this.awayDefenders.add(dao.getInstance().playersInClub(away).get(i));
            else if (dao.getInstance().playersInClub(away).get(i) instanceof Midfielder) this.awayMidfielders.add(dao.getInstance().playersInClub(away).get(i));
            else this.awayAttackers.add(dao.getInstance().playersInClub(away).get(i));
        }
    }

    public void initialize() {

        homePlayerChoiceBox1.setItems(this.homeGoalkeepers);
        homePlayerChoiceBox2.setItems(this.homeDefenders);
        homePlayerChoiceBox3.setItems(this.homeDefenders);
        homePlayerChoiceBox4.setItems(this.homeDefenders);
        homePlayerChoiceBox5.setItems(this.homeDefenders);
        homePlayerChoiceBox6.setItems(this.homeMidfielders);
        homePlayerChoiceBox7.setItems(this.homeMidfielders);
        homePlayerChoiceBox8.setItems(this.homeMidfielders);
        homePlayerChoiceBox9.setItems(this.homeAttackers);
        homePlayerChoiceBox10.setItems(this.homeAttackers);
        homePlayerChoiceBox11.setItems(this.homeAttackers);

        // defaultna postava
        homePlayerChoiceBox1.setValue(this.homeGoalkeepers.get(0));
        homePlayerChoiceBox2.setValue(this.homeDefenders.get(0));
        homePlayerChoiceBox3.setValue(this.homeDefenders.get(1));
        homePlayerChoiceBox4.setValue(this.homeDefenders.get(2));
        homePlayerChoiceBox5.setValue(this.homeDefenders.get(3));
        homePlayerChoiceBox6.setValue(this.homeMidfielders.get(0));
        homePlayerChoiceBox7.setValue(this.homeMidfielders.get(1));
        homePlayerChoiceBox8.setValue(this.homeMidfielders.get(2));
        homePlayerChoiceBox9.setValue(this.homeAttackers.get(0));
        homePlayerChoiceBox10.setValue(this.homeAttackers.get(1));
        homePlayerChoiceBox11.setValue(this.homeAttackers.get(2));

        awayPlayerChoiceBox1.setItems(this.awayGoalkeepers);
        awayPlayerChoiceBox2.setItems(this.awayDefenders);
        awayPlayerChoiceBox3.setItems(this.awayDefenders);
        awayPlayerChoiceBox4.setItems(this.awayDefenders);
        awayPlayerChoiceBox5.setItems(this.awayDefenders);
        awayPlayerChoiceBox6.setItems(this.awayMidfielders);
        awayPlayerChoiceBox7.setItems(this.awayMidfielders);
        awayPlayerChoiceBox8.setItems(this.awayMidfielders);
        awayPlayerChoiceBox9.setItems(this.awayAttackers);
        awayPlayerChoiceBox10.setItems(this.awayAttackers);
        awayPlayerChoiceBox11.setItems(this.awayAttackers);

        // defaultna postava
        awayPlayerChoiceBox1.setValue(this.awayGoalkeepers.get(0));
        awayPlayerChoiceBox2.setValue(this.awayDefenders.get(0));
        awayPlayerChoiceBox3.setValue(this.awayDefenders.get(1));
        awayPlayerChoiceBox4.setValue(this.awayDefenders.get(2));
        awayPlayerChoiceBox5.setValue(this.awayDefenders.get(3));
        awayPlayerChoiceBox6.setValue(this.awayMidfielders.get(0));
        awayPlayerChoiceBox7.setValue(this.awayMidfielders.get(1));
        awayPlayerChoiceBox8.setValue(this.awayMidfielders.get(2));
        awayPlayerChoiceBox9.setValue(this.awayAttackers.get(0));
        awayPlayerChoiceBox10.setValue(this.awayAttackers.get(1));
        awayPlayerChoiceBox11.setValue(this.awayAttackers.get(2));
    }

    public void okPressed(ActionEvent actionEvent) throws IOException {
        Set<Player> homeSet = new HashSet<Player>();
        homeSet.add(homePlayerChoiceBox1.getValue());
        homeSet.add(homePlayerChoiceBox2.getValue());
        homeSet.add(homePlayerChoiceBox3.getValue());
        homeSet.add(homePlayerChoiceBox4.getValue());
        homeSet.add(homePlayerChoiceBox5.getValue());
        homeSet.add(homePlayerChoiceBox6.getValue());
        homeSet.add(homePlayerChoiceBox7.getValue());
        homeSet.add(homePlayerChoiceBox8.getValue());
        homeSet.add(homePlayerChoiceBox9.getValue());
        homeSet.add(homePlayerChoiceBox10.getValue());
        homeSet.add(homePlayerChoiceBox11.getValue());

        Set<Player> awaySet = new HashSet<Player>();
        awaySet.add(awayPlayerChoiceBox1.getValue());
        awaySet.add(awayPlayerChoiceBox2.getValue());
        awaySet.add(awayPlayerChoiceBox3.getValue());
        awaySet.add(awayPlayerChoiceBox4.getValue());
        awaySet.add(awayPlayerChoiceBox5.getValue());
        awaySet.add(awayPlayerChoiceBox6.getValue());
        awaySet.add(awayPlayerChoiceBox7.getValue());
        awaySet.add(awayPlayerChoiceBox8.getValue());
        awaySet.add(awayPlayerChoiceBox9.getValue());
        awaySet.add(awayPlayerChoiceBox10.getValue());
        awaySet.add(awayPlayerChoiceBox11.getValue());

        if (homeSet.size()!=11 || awaySet.size()!=11) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Nepravilan odabir igrača");
            alert.setContentText("Neki igrači se ponavljaju");
            alert.showAndWait();
        }
        else {
            ArrayList<Player> homeStartingLineUp = new ArrayList<>(homeSet);
            ArrayList<Player> awayStartingLineUp = new ArrayList<>(awaySet);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/match.fxml"));
            MatchController ctrl = new MatchController(this.homeClub, this.awayClub, homeStartingLineUp, awayStartingLineUp, this.clubsOnTable);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Match");
            stage.setScene(scene);
            stage.show();

            Stage stage2 = (Stage) okButton.getScene().getWindow();
            stage2.close();
        }
    }

    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
