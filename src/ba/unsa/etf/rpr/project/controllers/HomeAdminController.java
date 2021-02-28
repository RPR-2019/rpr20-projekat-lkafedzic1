package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import ba.unsa.etf.rpr.project.models.ScientificWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeAdminController extends HomeController {

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeAdministrator.fxml"), bundle);
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeAdministrator.fxml"), bundle);
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
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        //Search: tag, admin, author - clients wishes
        choiceCategory.getItems().add(bundle.getString("titleColumn"));
        choiceCategory.getItems().add(bundle.getString("tagsChoice"));
        choiceCategory.getItems().add(bundle.getString("itemAuthor"));
        choiceCategory.getSelectionModel().selectFirst();
    }

    public void actionAddScientificWork() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scientific.fxml"), bundle);
        Parent root = loader.load();
        ScientificWorkController scientificWorkController = loader.getController();
        stage.setTitle(bundle.getString("addWorkTitle"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMinWidth(470);
        stage.show();
        stage.setOnHiding(windowEvent -> {
            ScientificWork scientificWork = scientificWorkController.getScientificWork();
            if (scientificWork != null) {
                try {
                    scientificWorksList.setAll(instance.scientificWorks());
                    tableView.setItems(scientificWorksList);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    public void actionAddFieldOfStudy() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newFieldOfStudy.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("addFieldTitle"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddPublicationType() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newPublicationType.fxml"), bundle);
        Parent root = loader.load();
        PublicationTypeController newWindow = loader.getController();
        newWindow.lblStatusBar.setText(bundle.getString("addType"));
        stage.setTitle(bundle.getString("addType"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddAuthor() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/author.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("addAuthor"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionDeleteScientificWork() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        checkSelection();
        ScientificWork scientificWork = tableView.getSelectionModel().getSelectedItem();
        if (scientificWork == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("confirm"));
        alert.setHeaderText(bundle.getString("sure"));
        alert.setContentText(bundle.getString("deleting") + " " + scientificWork.getTitle()+"\"");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteScientificWork(scientificWork);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

    public void actionDeleteFieldOfStudy() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        checkSelection();
        String field = tableView.getSelectionModel().getSelectedItem().getField();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("confirm"));
        alert.setHeaderText(bundle.getString("deletingField") + " " + field.toUpperCase() + "?");
        alert.setContentText(bundle.getString("deletingQuote") + " " + field + " " + bundle.getString("willDelete"));
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteField(field);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

    public void actionDeletePublicationType() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        checkSelection();
        String type = tableView.getSelectionModel().getSelectedItem().getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("confirm"));
        alert.setHeaderText(bundle.getString("deletingType") +  " " + type.toUpperCase() + "?");
        alert.setContentText(bundle.getString("deletingQuote") +  " " + type + " " + bundle.getString("willDeleteType"));
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteType(type);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

    public void actionDeleteAuthor() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        checkSelection();
        String author = tableView.getSelectionModel().getSelectedItem().getAuthor();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("confirm"));
        alert.setHeaderText(bundle.getString("deletingAuthor") + " " + author.toUpperCase() + "?");
        alert.setContentText(("deletingQuote") + " " + author + " " + bundle.getString("andAll") + " " + author);
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteAuthor(author);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

}
