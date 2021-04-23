package ba.unsa.etf.rpr;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerController {

    public Button saveButton;
    public Button cancelButton;
    public DatePicker dateIdentity;
    private ArrayList<String> countries = new ArrayList<String>(Arrays.asList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos Islands", "Colombia", "Comoros", "Congo", "DR Congo", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Vatican", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "North Korea", "South Korea", "Kuwait", "Kyrgyzstan", "Lao", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "North Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Serbia", "Zambia", "Zimbabwe", "Palestine", "Montenegro", "Kosovo"));
    public ChoiceBox<String> choiceNationality;
    public ChoiceBox<String> choicePosition;
    private Player currentPlayer;
    public TextField nameField, surnameField;
    private Club currentClub;

    PlayerController (Club club, Player player) {
        this.currentPlayer=player;
        this.currentClub=club;
    }

    @FXML
    public void initialize() {
        ObservableList<String> nations = FXCollections.observableArrayList();
        for (int i=0; i<countries.size(); i++) {
            nations.add(countries.get(i));
        }
        choiceNationality.setItems(nations);
        ObservableList<String> positions = FXCollections.observableArrayList();
        positions.add("Goalkeeper");
        positions.add("Defender");
        positions.add("Midfielder");
        positions.add("Attacker");
        if (this.currentPlayer!=null) {
            nameField.setText(currentPlayer.getName());
            surnameField.setText(currentPlayer.getSurname());
            choiceNationality.setValue(currentPlayer.getNationality());
            dateIdentity.setValue(currentPlayer.getBirth());
            if (this.currentPlayer instanceof Goalkeeper) choicePosition.setValue("Goalkeeper");
            else if (this.currentPlayer instanceof Defender) choicePosition.setValue("Defender");
            else if (this.currentPlayer instanceof Midfielder) choicePosition.setValue("Midfielder");
            else choicePosition.setValue("Attacker");
        }
        else choicePosition.setItems(positions);
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
              if (t1.length()>0) {
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
        surnameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
             if (t1.length()>0) {
                 surnameField.getStyleClass().removeAll("popunjenoKakoNeTreba");
                 surnameField.getStyleClass().add("popunjenoKakoTreba");
             }
             else {
                 surnameField.getStyleClass().remove("popunjenoKakoTreba");
                 surnameField.getStyleClass().add("popunjenoKakoNeTreba");
             }
            }
            }
        );
    }

    public void savePressed(ActionEvent actionEvent) {
        Boolean irregular = false;

        if (nameField.getText().length()==0 || surnameField.getText().length()==0) irregular=true;
        if (choicePosition.getValue()==null || choiceNationality.getValue()==null) irregular=true;
        // dodati još uslov za datum

        if (irregular==true) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText("Neispravni podaci");
            alert.setContentText("Niste unijeli sve podatke za igrača");
            alert.showAndWait();
        }

        else {

            if (this.currentPlayer != null) {
                currentPlayer.setName(nameField.getText());
                currentPlayer.setSurname(surnameField.getText());
                currentPlayer.setNationality(choiceNationality.getValue());
                currentPlayer.setBirth(dateIdentity.getValue());
            }
            else {
                if (choicePosition.getValue().equals("Goalkeeper")) {
                    Goalkeeper player = new Goalkeeper(nameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                    this.currentClub.getPlayers().add(player);
                    FxRobot robot = new FxRobot();
                    ListView playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                    playersLv.getItems().add(player);
                }
                else if (choicePosition.getValue().equals("Defender")) {
                    Defender player = new Defender(nameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                    this.currentClub.getPlayers().add(player);
                    FxRobot robot = new FxRobot();
                    ListView playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                    playersLv.getItems().add(player);
                }
                else if (choicePosition.getValue().equals("Midfielder")) {
                    Midfielder player = new Midfielder(nameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                    this.currentClub.getPlayers().add(player);
                    FxRobot robot = new FxRobot();
                    ListView playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                    playersLv.getItems().add(player);
                }
                else {
                    Attacker player = new Attacker(nameField.getText(), surnameField.getText(), dateIdentity.getValue(), choiceNationality.getValue());
                    this.currentClub.getPlayers().add(player);
                    FxRobot robot = new FxRobot();
                    ListView playersLv = robot.lookup("#playersLv").queryAs(ListView.class);
                    playersLv.getItems().add(player);
                }
                // dodati nekako i u bazu
            }
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        }
    }

    public void cancelPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
