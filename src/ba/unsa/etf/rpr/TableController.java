package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class TableController {
    private ArrayList<Club> allClubs;
    private ArrayList<Match> allMatches;
    public TableView tableViewTable = new TableView();
    public TableColumn tableColumnClubs, tableColumnPlayed, tableColumnWins, tableColumnDraws, tableColumnLosses;
    public TableColumn tableColumnGoalsScored, tableColumnGoalsConceded, tableColumnGoalDifference, tableColumnPoints;

    TableController (ArrayList<Club> clubs, ArrayList<Match> matches) {
        this.allClubs=clubs;
        this.allMatches=matches;
    }

    @FXML
    public void initialize() {
        tableColumnClubs.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Club>("Club"));
        tableColumnPlayed.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("P"));
        tableColumnWins.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("W"));
        tableColumnDraws.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("D"));
        tableColumnLosses.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("L"));
        tableColumnGoalsScored.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("GS"));
        tableColumnGoalsConceded.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("GC"));
        tableColumnGoalDifference.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("GD"));
        tableColumnPoints.setCellValueFactory(new PropertyValueFactory<ClubOnTable, Integer>("Pts"));
        ObservableList<ClubOnTable> clubsOnTable = FXCollections.observableArrayList();
        for (int i=0; i<this.allClubs.size(); i++) {
            clubsOnTable.add(new ClubOnTable(this.allClubs.get(i), this.allMatches));
        }
        tableViewTable.setItems(clubsOnTable);
    }

}
