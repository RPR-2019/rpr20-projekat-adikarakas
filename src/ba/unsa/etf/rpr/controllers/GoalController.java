package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Goal;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.other.GoalDistance;
import ba.unsa.etf.rpr.other.GoalSituation;
import ba.unsa.etf.rpr.other.GoalType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.List;

public class GoalController {

    public ChoiceBox<Player> goalScorer;
    public ChoiceBox<Player> assistProvider;

    private final ObservableList<Player> players = FXCollections.observableArrayList();
    private final ObservableList<Goal> goals;

    public RadioButton rightFoot = new RadioButton("Right foot");
    public RadioButton leftFoot = new RadioButton("Left foot");
    public RadioButton head = new RadioButton("Head");

    public RadioButton insideBox = new RadioButton("Inside box");
    public RadioButton outsideBox = new RadioButton("Outside box");

    public RadioButton penalty = new RadioButton("Penalty");
    public RadioButton freeKick = new RadioButton("Free kick");
    public RadioButton openPlay = new RadioButton("Open play");

    public Button okButton;
    public Button cancelGoalButton;

    public Spinner<Integer> minuteSpinner;

    private final boolean home;

    GoalController(List<Player> p, ObservableList<Goal> g, boolean home) {
        for (Player player : p) {
            this.players.add(player);
        }
        this.goals=g;
        this.home=home;
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 90);
        valueFactory.setValue(1);
        minuteSpinner.setValueFactory(valueFactory);

        goalScorer.setItems(players);
        assistProvider.setItems(players);

        assistProvider.getItems().add(null);

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

    public void okPressed () {
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

        String message = "Wrong input";
        if (goalScorer.getValue()==null)  {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle(message);
            alert.setHeaderText("You must select a scorer");
            alert.showAndWait();
            goalScorer.requestFocus();
        }
        else if (assistProvider.getValue()!=null && goalScorer.getValue()==assistProvider.getValue()) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle(message);
            alert.setHeaderText("Scorer and assist provider can't be the same person");
            alert.showAndWait();
            assistProvider.requestFocus();
        }
        else if (assistProvider.getValue()!=null && goalSituation!=GoalSituation.OPENPLAY) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle(message);
            alert.setHeaderText("Player can't get assist for direct free-kick goals and penalties");
            alert.showAndWait();
            assistProvider.requestFocus();
        }
        else if (goalSituation==GoalSituation.PENALTY && goalDistance!=GoalDistance.INSIDEBOX) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle(message);
            alert.setHeaderText("Penalty is taken from 11 meters, which is inside box");
            alert.showAndWait();
        }
        else if (goalSituation==GoalSituation.FREEKICK && goalDistance!=GoalDistance.OUTSIDEBOX) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle(message);
            alert.setHeaderText("Free kicks are taken outside box");
            alert.showAndWait();
        }
        else if (irregular) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle(message);
            alert.setHeaderText("Wrong data");
            alert.showAndWait();
        }

        else {

            Goal goal = new Goal(goalScorer.getValue(), assistProvider.getValue(), minuteSpinner.getValue(), goalType, goalSituation, goalDistance);
            this.goals.add(goal);
            this.goals.sort((o1, o2) -> o1.getMinute()-o2.getMinute());

            if (this.home) {
                FxRobot robot = new FxRobot();
                TextField homeTeamScore= robot.lookup("#homeTeamScore").queryAs(TextField.class);
                homeTeamScore.setText(String.valueOf(this.goals.size()));
            }
            else {
                FxRobot robot = new FxRobot();
                TextField awayTeamScore= robot.lookup("#awayTeamScore").queryAs(TextField.class);
                awayTeamScore.setText(String.valueOf(this.goals.size()));
            }
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelPressed() {
        Stage stage = (Stage) cancelGoalButton.getScene().getWindow();
        stage.close();
    }
}
