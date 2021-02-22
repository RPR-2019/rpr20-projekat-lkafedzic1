package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.Author;
import ba.unsa.etf.rpr.project.PublicationType;
import ba.unsa.etf.rpr.project.ScientificWork;
import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
        tableView.setPlaceholder(new Label("There is no results"));
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

    public void actionSearch(ActionEvent actionEvent) {
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
}
