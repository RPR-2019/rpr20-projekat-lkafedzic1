package ba.unsa.etf.rpr.project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeAdminController {
    public void actionSignOut(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
    }

    public void actionSearch(ActionEvent actionEvent) {
    }

    public void menuBtnAddClick(ActionEvent actionEvent) {
    }

    public void actionAddScientificWork(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scientific.fxml"));
        Parent root = loader.load();
        ScientificWorkController newWindow = loader.getController();
        stage.setTitle("Add new scientific work");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.show();
    }

    public void actionAddFieldOfStudy(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/new.fxml"));
        Parent root = loader.load();
        NewChoiceController newWindow = loader.getController();
        stage.setTitle("Add new field of study");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddPublicationType(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/new.fxml"));
        Parent root = loader.load();
        NewChoiceController newWindow = loader.getController();
        newWindow.lblStatusBar.setText("Add new type of publication");
        stage.setTitle("Add new type of publication");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionDeleteScientificWork(ActionEvent actionEvent) {
    }

    public void actionDeleteFieldOfStudy(ActionEvent actionEvent) {
    }

    public void actionDeletePublicationType(ActionEvent actionEvent) {
    }

    public void actionDownload(ActionEvent actionEvent) {
    }

    public void actionRead(ActionEvent actionEvent) {
    }
}
