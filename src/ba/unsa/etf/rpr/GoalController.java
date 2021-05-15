package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.ArrayList;
import java.util.Comparator;

public class GoalController {

    public ChoiceBox<Player> goalScorer;
    public ChoiceBox<Player> assistProvider;

    private ObservableList<Player> players = FXCollections.observableArrayList();
    private ObservableList<Goal> goals;

    public RadioButton rightFoot = new RadioButton("Right foot");
    public RadioButton leftFoot = new RadioButton("Left foot");
    public RadioButton head = new RadioButton("Head");

    public RadioButton insideBox = new RadioButton("Inside box");
    public RadioButton outsideBox = new RadioButton("Outside box");

    public RadioButton penalty = new RadioButton("Penalty");
    public RadioButton freeKick = new RadioButton("Free kick");
    public RadioButton openPlay = new RadioButton("Open play");

    public Button okButton;
    public Button cancelButton;

    public Spinner<Integer> minuteSpinner;

    private boolean home;

    GoalController(ArrayList<Player> p, ObservableList<Goal> g, boolean home) {
        for (int i=0; i<p.size(); i++) {
            this.players.add(p.get(i));
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

        if (goalScorer.getValue()==null)  {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Morate odrediti strijelca");
            alert.showAndWait();
        }
        else if (assistProvider.getValue()!=null && goalScorer.getValue()==assistProvider.getValue()) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Asistent i strijelac ne mogu biti ista osoba");
            alert.showAndWait();
        }
        else if (assistProvider.getValue()!=null && goalSituation!=GoalSituation.OPENPLAY) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Kod penala i slobodnih udaraca se ne dodjeljuje asistencija");
            alert.showAndWait();
        }
        else if (goalSituation==GoalSituation.PENALTY && goalDistance!=GoalDistance.INSIDEBOX) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Penal se izvodi sa 11 metara");
            alert.showAndWait();
        }
        else if (goalSituation==GoalSituation.FREEKICK && goalDistance!=GoalDistance.OUTSIDEBOX) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Slobodan udarac se izvodi izvan šesnaesterca");
            alert.showAndWait();
        }
        else if (irregular==true) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Pogrešan unos");
            alert.setHeaderText("Niste dobro unijeli podatke");
            alert.showAndWait();
        }

        else {

            Goal goal = new Goal(goalScorer.getValue(), assistProvider.getValue(), minuteSpinner.getValue(), goalType, goalSituation, goalDistance);
            this.goals.add(goal);
            this.goals.sort(new Comparator<Goal>() {
                @Override
                public int compare(Goal o1, Goal o2) {
                    return o1.getMinute()-o2.getMinute();
                }
            });

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

    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
