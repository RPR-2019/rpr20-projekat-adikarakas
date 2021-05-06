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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PreseasonController {
    public Button addClubButton, editClubButton, deleteClubButton, startButton;
    public ListView<Club> clubsLv;
    private int numberOfClubs;
    private League league;

    PreseasonController (League league, int number) {
        this.numberOfClubs=number;
        this.league=league;
/*        league.getClubs().get(0).addPlayer(new Goalkeeper("Edouard", "Mendy", LocalDate.of(1992, 03, 13), "Senegal"));
        league.getClubs().get(0).addPlayer(new Goalkeeper("Kepa", "Arizzabalaga",LocalDate.of(1994, 02, 13), "Spain"));
        league.getClubs().get(0).addPlayer(new Defender("Reece", "James", LocalDate.of(1999, 04, 13), "England"));
        league.getClubs().get(0).addPlayer(new Defender("Thiago", "Silva", LocalDate.of(1984, 05, 13), "Brazil"));
        league.getClubs().get(0).addPlayer(new Defender("Kurt", "Zouma", LocalDate.of(1995, 06, 13), "France"));
        league.getClubs().get(0).addPlayer(new Defender("Ben", "Chilwell", LocalDate.of(1996, 07, 13), "England"));
        league.getClubs().get(0).addPlayer(new Defender("Cesar", "Azpilicueta", LocalDate.of(1989, 8, 13), "Spain"));
        league.getClubs().get(0).addPlayer(new Midfielder("Ngolo", "Kante", LocalDate.of(1991, 9, 13), "France"));
        league.getClubs().get(0).addPlayer(new Midfielder("Mateo", "Kovačić", LocalDate.of(1995, 10, 13), "Croatia"));
        league.getClubs().get(0).addPlayer(new Midfielder("Mason", "Mount", LocalDate.of(1999, 11, 13), "England"));
        league.getClubs().get(0).addPlayer(new Midfielder("Kai", "Havertz", LocalDate.of(1999, 12, 13), "Germany"));
        league.getClubs().get(0).addPlayer(new Attacker("Timo", "Werner", LocalDate.of(1996, 10, 13), "Germany"));
        league.getClubs().get(0).addPlayer(new Attacker("Olivier", "Giroud", LocalDate.of(1986, 11, 13), "France"));
        league.getClubs().get(0).addPlayer(new Attacker("Christian", "Pulišić", LocalDate.of(1998, 03, 13), "USA"));
        league.getClubs().get(0).addPlayer(new Attacker("Hakim", "Ziyech", LocalDate.of(1993, 01, 13), "Morocco"));

        league.getClubs().get(1).addPlayer(new Goalkeeper("Bernd", "Leno", LocalDate.of(1992, 03, 13), "Germany"));
        league.getClubs().get(1).addPlayer(new Goalkeeper("Mat", "Ryan",LocalDate.of(1994, 02, 13), "Australia"));
        league.getClubs().get(1).addPlayer(new Defender("Hector", "Bellerin", LocalDate.of(1999, 04, 13), "Spain"));
        league.getClubs().get(1).addPlayer(new Defender("David", "Luiz", LocalDate.of(1984, 05, 13), "Brazil"));
        league.getClubs().get(1).addPlayer(new Defender("Rob", "Holding", LocalDate.of(1995, 06, 13), "England"));
        league.getClubs().get(1).addPlayer(new Defender("Kieran", "Tierney", LocalDate.of(1996, 07, 13), "Scotland"));
        league.getClubs().get(1).addPlayer(new Defender("Cedric", "Soares", LocalDate.of(1989, 8, 13), "Portugal"));
        league.getClubs().get(1).addPlayer(new Midfielder("Thomas", "Partey", LocalDate.of(1991, 9, 13), "Ghana"));
        league.getClubs().get(1).addPlayer(new Midfielder("Granit", "Xhaka", LocalDate.of(1995, 10, 13), "Switzerland"));
        league.getClubs().get(1).addPlayer(new Midfielder("Daniel", "Ceballos", LocalDate.of(1999, 11, 13), "Spain"));
        league.getClubs().get(1).addPlayer(new Midfielder("Emile", "Smith Rowe", LocalDate.of(1999, 12, 13), "England"));
        league.getClubs().get(1).addPlayer(new Attacker("Pierre Emerick", "Aubameyang", LocalDate.of(1996, 10, 13), "Gabon"));
        league.getClubs().get(1).addPlayer(new Attacker("Alexandre", "Lacazette", LocalDate.of(1986, 11, 13), "France"));
        league.getClubs().get(1).addPlayer(new Attacker("Nicolas", "Pepe", LocalDate.of(1998, 03, 13), "Ivory Coast"));
        league.getClubs().get(1).addPlayer(new Attacker("Bukayo", "Saka", LocalDate.of(1993, 01, 13), "England"));

 */
    }

    @FXML
    public void initialize() {
        ObservableList<Club> list = FXCollections.observableArrayList();
        for (int i=0; i<league.getClubs().size(); i++) {
            list.add(league.getClubs().get(i));
        }
        clubsLv.setItems(list);
        clubsLv.refresh();
    }

    public void addClubPressed (ActionEvent actionEvent) throws IOException {
        clubsLv.refresh();
        if (league.getClubs().size()==this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Dostigli ste limit");
            alert.setContentText("Ne možete dodavati nove timove u ligu");
            alert.showAndWait();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/club.fxml"));
            ClubController ctrl = new ClubController(this.league, null);
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
            ClubController ctrl = new ClubController(this.league, clubsLv.getSelectionModel().getSelectedItem());
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
                league.removeClub(temp);
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
        if (league.getClubs().size()!=this.numberOfClubs) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Liga nije popunjena");
            alert.setContentText("Ne možete pokrenuti novu sezonu dok se liga ne popuni");
            alert.showAndWait();
            start = false;
        }
        else {
            for (int i = 0; i < league.getNumberOfClubs(); i++) {
                Club temp = league.getClubs().get(i);
                if (temp.getPlayers().size() < 15) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Greška");
                    alert.setHeaderText("Nedovoljno igrača");
                    alert.setContentText("Klub " + league.getClubs().get(i) + " ima manje od 15 igrača.");
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
                    alert.setContentText("Klub " + league.getClubs().get(i) + " nema dovoljno igrača po pozicijama.");
                    alert.showAndWait();
                    start = false;
                    break;
                }
            }
        }
        if (start) {
            if (this.league.isScheduleRandom()) scheduleGenerator(this.league.getClubs());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/table.fxml"));
            TableController ctrl = new TableController(this.league);
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Table");
            stage.setScene(scene);
            stage.show();

            Stage stage2 = (Stage) startButton.getScene().getWindow();
            stage2.close();
        }
    }

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
            this.league.getFixtures().add(new Fixture(teams.get(teamIdx), clubs.get(0)));
            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                this.league.getFixtures().add(new Fixture(teams.get(firstTeam), teams.get(secondTeam)));
            }
        }
        for (int day = 0; day < numDays; day++) {
            int teamIdx = day % teamsSize;
            this.league.getFixtures().add(new Fixture(clubs.get(0), teams.get(teamIdx)));
            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                this.league.getFixtures().add(new Fixture(teams.get(secondTeam), teams.get(firstTeam)));
            }
        }
    }
}
