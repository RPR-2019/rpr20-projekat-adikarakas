package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Fixture;
import ba.unsa.etf.rpr.beans.Result;
import ba.unsa.etf.rpr.other.ClubOnTable;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class SeasonController {
    public TableView<ClubOnTable> tableViewTable = new TableView<>();
    public TableColumn<ClubOnTable, String> tableColumnPosition;
    public TableColumn<ClubOnTable, Club> tableColumnClubs;
    public TableColumn<ClubOnTable, Integer> tableColumnPlayed;
    public TableColumn<ClubOnTable, Integer> tableColumnWins;
    public TableColumn<ClubOnTable, Integer> tableColumnDraws;
    public TableColumn<ClubOnTable, Integer> tableColumnLosses;
    public TableColumn<ClubOnTable, Integer> tableColumnGoalsScored;
    public TableColumn<ClubOnTable, Integer> tableColumnGoalsConceded;
    public TableColumn<ClubOnTable, Integer> tableColumnGoalDifference;
    public TableColumn<ClubOnTable, Integer> tableColumnPoints;
    private final ObservableList<Fixture> fixtures = FXCollections.observableArrayList();
    private final ObservableList<Result> results = FXCollections.observableArrayList();
    public ListView<Fixture> fixturesList;
    public ListView<Result> resultsList;
    public Button addGameButton;
    public Button playGameButton;
    public Button seeReportButton;
    public Button finishSeasonButton;
    private final LeagueDAO dao;
    ObservableList<ClubOnTable> clubsOnTable = FXCollections.observableArrayList();
    public Label statusBarLabel;
    private final List<Club> clubs;
    private static final String TRANSLATION="Translation";
    private static final String ENGLISH="English";

    public SeasonController() {
        dao=LeagueDAO.getInstance();
        clubs=new ArrayList<>(dao.clubs());
    }

    @FXML
    public void initialize() {
        tableColumnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        tableColumnClubs.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnPlayed.setCellValueFactory(new PropertyValueFactory<>("played"));
        tableColumnWins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        tableColumnDraws.setCellValueFactory(new PropertyValueFactory<>("draws"));
        tableColumnLosses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        tableColumnGoalsScored.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        tableColumnGoalsConceded.setCellValueFactory(new PropertyValueFactory<>("goalsConceded"));
        tableColumnGoalDifference.setCellValueFactory(new PropertyValueFactory<>("goalDifference"));
        tableColumnPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        if (Locale.getDefault().equals(new Locale("en", "EN"))) {
            setTooltips(tableColumnPlayed, "P", "Played games");
            setTooltips(tableColumnWins, "W", "Wins");
            setTooltips(tableColumnDraws, "D", "Draws");
            setTooltips(tableColumnLosses, "L", "Losses");
            setTooltips(tableColumnGoalsScored, "GS", "Goals scored by team");
            setTooltips(tableColumnGoalsConceded, "GC", "Goals conceded by team");
            setTooltips(tableColumnGoalDifference, "GD", "Goal difference");
            setTooltips(tableColumnPoints, "Pts", "Points");
        }

        else if (Locale.getDefault().equals(new Locale("bs", "BA"))) {
            setTooltips(tableColumnPlayed, "O", "Odigrane utakmice");
            setTooltips(tableColumnWins, "P", "Pobjede");
            setTooltips(tableColumnDraws, "N", "Neriješene utakmice");
            setTooltips(tableColumnLosses, "I", "Izgubljene utakmice");
            setTooltips(tableColumnGoalsScored, "DG", "Dati golovi");
            setTooltips(tableColumnGoalsConceded, "PG", "Primljeni golovi");
            setTooltips(tableColumnGoalDifference, "GR", "Gol-razlika");
            setTooltips(tableColumnPoints, "Bod", "Bodovi");
        }


        ArrayList<Result> allResults = new ArrayList<>(dao.results());
        ArrayList<Fixture> allFixtures = new ArrayList<>(dao.fixtures());

        for (Result allResult : allResults) {
            results.add(allResult);
        }
        resultsList.setItems(results);

        for (Fixture allFixture : allFixtures) {
            fixtures.add(allFixture);
        }
        fixturesList.setItems(fixtures);

        if (allResults.size() == (clubs.size())*(clubs.size()-1)) {
            if (Locale.getDefault().equals(new Locale("en", "EN"))) statusBarLabel.setText("All games have been played");
            else if (Locale.getDefault().equals(new Locale("bs", "BA"))) statusBarLabel.setText("Sve utakmice su odigrane");
        }

        for (Club club : clubs) {
            clubsOnTable.add(new ClubOnTable(club, results));
        }
        this.clubsOnTable.sort((o1, o2) -> o2.compareTo(o1));
        for (int i=0; i<this.clubsOnTable.size(); i++) {
            this.clubsOnTable.get(i).setPosition((i+1) + ".");
        }
        tableViewTable.setItems(this.clubsOnTable);
        tableViewTable.refresh();
    }

    public void addGame() throws IOException {
        if ((dao.fixtures().size() + dao.results().size())==((clubs.size())*(clubs.size()-1))) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setContentText("All fixtures have been added");
            alert.showAndWait();
        }
        else {
            ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/fixtureAdder.fxml"), bundle);
            FixtureAdderController ctrl = new FixtureAdderController();
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Fixture adder");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            stage.setWidth(stage.getWidth() + 10);
        }
    }

    public void playGame () throws IOException {
        if (fixturesList.getSelectionModel().getSelectedItem()!=null) {
            ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/squad.fxml"), bundle);
            SquadController ctrl = new SquadController(fixturesList.getSelectionModel().getSelectedItem().getHomeTeam(), fixturesList.getSelectionModel().getSelectedItem().getAwayTeam(), this.clubsOnTable);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Squad");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            stage.setWidth(stage.getWidth() + 10);
        }
    }

    public void seeReport () throws IOException {
        if (resultsList.getSelectionModel().getSelectedItem()!=null) {
            ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/result.fxml"), bundle);
            ResultController ctrl = new ResultController(resultsList.getSelectionModel().getSelectedItem());
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setResizable(false);
            stage.setTitle("Report");
            stage.setScene(scene);
            stage.show();
            stage.setWidth(stage.getWidth() + 10);
        }
    }

    public void openStats () throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/stats.fxml"), bundle);
        StatsController ctrl = new StatsController();
        fxmlLoader.setController(ctrl);
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setMinHeight(761);
        stage.setMinWidth(1072);
        stage.setTitle("Stats");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(stage.getWidth() + 10);
    }

    public void finish () throws IOException {
        if (dao.results().size()!=(clubs.size()-1)*(clubs.size())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You can't finish a season before all games have been played.");
            alert.showAndWait();
        }
        else {
            ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/finish.fxml"), bundle);
            FinishController ctrl = new FinishController(this.clubsOnTable.get(0).getClub());
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setTitle("Finish");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.setWidth(stage.getWidth() + 10);

            Stage stage2 = (Stage) finishSeasonButton.getScene().getWindow();
            stage2.close();
        }
    }

    public void setLanguage() throws IOException {
        List<String> choices = new ArrayList<>();
        choices.add("Bosanski");
        choices.add(ENGLISH);

        ChoiceDialog<String> dialog = new ChoiceDialog<>(ENGLISH, choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Language chooser");
        dialog.setContentText("Choose your language:");

        String previous = dao.readLanguage();
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (("Bosanski").equals(result.get())) Locale.setDefault(new Locale("bs", "BA"));
            else if ((ENGLISH).equals(result.get())) Locale.setDefault(new Locale("en", "EN"));
            dao.writeLanguage(result.get());
            if (!previous.equals(dao.readLanguage())) {
                Stage stage = (Stage) addGameButton.getScene().getWindow();
                stage.close();

                ResourceBundle bundle = ResourceBundle.getBundle(TRANSLATION);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/season.fxml"), bundle);
                SeasonController ctrl = new SeasonController();
                fxmlLoader.setController(ctrl);
                Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
                Stage stage2 = new Stage();
                stage.setMinHeight(400);
                stage.setMinWidth(1000);
                stage.setTitle("Season");
                stage2.setScene(scene);
                stage2.show();
                stage.setWidth(stage.getWidth() + 10);
            }
        }
    }

    private void setTooltips(TableColumn tc, String tag, String tip) {
        Label playedLabel = new Label(tag);
        playedLabel.setTooltip(new Tooltip(tip));
        tc.setGraphic(playedLabel);
    }
}
