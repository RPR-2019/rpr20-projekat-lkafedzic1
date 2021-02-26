package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import ba.unsa.etf.rpr.project.exceptions.IllegalDeletionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeController {

    public Label lblStatusBar;
    public TextField fldSearch;
    public Button btnSearch;
    public ChoiceBox<String> choiceCategory;
    public TableView<ScientificWork> tableView;
    public TableColumn<ScientificWork,String> columnTitle;
    public TableColumn<ScientificWork,String> columnAuthor;
    public TableColumn<ScientificWork,Integer> columnYear;
    public TableColumn<ScientificWork,String> columnFieldOfStudy;
    public TableColumn<ScientificWork,String> columnType;
    protected ScientificWorkDAO instance = null;

    protected ObservableList<ScientificWork> scientificWorksList = null;

    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();
        loadSearchChoices(choiceCategory);
        if (scientificWorksList == null)
            scientificWorksList = FXCollections.observableArrayList(instance.scientificWorks());
        tableView.setItems(scientificWorksList);
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

    void loadSearchChoices(ChoiceBox<String> choiceCategory) {
        //Search: tag, admin, author - clients wishes
        choiceCategory.getItems().add("Title");
        choiceCategory.getItems().add("Tags");
        choiceCategory.getItems().add("Author");
        choiceCategory.getSelectionModel().selectFirst();
    }

    public void actionSignOut(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage st = (Stage) node.getScene().getWindow();
        st.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

public void actionRead(ActionEvent actionEvent) throws IOException {
    checkSelection();
    ScientificWork scientificWork = tableView.getSelectionModel().getSelectedItem();

    Stage stage = new Stage();
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"));
    DocumentController documentController = new DocumentController(scientificWork);
    loader.setController(documentController);
    root = loader.load();

    stage.setTitle("Scientific work " + "\"" + scientificWork.getTitle() + "\"");
    stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
    stage.show();
}


    protected void checkSelection() {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            try {
                throw new IllegalDeletionException("Can not operate if there is no row selected");
            } catch (IllegalDeletionException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Nothing selected" );
                alert.setContentText("Please, select row from table");
                alert.showAndWait();
            }
        }
    }

    public void actionDownload(ActionEvent actionEvent) {

    }

    public void actionAbout(ActionEvent actionEvent)  throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        Parent root = loader.load();
        AboutController aboutWindow = loader.getController();
        stage.setTitle("About");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionRefresh(ActionEvent actionEvent) {
        lblStatusBar.setText("Refreshed");
        tableView.refresh();
        fldSearch.setText("");
        choiceCategory.getSelectionModel().clearSelection();
        tableView.setItems(scientificWorksList);
    }
}
