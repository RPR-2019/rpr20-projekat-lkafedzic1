package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ScientificWorkController {

    public Button btnAdd, btnCancel;
    public Label lblTitle;
    public TextField fldAuthor;
    public TextField fldTitle;

    @FXML
    private void initialize() {

    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) fldAuthor.getScene().getWindow();
        stage.close();
    }

    public void actionDownload(ActionEvent actionEvent) {
    }

    public void actionDelete(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        Parent root = loader.load();
        HomeController aboutWindow = loader.getController();
        stage.setTitle("About");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddScienceWork(ActionEvent actionEvent) {

    }

    public void actionUpload(ActionEvent actionEvent) {
    }

    public void actionClose(ActionEvent actionEvent) {
        Stage stage = (Stage) lblTitle.getScene().getWindow();
        stage.close();
    }
}
