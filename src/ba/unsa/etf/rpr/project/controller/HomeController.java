package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeController {
    public Label lblStatusBar;
    public MenuButton menuBtnAdd;
    public MenuButton menuBtnDelete;


    private ScientificWorkDAO instance;

    public HomeController() {
    }

    @FXML
    public void initialize() {
    }

    public void actionSearch(ActionEvent actionEvent) {
    }

    public void actionSignOut(ActionEvent actionEvent) throws IOException {
        lblStatusBar.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
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

    public void actionDownload(ActionEvent actionEvent) {
    }

    public void actionRead(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"));
        Parent root = loader.load();
        ScientificWorkController newWindow = loader.getController();
        stage.setTitle(String.valueOf(newWindow.fldTitle));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.show();
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
/*        stage.setMinHeight(200);
        stage.setMinWidth(300);*/
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
/*        stage.setMinHeight(200);
        stage.setMinWidth(350);*/
        stage.setResizable(false);
        stage.show();
    }

    public void actionDeleteScientificWork(ActionEvent actionEvent) {
    }

    public void actionDeleteFieldOfStudy(ActionEvent actionEvent) {
    }

    public void actionDeletePublicationType(ActionEvent actionEvent) {
    }
}
