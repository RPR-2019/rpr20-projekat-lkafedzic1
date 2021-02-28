package ba.unsa.etf.rpr.project.controllers;


import ba.unsa.etf.rpr.project.models.ScientificWork;
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
import java.util.Locale;
import java.util.ResourceBundle;

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

    public RadioMenuItem itemEnglish;
    public RadioMenuItem itemBosnian;

    private ScientificWorkDAO instance = null;
    private ObservableList<ScientificWork> scientificWorks = null;

    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();
        loadSearchChoices(choiceCategory);
        if (scientificWorks == null)
            scientificWorks = FXCollections.observableArrayList(instance.scientificWorks());
        tableView.setItems(scientificWorks);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnFieldOfStudy.setCellValueFactory(new PropertyValueFactory<>("field"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

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
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            if (fldSearch.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"))) {
                System.out.println(choiceCategory.getSelectionModel().getSelectedItem());
                ArrayList<ScientificWork> result = switch (choiceCategory.getSelectionModel().getSelectedItem()) {
                    case "Title","Naziv" -> instance.getWorksByTitle(fldSearch.getText());
                    case "Tags","Oznake" -> instance.getWorksByTag(fldSearch.getText());
                    case "Author","Autor" -> instance.getWorksByAuthor(fldSearch.getText());
                    default -> new ArrayList<>();
                };
                ObservableList<ScientificWork> list = FXCollections.observableArrayList();
            list.setAll(result);
            tableView.setItems(list);
            tableView.refresh();
        }
        else {
            lblStatusBar.setText(bundle.getString("notFilled"));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("Error"));
            alert.setHeaderText(bundle.getString("keywords"));
            alert.show();
        }
        lblStatusBar.setText(bundle.getString("searched"));
        });

        itemEnglish.selectedProperty().addListener((obs, o, n) -> {
            if(n){
                itemBosnian.setSelected(false);
                Locale.setDefault(new Locale("en", "US"));
                Scene scene = btnSearch.getScene();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/guest.fxml"), bundle);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.setRoot(root);
            }else if(o && !itemBosnian.isSelected()){
                itemEnglish.setSelected(true);
            }
        });

        itemBosnian.selectedProperty().addListener((obs, o, n) -> {
            if(n){
                itemEnglish.setSelected(false);
                Locale.setDefault(new Locale("bs", "BA"));
                Scene scene = btnSearch.getScene();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/guest.fxml"), bundle);
               Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.setRoot(root);
            }else if(o && !itemBosnian.isSelected()){
                itemBosnian.setSelected(true);
            }
        });
    }

    private void loadSearchChoices(ChoiceBox<String> choiceCategory) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        choiceCategory.getItems().add(bundle.getString("titleColumn"));
        choiceCategory.getItems().add(bundle.getString("tagsChoice"));
        choiceCategory.getItems().add(bundle.getString("itemAuthor"));
        choiceCategory.getSelectionModel().selectFirst();
    }

    public void actionLogin() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        lblWelcome.getScene().getWindow().hide();
        stage.show();
    }

    public void actionClose() {
        Stage window = (Stage) lblWelcome.getScene().getWindow();
        window.close();
    }

    public void actionAbout() throws IOException {
        Stage stage = new Stage();
        Parent root;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"), bundle);
        AboutController aboutController = new AboutController();
        loader.setController(aboutController);
        root = loader.load();

        stage.setTitle(bundle.getString("about"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionRefresh() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        lblStatusBar.setText(bundle.getString("refreshed"));
        tableView.refresh();
        fldSearch.setText("");
        choiceCategory.getSelectionModel().clearSelection();
        tableView.setItems(scientificWorks);
    }

    public void onActionHelp() throws IOException {
        Stage stage = new Stage();
        Parent root;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/help.fxml"),bundle);
        HelpController aboutController = new HelpController();
        loader.setController(aboutController);
        aboutController.txtAreaInstructions = new TextArea(bundle.getString("instructionsText"));
        root = loader.load();
        stage.setTitle(bundle.getString("about"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
    }
}
