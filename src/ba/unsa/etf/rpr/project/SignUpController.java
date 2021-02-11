package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SignUpController {

    public Label lblFirstName;

    public void actionOk(ActionEvent actionEvent) {
        //sačuvati
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) lblFirstName.getScene().getWindow();
        stage.close();
    }
}
