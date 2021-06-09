package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.*;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerController {
    public Button saveButton;
    public Button cancelPlayerButton;
    public DatePicker dateIdentity;
    private final List<String> countries = new ArrayList<>(Arrays.asList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos Islands", "Colombia", "Comoros", "Congo", "DR Congo", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Vatican", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "North Korea", "South Korea", "Kuwait", "Kyrgyzstan", "Lao", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "North Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "England", "Wales", "Scotland", "Ireland", "Northern Ireland", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Serbia", "Zambia", "Zimbabwe", "Palestine", "Montenegro", "Kosovo"));
    public ChoiceBox<String> choiceNationality;
    public ChoiceBox<String> choicePosition;
    private final Player currentPlayer;
    public TextField firstNameField;
    public TextField surnameField;
    private final Club currentClub;
    private LeagueDAO dao;

    PlayerController (Club club, Player player) {
        dao=LeagueDAO.getInstance();
        this.currentPlayer=player;
        this.currentClub=club;
    }

    @FXML
    public void initialize() {
        ObservableList<String> nations = FXCollections.observableArrayList();
        for (String country : countries) {
            nations.add(country);
        }
        nations.sort((o1, o2) -> o1.compareTo(o2));
        choiceNationality.setItems(nations);
        ObservableList<String> positions = FXCollections.observableArrayList();
        positions.add("Goalkeeper");
        positions.add("Defender");
        positions.add("Midfielder");
        positions.add("Attacker");
        choicePosition.setItems(positions);
    }

    public void savePressed() {
        Boolean irregular = false;

        if (firstNameField.getText().isBlank() && surnameField.getText().isBlank()) irregular=true;
        if (choicePosition.getValue()==null || choiceNationality.getValue()==null) irregular=true;
        if (dateIdentity.getValue()==null || dateIdentity.getValue().isAfter(LocalDate.now())) irregular=true;

        if (irregular) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect data");
            alert.setContentText("You haven't filled all fields");
            alert.showAndWait();
        }
        else {
            if (("Goalkeeper").equals(choicePosition.getValue())) {
                Goalkeeper player = new Goalkeeper(firstNameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                this.currentClub.getPlayers().add(player);
                player.setClub(this.currentClub);
                player.setPosition(player.getClass().getSimpleName());
                dao.addPlayer(player);
                FxRobot robot = new FxRobot();
                ListView<Player> playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                playersLv.getItems().add(player);
            }
            else if (("Defender").equals(choicePosition.getValue())) {
                Defender player = new Defender(firstNameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                this.currentClub.getPlayers().add(player);
                player.setClub(this.currentClub);
                player.setPosition(player.getClass().getSimpleName());
                dao.addPlayer(player);
                FxRobot robot = new FxRobot();
                ListView<Player> playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                playersLv.getItems().add(player);
            }
            else if (("Midfielder").equals(choicePosition.getValue())) {
                Midfielder player = new Midfielder(firstNameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                this.currentClub.getPlayers().add(player);
                player.setClub(this.currentClub);
                player.setPosition(player.getClass().getSimpleName());
                dao.addPlayer(player);
                FxRobot robot = new FxRobot();
                ListView<Player> playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                playersLv.getItems().add(player);
            }
            else {
                Attacker player = new Attacker(firstNameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                this.currentClub.getPlayers().add(player);
                player.setClub(this.currentClub);
                player.setPosition(player.getClass().getSimpleName());
                dao.addPlayer(player);
                FxRobot robot = new FxRobot();
                ListView<Player> playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                playersLv.getItems().add(player);
            }
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        }
    }

    public void cancelPressed() {
        Stage stage = (Stage) cancelPlayerButton.getScene().getWindow();
        stage.close();
    }

}
