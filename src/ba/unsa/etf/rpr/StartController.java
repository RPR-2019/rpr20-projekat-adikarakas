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

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class StartController {
    public Button cancelButton, createButton;
    public TextField nameField;
    public Spinner<Integer> spinnerField;
    public CheckBox scheduleCb;

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
            League league = new League(nameField.getText(), spinnerField.getValue(), scheduleCb.isSelected());

            // eksperimentalno
//            league.addClub(new Club ("Chelsea"));
  //          league.addClub(new Club ("Arsenal"));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/preseason.fxml"));
            PreseasonController ctrl = new PreseasonController(league, spinnerField.getValue());
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
