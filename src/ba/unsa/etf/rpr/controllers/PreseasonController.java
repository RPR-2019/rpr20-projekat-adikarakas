package ba.unsa.etf.rpr.controllers;

// probati PMD još srediti

import ba.unsa.etf.rpr.beans.*;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PreseasonController {
    public Button startButton;
    public ListView<Club> clubsLv;
    private final LeagueDAO dao;
    private static final String MESSAGE = "Greška";

    public PreseasonController() {
        dao=LeagueDAO.getInstance();
    }

    @FXML
    public void initialize() {
        ObservableList<Club> list = FXCollections.observableArrayList();
        list.addAll(dao.clubs());
        clubsLv.setItems(list);
        clubsLv.refresh();
    }

    public void addClubPressed () throws IOException {
        clubsLv.refresh();
        if (dao.clubs().size()==30) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(MESSAGE);
            alert.setHeaderText("Dostigli ste limit");
            alert.setContentText("Ne možete dodavati nove timove u ligu");
            alert.showAndWait();
        }
        else {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/club.fxml"), bundle);
            ClubController ctrl = new ClubController(null);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setResizable(false);
            stage.setTitle("Club");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void editClubPressed () throws IOException {
        if (clubsLv.getSelectionModel().getSelectedItem()!=null) {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/club.fxml"), bundle);
            ClubController ctrl = new ClubController(clubsLv.getSelectionModel().getSelectedItem());
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Club");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void deleteClubPressed () {
        Club temp = clubsLv.getSelectionModel().getSelectedItem();
        if (temp!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda");
            alert.setContentText("Jeste li sigurni da želite izbrisati klub " + temp.getName() + "?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                dao.deleteClub(temp.getName());
                clubsLv.getItems().remove(temp);
            }
        }
    }

    public void addPlayerPressed () throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"), bundle);
        PlayerController ctrl = new PlayerController(clubsLv.getSelectionModel().getSelectedItem(), null);
        fxmlLoader.setController(ctrl);
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        Stage stage = new Stage();
        stage.setMinHeight(150);
        stage.setMinWidth(400);
        stage.setTitle("Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void startPressed () throws IOException {
        boolean start = true;
        if (dao.clubs().size()%2==1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(MESSAGE);
            alert.setHeaderText("Broj klubova neparan");
            alert.setContentText("Liga mora imati paran broj klubova");
            alert.showAndWait();
            start = false;
        }
        else if (dao.clubs().size()<2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(MESSAGE);
            alert.setHeaderText("Nedovoljno klubova");
            alert.setContentText("Liga mora imati barem 2 kluba");
            alert.showAndWait();
            start = false;
        }
        else {
            for (int i = 0; i < dao.clubs().size(); i++) {
                Club temp = dao.clubs().get(i);
                if (dao.playersInClub(temp).size() < 15) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(MESSAGE);
                    alert.setHeaderText("Nedovoljno igrača");
                    alert.setContentText("Klub " + dao.clubs().get(i) + " ima manje od 15 igrača.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
                ArrayList<Player> goalkeepers = new ArrayList<>();
                ArrayList<Player> defenders = new ArrayList<>();
                ArrayList<Player> midfielders = new ArrayList<>();
                ArrayList<Player> attackers = new ArrayList<>();
                for (int j = 0; j < dao.playersInClub(temp).size(); j++) {
                    if (dao.playersInClub(temp).get(j) instanceof Goalkeeper) goalkeepers.add(dao.playersInClub(temp).get(j));
                    else if (dao.playersInClub(temp).get(j) instanceof Defender) defenders.add(dao.playersInClub(temp).get(j));
                    else if (dao.playersInClub(temp).get(j) instanceof Midfielder) midfielders.add(dao.playersInClub(temp).get(j));
                    else attackers.add(dao.playersInClub(temp).get(j));
                }
                if (goalkeepers.isEmpty() || defenders.size() < 4 || midfielders.size() < 3 || attackers.size() < 3) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(MESSAGE);
                    alert.setHeaderText("Nedovoljno igrača");
                    alert.setContentText("Klub " + dao.clubs().get(i) + " nema dovoljno igrača po pozicijama.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
                if (temp.getCaptain()==null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(MESSAGE);
                    alert.setHeaderText("Nema kapitena");
                    alert.setContentText("Klub " + dao.clubs().get(i) + " nije izabrao kapitena.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
                if (temp.getManager().isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(MESSAGE);
                    alert.setHeaderText("Nema menadžera");
                    alert.setContentText("Klub " + dao.clubs().get(i) + " nije imenovao menadžera.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
            }
        }
        if (start) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Želite li unaprijed generisan raspored?");

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yes, no, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == yes) scheduleGenerator(dao.clubs());

            if (result.get() == yes || result.get() == no) {
                dao.createStats();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/season.fxml"), bundle);
                SeasonController ctrl = new SeasonController();
                fxmlLoader.setController(ctrl);
                Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
                Stage stage = new Stage();
                stage.setMinHeight(150);
                stage.setMinWidth(400);
                stage.setTitle("Season");
                stage.setScene(scene);
                stage.show();

                Stage stage2 = (Stage) startButton.getScene().getWindow();
                stage2.close();
            }
        }
    }

    public void setLanguage() {
        List<String> choices = new ArrayList<>();
        choices.add("Bosanski");
        choices.add("English");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("English", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Look, a Choice Dialog");
        dialog.setContentText("Choose your language:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if (("Bosanski").equals(result.get())) Locale.setDefault(new Locale("bs", "BA"));
            else if (("English").equals(result.get())) Locale.setDefault(new Locale("en", "EN"));
        }
    }



    public void scheduleGenerator(List<Club> clubs) {

        // round-robin algoritam
        int numTeams = clubs.size();
        int numDays = (numTeams - 1);
        int halfSize = numTeams / 2;

        List<Club> teams = new ArrayList<>(clubs);
        teams.remove(0);

        int teamsSize = teams.size();

        for (int day = 0; day < numDays; day++) {
            int teamIdx = day % teamsSize;
            dao.addFixture(new Fixture(teams.get(teamIdx), clubs.get(0)));
            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                dao.addFixture(new Fixture(teams.get(firstTeam), teams.get(secondTeam)));
            }
        }
        for (int day = 0; day < numDays; day++) {
            int teamIdx = day % teamsSize;
            dao.addFixture(new Fixture(clubs.get(0), teams.get(teamIdx)));
            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                dao.addFixture(new Fixture(teams.get(secondTeam), teams.get(firstTeam)));
            }
        }
    }
}
