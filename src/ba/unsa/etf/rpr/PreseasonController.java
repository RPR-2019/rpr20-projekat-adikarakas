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
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PreseasonController {
    public Button addClubButton, editClubButton, deleteClubButton, startButton;
    public ListView<Club> clubsLv;
    private int numberOfClubs;
    private LeagueDAO dao;

    PreseasonController (int number) {
        this.numberOfClubs=number;
    }

    @FXML
    public void initialize() {
        ObservableList<Club> list = FXCollections.observableArrayList();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            list.add(dao.getInstance().clubs().get(i));
        }
        clubsLv.setItems(list);
        clubsLv.refresh();
    }

    public void addClubPressed (ActionEvent actionEvent) throws IOException {
        clubsLv.refresh();
        if (dao.getInstance().clubs().size()==this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Dostigli ste limit");
            alert.setContentText("Ne možete dodavati nove timove u ligu");
            alert.showAndWait();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/club.fxml"));
            ClubController ctrl = new ClubController(null);
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
            ClubController ctrl = new ClubController(clubsLv.getSelectionModel().getSelectedItem());
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
                dao.getInstance().deleteClub(temp.getName());
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

    public void startPressed (ActionEvent actionEvent) throws IOException {
        boolean start = true;
        if (dao.getInstance().clubs().size()!=this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Liga nije popunjena");
            alert.setContentText("Ne možete pokrenuti novu sezonu dok se liga ne popuni");
            alert.showAndWait();
            start = false;
        }
        else {
            for (int i = 0; i < dao.getInstance().getNumberOfClubs(); i++) {
                Club temp = dao.getInstance().clubs().get(i);
                if (temp.getPlayers().size() < 15) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Nedovoljno igrača");
                    alert.setContentText("Klub " + dao.getInstance().clubs().get(i) + " ima manje od 15 igrača.");
                    alert.showAndWait();
                    start = false;
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
                    alert.setContentText("Klub " + dao.getInstance().clubs().get(i) + " nema dovoljno igrača po pozicijama.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
                if (temp.getCaptain()==null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Nema kapitena");
                    alert.setContentText("Klub " + dao.getInstance().clubs().get(i) + " nije izabrao kapitena.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
                if (temp.getManager().trim().length()==0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Nema menadžera");
                    alert.setContentText("Klub " + dao.getInstance().clubs().get(i) + " nije imenovao menadžera.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
            }
        }
        if (start) {
            if (dao.getInstance().isScheduleRandom()) scheduleGenerator(dao.getInstance().clubs());
            dao.getInstance().setStarted(true);
            dao.getInstance().createStats();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/season.fxml"));
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

    // ovo popraviti kad dodamo tabelu za kola

    public void scheduleGenerator(ArrayList<Club> clubs) {

        // round-robin algoritam
        int numTeams = clubs.size();
        int numDays = (numTeams - 1);
        int halfSize = numTeams / 2;

        List<Club> teams = new ArrayList<Club>();

        teams.addAll(clubs);
        teams.remove(0);

        int teamsSize = teams.size();

        for (int day = 0; day < numDays; day++) {
            int teamIdx = day % teamsSize;
            dao.getInstance().fixtures().add(new Fixture(teams.get(teamIdx), clubs.get(0)));
            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                dao.getInstance().fixtures().add(new Fixture(teams.get(firstTeam), teams.get(secondTeam)));
            }
        }
        for (int day = 0; day < numDays; day++) {
            int teamIdx = day % teamsSize;
            dao.getInstance().fixtures().add(new Fixture(clubs.get(0), teams.get(teamIdx)));
            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                dao.getInstance().fixtures().add(new Fixture(teams.get(secondTeam), teams.get(firstTeam)));
            }
        }
    }
}
