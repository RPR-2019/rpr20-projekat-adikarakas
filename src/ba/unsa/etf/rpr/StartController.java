package ba.unsa.etf.rpr;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class StartController {
    public Button cancelButton, createButton;
    public TextField nameField;
    public Spinner<Integer> spinnerField;
    public CheckBox scheduleCb;
    private LeagueDAO dao;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 30);
        valueFactory.setValue(2);
        spinnerField.setValueFactory(valueFactory);
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.trim().length()>0) {
                    nameField.getStyleClass().removeAll("popunjenoKakoNeTreba");
                    nameField.getStyleClass().add("popunjenoKakoTreba");
                }
                else {
                    nameField.getStyleClass().remove("popunjenoKakoTreba");
                    nameField.getStyleClass().add("popunjenoKakoNeTreba");
                }
            }
        }
        );
    }

    public void createPressed(ActionEvent actionEvent) throws IOException {
        if (spinnerField.getValue()%2!=0)  {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Pogrešni podaci");
            alert.setContentText("Broj timova mora biti paran");
            alert.showAndWait();
        }
        else if (nameField.getText().trim().length()==0) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Pogrešni podaci");
            alert.setContentText("Morate unijeti neko ime");
            alert.showAndWait();
        }
        else {
            dao.getInstance().setName(nameField.getText());
            dao.getInstance().setNumberOfClubs(spinnerField.getValue());
            dao.getInstance().setScheduleRandom(scheduleCb.isSelected());
            dao.getInstance().setCreated(true);

            // eksperimentalno
            /*
            Club chelsea = new Club(1, "Chelsea");
            chelsea.addPlayer(new Goalkeeper("Edouard", "Mendy", LocalDate.of(1992, 03, 13), "Senegal"));
            chelsea.addPlayer(new Goalkeeper("Kepa", "Arizzabalaga",LocalDate.of(1994, 02, 13), "Spain"));
            chelsea.addPlayer(new Defender("Reece", "James", LocalDate.of(1999, 04, 13), "England"));
            chelsea.addPlayer(new Defender("Thiago", "Silva", LocalDate.of(1984, 05, 13), "Brazil"));
            chelsea.addPlayer(new Defender("Kurt", "Zouma", LocalDate.of(1995, 06, 13), "France"));
            chelsea.addPlayer(new Defender("Ben", "Chilwell", LocalDate.of(1996, 07, 13), "England"));
            chelsea.addPlayer(new Defender("Cesar", "Azpilicueta", LocalDate.of(1989, 8, 13), "Spain"));
            chelsea.addPlayer(new Midfielder("Ngolo", "Kante", LocalDate.of(1991, 9, 13), "France"));
            chelsea.addPlayer(new Midfielder("Mateo", "Kovačić", LocalDate.of(1995, 10, 13), "Croatia"));
            chelsea.addPlayer(new Midfielder("Mason", "Mount", LocalDate.of(1999, 11, 13), "England"));
            chelsea.addPlayer(new Midfielder("Kai", "Havertz", LocalDate.of(1999, 12, 13), "Germany"));
            chelsea.addPlayer(new Attacker("Timo", "Werner", LocalDate.of(1996, 10, 13), "Germany"));
            chelsea.addPlayer(new Attacker("Olivier", "Giroud", LocalDate.of(1986, 11, 13), "France"));
            chelsea.addPlayer(new Attacker("Christian", "Pulišić", LocalDate.of(1998, 03, 13), "USA"));
            chelsea.addPlayer(new Attacker("Hakim", "Ziyech", LocalDate.of(1993, 01, 13), "Morocco"));


            Club arsenal = new Club(2, "Arsenal");
            arsenal.addPlayer(new Goalkeeper("Bernd", "Leno", LocalDate.of(1992, 03, 13), "Germany"));
            arsenal.addPlayer(new Goalkeeper("Mat", "Ryan",LocalDate.of(1994, 02, 13), "Australia"));
            arsenal.addPlayer(new Defender("Hector", "Bellerin", LocalDate.of(1999, 04, 13), "Spain"));
            arsenal.addPlayer(new Defender("David", "Luiz", LocalDate.of(1984, 05, 13), "Brazil"));
            arsenal.addPlayer(new Defender("Rob", "Holding", LocalDate.of(1995, 06, 13), "England"));
            arsenal.addPlayer(new Defender("Kieran", "Tierney", LocalDate.of(1996, 07, 13), "Scotland"));
            arsenal.addPlayer(new Defender("Cedric", "Soares", LocalDate.of(1989, 8, 13), "Portugal"));
            arsenal.addPlayer(new Midfielder("Thomas", "Partey", LocalDate.of(1991, 9, 13), "Ghana"));
            arsenal.addPlayer(new Midfielder("Granit", "Xhaka", LocalDate.of(1995, 10, 13), "Switzerland"));
            arsenal.addPlayer(new Midfielder("Daniel", "Ceballos", LocalDate.of(1999, 11, 13), "Spain"));
            arsenal.addPlayer(new Midfielder("Emile", "Smith Rowe", LocalDate.of(1999, 12, 13), "England"));
            arsenal.addPlayer(new Attacker("Pierre Emerick", "Aubameyang", LocalDate.of(1996, 10, 13), "Gabon"));
            arsenal.addPlayer(new Attacker("Alexandre", "Lacazette", LocalDate.of(1986, 11, 13), "France"));
            arsenal.addPlayer(new Attacker("Nicolas", "Pepe", LocalDate.of(1998, 03, 13), "Ivory Coast"));
            arsenal.addPlayer(new Attacker("Bukayo", "Saka", LocalDate.of(1993, 01, 13), "England"));

            Club liverpool = new Club(3, "Liverpool");
            liverpool.addPlayer(new Goalkeeper("Alisson", "Becker", LocalDate.of(1992, 03, 13), "Germany"));
            liverpool.addPlayer(new Goalkeeper("Caoimhin", "Kelleher",LocalDate.of(1994, 02, 13), "Australia"));
            liverpool.addPlayer(new Defender("Trent", "Alexander-Arnold", LocalDate.of(1999, 04, 13), "Spain"));
            liverpool.addPlayer(new Defender("Joe", "Gomez", LocalDate.of(1984, 05, 13), "Brazil"));
            liverpool.addPlayer(new Defender("Virgil", "Van Dijk", LocalDate.of(1995, 06, 13), "England"));
            liverpool.addPlayer(new Defender("Joel", "Matip", LocalDate.of(1996, 07, 13), "Scotland"));
            liverpool.addPlayer(new Defender("Andrew", "Robertson", LocalDate.of(1989, 8, 13), "Portugal"));
            liverpool.addPlayer(new Midfielder("Jordan", "Henderson", LocalDate.of(1991, 9, 13), "Ghana"));
            liverpool.addPlayer(new Midfielder("", "Fabinho", LocalDate.of(1995, 10, 13), "Switzerland"));
            liverpool.addPlayer(new Midfielder("Georginio", "Wijnaldum", LocalDate.of(1999, 11, 13), "Spain"));
            liverpool.addPlayer(new Midfielder("Thiago", "Alcantara", LocalDate.of(1999, 12, 13), "England"));
            liverpool.addPlayer(new Attacker("Mohamed", "Salah", LocalDate.of(1996, 10, 13), "Gabon"));
            liverpool.addPlayer(new Attacker("Sadio", "Mane", LocalDate.of(1986, 11, 13), "France"));
            liverpool.addPlayer(new Attacker("Roberto", "Firmino", LocalDate.of(1998, 03, 13), "Ivory Coast"));
            liverpool.addPlayer(new Attacker("Diogo", "Jota", LocalDate.of(1993, 01, 13), "England"));

            Club everton = new Club(4, "Everton");
            everton.addPlayer(new Goalkeeper("Jordan", "Pickford", LocalDate.of(1992, 03, 13), "Germany"));
            everton.addPlayer(new Goalkeeper("Robin", "Olsen",LocalDate.of(1994, 02, 13), "Australia"));
            everton.addPlayer(new Defender("Seamus", "Coleman", LocalDate.of(1999, 04, 13), "Spain"));
            everton.addPlayer(new Defender("Mason", "Holgate", LocalDate.of(1984, 05, 13), "Brazil"));
            everton.addPlayer(new Defender("Yerry", "Mina", LocalDate.of(1995, 06, 13), "England"));
            everton.addPlayer(new Defender("Michael", "Keane", LocalDate.of(1996, 07, 13), "Scotland"));
            everton.addPlayer(new Defender("Lucas", "Digne", LocalDate.of(1989, 8, 13), "Portugal"));
            everton.addPlayer(new Midfielder("", "Allan", LocalDate.of(1991, 9, 13), "Ghana"));
            everton.addPlayer(new Midfielder("Abdoulaye", "Doucoure", LocalDate.of(1995, 10, 13), "Switzerland"));
            everton.addPlayer(new Midfielder("Gylfi", "Sigurdsson", LocalDate.of(1999, 11, 13), "Spain"));
            everton.addPlayer(new Midfielder("James", "Rodriguez", LocalDate.of(1999, 12, 13), "England"));
            everton.addPlayer(new Attacker("Dominic", "Calvert-Lewin", LocalDate.of(1996, 10, 13), "Gabon"));
            everton.addPlayer(new Attacker("", "Richarlison", LocalDate.of(1986, 11, 13), "France"));
            everton.addPlayer(new Attacker("Alex", "Iwobi", LocalDate.of(1998, 03, 13), "Ivory Coast"));
            everton.addPlayer(new Attacker("Josh", "King", LocalDate.of(1993, 01, 13), "England"));

            league.addClub(chelsea);
            league.addClub(arsenal);
            league.addClub(liverpool);
            league.addClub(everton); */

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"));
            PreseasonController ctrl = new PreseasonController(spinnerField.getValue());
            fxmlLoader.setController(ctrl);
            Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            Stage stage = new Stage();
            stage.setMinHeight(150);
            stage.setMinWidth(400);
            stage.setTitle("Preseason");
            stage.setScene(scene);
            stage.show();

            Stage stage2 = (Stage) createButton.getScene().getWindow();
            stage2.close();
        }
    }

    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
