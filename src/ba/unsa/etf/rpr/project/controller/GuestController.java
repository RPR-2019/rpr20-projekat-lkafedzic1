package ba.unsa.etf.rpr.project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GuestController {
    public Label lblWelcome;
    public TextField fldSearch;
    public ChoiceBox choiceFields;

    public void actionLogin(ActionEvent actionEvent) throws IOException {
        /*prikazuje se početna strana*/
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMinHeight(200);
        stage.setMinWidth(320);
        Stage window = (Stage) lblWelcome.getScene().getWindow();
        window.close();
        stage.setResizable(false);
        stage.show();
    }

    public void actionSearch(ActionEvent actionEvent) {
    }
}
