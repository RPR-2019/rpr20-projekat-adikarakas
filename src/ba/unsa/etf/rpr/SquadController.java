package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SquadController {
    public Button okButton, cancelButton;
    public TextField position1, position2, position3, position4, position5, position6, position7, position8, position9, position10, position11;
    public ChoiceBox<Player> playerChoiceBox1, playerChoiceBox2, playerChoiceBox3, playerChoiceBox4, playerChoiceBox5, playerChoiceBox6, playerChoiceBox7, playerChoiceBox8, playerChoiceBox9, playerChoiceBox10, playerChoiceBox11;
    private ObservableList<Player> goalkeepers, defenders, midfielders, attackers;

    SquadController (ArrayList<Player> allPlayers) {
        this.goalkeepers = FXCollections.observableArrayList();
        this.defenders = FXCollections.observableArrayList();
        this.midfielders = FXCollections.observableArrayList();
        this.attackers = FXCollections.observableArrayList();
        for (int i=0; i<allPlayers.size(); i++) {
            if (allPlayers.get(i).getPosition().equals(Position.GOALKEEPER)) this.goalkeepers.add(allPlayers.get(i));
            else if (allPlayers.get(i).getPosition().equals(Position.DEFENDER)) this.defenders.add(allPlayers.get(i));
            else if (allPlayers.get(i).getPosition().equals(Position.MIDFIELDER)) this.midfielders.add(allPlayers.get(i));
            else this.attackers.add(allPlayers.get(i));
        }
    }

    public void initialize() {
        // za početak će biti fiksna formacija 4-3-3
        position1.setText("Goalkeeper:");
        position2.setText("Defender:");
        position3.setText("Defender:");
        position4.setText("Defender:");
        position5.setText("Defender:");
        position6.setText("Midfielder:");
        position7.setText("Midfielder:");
        position8.setText("Midfielder:");
        position9.setText("Attacker:");
        position10.setText("Attacker:");
        position11.setText("Attacker:");

        playerChoiceBox1.setItems(this.goalkeepers);
        playerChoiceBox2.setItems(this.defenders);
        playerChoiceBox3.setItems(this.defenders);
        playerChoiceBox4.setItems(this.defenders);
        playerChoiceBox5.setItems(this.defenders);
        playerChoiceBox6.setItems(this.midfielders);
        playerChoiceBox7.setItems(this.midfielders);
        playerChoiceBox8.setItems(this.midfielders);
        playerChoiceBox9.setItems(this.attackers);
        playerChoiceBox10.setItems(this.attackers);
        playerChoiceBox11.setItems(this.attackers);

        // defaultna postava
        playerChoiceBox1.setValue(this.goalkeepers.get(0));
        playerChoiceBox2.setValue(this.defenders.get(0));
        playerChoiceBox3.setValue(this.defenders.get(1));
        playerChoiceBox4.setValue(this.defenders.get(2));
        playerChoiceBox5.setValue(this.defenders.get(3));
        playerChoiceBox6.setValue(this.midfielders.get(0));
        playerChoiceBox7.setValue(this.midfielders.get(1));
        playerChoiceBox8.setValue(this.midfielders.get(2));
        playerChoiceBox9.setValue(this.attackers.get(0));
        playerChoiceBox10.setValue(this.attackers.get(1));
        playerChoiceBox11.setValue(this.attackers.get(2));
    }

    public void okPressed(ActionEvent actionEvent) {
        Set<Player> set = new HashSet<Player>();
        set.add(playerChoiceBox1.getValue());
        set.add(playerChoiceBox2.getValue());
        set.add(playerChoiceBox3.getValue());
        set.add(playerChoiceBox4.getValue());
        set.add(playerChoiceBox5.getValue());
        set.add(playerChoiceBox6.getValue());
        set.add(playerChoiceBox7.getValue());
        set.add(playerChoiceBox8.getValue());
        set.add(playerChoiceBox9.getValue());
        set.add(playerChoiceBox10.getValue());
        set.add(playerChoiceBox11.getValue());

        if (set.size()!=11) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Nepravilan odabir igrača");
            alert.setContentText("Neki igrači se ponavljaju");
            alert.showAndWait();
        }
        else {
            ArrayList<Player> startingLineUp = new ArrayList<>(set);

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
