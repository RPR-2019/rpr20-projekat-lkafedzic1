package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.FieldOfStudy;
import ba.unsa.etf.rpr.project.FieldOfStudyModel;
import ba.unsa.etf.rpr.project.ScientificWork;
import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GuestController {
    public Label lblWelcome;
    public ChoiceBox<String> choiceCategory;
    public TableView<ScientificWork> tableView;
    public TableColumn columnTitle;
    public TableColumn columnAuthor;
    public TableColumn columnYear;
    public TableColumn columnFieldOfStudy;
    public TableColumn columnType;
    public Label lblStatusBar;
    private ScientificWorkDAO instance = null;


    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();
        loadSearchChoices(choiceCategory);
        columnTitle.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("author"));
        columnYear.setCellValueFactory(new PropertyValueFactory<ScientificWork,Integer>("year"));
        columnFieldOfStudy.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("field"));
        columnType.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("type"));
        tableView.setItems(instance.getPopulationTableView(tableView));
    }

    private void loadSearchChoices(ChoiceBox<String> choiceCategory) {
        choiceCategory.getItems().add("Title");
        choiceCategory.getItems().add("Author");
        choiceCategory.getItems().add("Year");
        choiceCategory.getItems().add("Field of study");
        choiceCategory.getItems().add("Publication type");
        choiceCategory.getItems().add("Tags");
        choiceCategory.getSelectionModel().selectFirst();
    }

    public void actionLogin(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        lblWelcome.getScene().getWindow().hide();
        stage.show();
    }

    public void actionSearch(ActionEvent actionEvent) {
        lblStatusBar.setText("Searching finished");

    }

    public void actionClose(ActionEvent actionEvent) {
        Stage window = (Stage) lblWelcome.getScene().getWindow();
        window.close();
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        Parent root = loader.load();
        AboutController aboutWindow = loader.getController();
        stage.setTitle("About");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }
}
