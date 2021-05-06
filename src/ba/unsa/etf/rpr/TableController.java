package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class TableController {
    public TableView tableViewTable = new TableView();
    public TableColumn tableColumnPosition, tableColumnClubs, tableColumnPlayed, tableColumnWins, tableColumnDraws, tableColumnLosses;
    public TableColumn tableColumnGoalsScored, tableColumnGoalsConceded, tableColumnGoalDifference, tableColumnPoints;
    private ObservableList<Fixture> fixtures = FXCollections.observableArrayList();
    private ObservableList<Match> matches = FXCollections.observableArrayList();
    public ListView<Fixture> fixturesList;
    public ListView<Match> resultsList;
    public Button addGameButton, playGameButton, seeReportButton, refreshButton;
    private League league;
    ObservableList<ClubOnTable> clubsOnTable = FXCollections.observableArrayList();

    TableController (League league) {
        this.league=league;
    }

    @FXML
    public void initialize() {
        tableColumnPosition.setCellValueFactory(new PropertyValueFactory<ClubOnTable, String>("position"));
        tableColumnClubs.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Club>("club"));
        tableColumnPlayed.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("played"));
        tableColumnWins.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("wins"));
        tableColumnDraws.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("draws"));
        tableColumnLosses.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("losses"));
        tableColumnGoalsScored.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("goalsScored"));
        tableColumnGoalsConceded.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("goalsConceded"));
        tableColumnGoalDifference.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("goalDifference"));
        tableColumnPoints.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("points"));
        for (int i=0; i<this.league.getClubs().size(); i++) {
            clubsOnTable.add(new ClubOnTable(this.league.getClubs().get(i), this.league.getMatches()));
        }
        this.clubsOnTable.sort(new Comparator<ClubOnTable>() {
            @Override
            public int compare(ClubOnTable o1, ClubOnTable o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i=0; i<this.clubsOnTable.size(); i++) {
            this.clubsOnTable.get(i).setPosition((i+1) + ".");
        }
        tableViewTable.setItems(this.clubsOnTable);
        tableViewTable.refresh();


        if (this.league.getMatches()!=null) {
            for (int i = 0; i < this.league.getMatches().size(); i++) {
                matches.add(this.league.getMatches().get(i));
            }
            resultsList.setItems(matches);
        }


        if (this.league.getFixtures()!=null) {
            for (int i = 0; i < this.league.getFixtures().size(); i++) {
                fixtures.add(this.league.getFixtures().get(i));
            }
            fixturesList.setItems(fixtures);
        }
    }

    public void addGame(ActionEvent actionEvent) throws IOException {
        if (this.league.isScheduleRandom()) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setContentText("Raspored je automatski generisan");
            alert.showAndWait();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/fixtureAdder.fxml"));
            FixtureAdderController ctrl = new FixtureAdderController(this.league);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Fixture adder");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void playGame (ActionEvent actionEvent) throws IOException {
        if (fixturesList.getSelectionModel().getSelectedItem()!=null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/squad.fxml"));
            SquadController ctrl = new SquadController(this.league, fixturesList.getSelectionModel().getSelectedItem().getHomeTeam(), fixturesList.getSelectionModel().getSelectedItem().getAwayTeam(), this.clubsOnTable);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Squad");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void seeReport (ActionEvent actionEvent) throws IOException {

    }

    public void openStats (ActionEvent actionEvent) {

    }

    public void finish (ActionEvent actionEvent) {

    }

    public void refresh (ActionEvent actionEvent) {
        this.clubsOnTable.clear();
        tableViewTable.setItems(null);
        for (int i=0; i<this.league.getNumberOfClubs(); i++) {
            this.clubsOnTable.add(new ClubOnTable(this.league.getClubs().get(i), this.league.getMatches()));
        }
        tableViewTable.setItems(this.clubsOnTable);
        tableViewTable.sort();
    }

}
