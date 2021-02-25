package ba.unsa.etf.rpr.project.controller;


import ba.unsa.etf.rpr.project.ScientificWork;
import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.collections.FXCollections;
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
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GuestController {
    public Label lblWelcome;
    public TableView<ScientificWork> tableView;
    public TableColumn<ScientificWork,String> columnTitle;
    public TableColumn<ScientificWork,String> columnAuthor;
    public TableColumn<ScientificWork,Integer> columnYear;
    public TableColumn<ScientificWork,String> columnFieldOfStudy;
    public TableColumn<ScientificWork,String> columnType;
    public Label lblStatusBar;
    public TextField fldSearch;
    public ChoiceBox<String> choiceCategory;
    public Button btnSearch;

    private ScientificWorkDAO instance = null;
    private ObservableList<ScientificWork> scientificWorks = null;

    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();
        loadSearchChoices(choiceCategory);
        if (scientificWorks == null)
            scientificWorks = FXCollections.observableArrayList(instance.scientificWorks());
        tableView.setItems(scientificWorks);
        columnTitle.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("author"));
        columnYear.setCellValueFactory(new PropertyValueFactory<ScientificWork,Integer>("year"));
        columnFieldOfStudy.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("field"));
        columnType.setCellValueFactory(new PropertyValueFactory<ScientificWork,String>("type"));

        fldSearch.getStyleClass().add("fieldNotValid");
        fldSearch.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldSearch.getText().trim().isEmpty() || fldSearch == null) {
                        fldSearch.getStyleClass().removeAll("fieldValid");
                        fldSearch.getStyleClass().add("fieldNotValid");
                    } else {
                        fldSearch.getStyleClass().removeAll("fieldNotValid");
                        fldSearch.getStyleClass().add("fieldValid");
                    }
                }
        );

        btnSearch.setOnAction(actionEvent -> {
            if (fldSearch.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"))) {
                ArrayList<ScientificWork> result = new ArrayList<>();
                if (choiceCategory.getSelectionModel().getSelectedItem().equals("Title")) {
                    result = instance.getWorksByTitle(fldSearch.getText());
                }
            else if (choiceCategory.getSelectionModel().getSelectedItem().equals("Tags")) {
                result = instance.getWorksByTag(fldSearch.getText());
            }
            else if (choiceCategory.getSelectionModel().getSelectedItem().equals("Author")) {
                result = instance.getWorksByAuthor(fldSearch.getText());
            }
            ObservableList<ScientificWork> list = FXCollections.observableArrayList();
            list.setAll(result);
            tableView.setItems(list);
            tableView.refresh();
        }
        else {
            lblStatusBar.setText("Please, fill the form properly");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter some keywords");
            alert.show();
        }
        lblStatusBar.setText("Searching finished");
        });
    }

    private void loadSearchChoices(ChoiceBox<String> choiceCategory) {
        choiceCategory.getItems().add("Title");
        choiceCategory.getItems().add("Tags");
        choiceCategory.getItems().add("Author");
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
