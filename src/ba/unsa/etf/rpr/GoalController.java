package ba.unsa.etf.rpr;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GoalController {

    public ChoiceBox<Player> goalScorer;
    public ChoiceBox<Player> assistProvider;

    private ObservableList<Player> players;
    private ObservableList<Goal> goals;

    public RadioButton rightFoot, leftFoot, head;
    public RadioButton insideBox, outsideBox;
    public RadioButton penalty, freeKick, openPlay;

    public Button okButton;
    public Button cancelButton;


    GoalController(ArrayList<Player> p, ObservableList<Goal> g) {
        players = (ObservableList<Player>) p;
        this.goals=g;
        ToggleGroup goalTypeGroup = new ToggleGroup();
        ToggleGroup goalDistanceGroup = new ToggleGroup();
        ToggleGroup goalSituationGroup = new ToggleGroup();
        rightFoot.setToggleGroup(goalTypeGroup);
        leftFoot.setToggleGroup(goalTypeGroup);
        head.setToggleGroup(goalTypeGroup);
        insideBox.setToggleGroup(goalDistanceGroup);
        outsideBox.setToggleGroup(goalDistanceGroup);
        penalty.setToggleGroup(goalSituationGroup);
        freeKick.setToggleGroup(goalSituationGroup);
        openPlay.setToggleGroup(goalSituationGroup);
    }

    @FXML
    public void initialize() {
        goalScorer.setItems(players);
        assistProvider.setItems(players);
    }

    public void okPressed (ActionEvent actionEvent) {
        boolean irregular = false;

        GoalType goalType = GoalType.HEADER;
        if (rightFoot.isSelected()) goalType = GoalType.RIGHTFOOT;
        else if (leftFoot.isSelected()) goalType = GoalType.LEFTFOOT;
        else if (head.isSelected()) goalType = GoalType.HEADER;
        else irregular = true;

        GoalDistance goalDistance = GoalDistance.INSIDEBOX;
        if (outsideBox.isSelected()) goalDistance=GoalDistance.OUTSIDEBOX;
        else if (insideBox.isSelected()) goalDistance=GoalDistance.INSIDEBOX;
        else irregular = true;

        GoalSituation goalSituation = GoalSituation.OPENPLAY;
        if (penalty.isSelected()) goalSituation=GoalSituation.PENALTY;
        else if (freeKick.isSelected()) goalSituation=GoalSituation.FREEKICK;
        else if (openPlay.isSelected()) goalSituation=GoalSituation.OPENPLAY;
        else irregular = true;

        if (goalScorer.getValue()==null) irregular=true;
        if (assistProvider.getValue()!=null && goalScorer.getValue()==assistProvider.getValue()) irregular=true;


        if (irregular==true) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Niste dobro unijeli podatke");
        }

        Goal goal = new Goal(goalScorer.getValue(), assistProvider.getValue(), goalType, goalSituation, goalDistance);
        this.goals.add(goal);

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
