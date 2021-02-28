package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import ba.unsa.etf.rpr.project.exceptions.IllegalDeletionException;
import ba.unsa.etf.rpr.project.models.ScientificWork;
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
import java.util.Objects;
import java.util.ResourceBundle;

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

    public RadioMenuItem itemEnglish;
    public RadioMenuItem itemBosnian;

    protected ObservableList<ScientificWork> scientificWorksList = null;

    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();
        loadSearchChoices(choiceCategory);
        if (scientificWorksList == null)
            scientificWorksList = FXCollections.observableArrayList(instance.scientificWorks());
        tableView.setItems(scientificWorksList);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnFieldOfStudy.setCellValueFactory(new PropertyValueFactory<>("field"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

        fldSearch.getStyleClass().add("fieldNotValid");
        fldSearch.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldSearch.getText().trim().isEmpty() || fldSearch == null) {
                        Objects.requireNonNull(fldSearch).getStyleClass().removeAll("fieldValid");
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
                alert.setHeaderText(bundle.getString("notFilled"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
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

    void loadSearchChoices(ChoiceBox<String> choiceCategory) {
        //Search: tag, admin, author - clients wishes
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        choiceCategory.getItems().add(bundle.getString("titleColumn"));
        choiceCategory.getItems().add(bundle.getString("tagsChoice"));
        choiceCategory.getItems().add(bundle.getString("itemAuthor"));
        choiceCategory.getSelectionModel().selectFirst();
    }

    public void actionSignOut(ActionEvent actionEvent) throws IOException {
        Stage st = (Stage) lblStatusBar.getScene().getWindow();
        st.hide();
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle(bundle.getString("login"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

public void actionRead(ActionEvent actionEvent) throws IOException {
    checkSelection();
    ScientificWork scientificWork = tableView.getSelectionModel().getSelectedItem();

    Stage stage = new Stage();
    Parent root;
    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"), bundle);
    DocumentController documentController = new DocumentController(scientificWork);
    loader.setController(documentController);
    root = loader.load();

    stage.setTitle(bundle.getString("work") + "\"" + scientificWork.getTitle() + "\"");
    stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
    stage.show();
}


    protected void checkSelection() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            try {
                throw new IllegalDeletionException("Can not operate if there is no row selected");
            } catch (IllegalDeletionException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("Error"));
                alert.setHeaderText(bundle.getString("nothingSelected") );
                alert.setContentText(bundle.getString("select"));
                alert.showAndWait();
            }
        }
    }

    public void actionAbout(ActionEvent actionEvent)  throws IOException {
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

    public void actionRefresh(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        lblStatusBar.setText(bundle.getString("refreshed"));
        tableView.refresh();
        fldSearch.setText("");
        choiceCategory.getSelectionModel().clearSelection();
        tableView.setItems(scientificWorksList);
    }

    public void onActionHelp(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/help.fxml"), bundle);
        HelpController aboutController = new HelpController();
        loader.setController(aboutController);
        root = loader.load();

        stage.setTitle(bundle.getString("help"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
    }
}
