package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.models.ScientificWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeAdminController extends HomeController {

    public void actionAddScientificWork(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scientific.fxml"), bundle);
        Parent root = loader.load();
        ScientificWorkController scientificWorkController = loader.getController();
        stage.setTitle("Add new scientific work");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMinWidth(470);
        stage.show();

        stage.setOnHiding(windowEvent -> {
            ScientificWork scientificWork = scientificWorkController.getScientificWork();
            if (scientificWork != null) {
                try {
                    //instance.addScientificWork(scientificWork);
                    scientificWorksList.setAll(instance.scientificWorks());
                    tableView.setItems(scientificWorksList);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    public void actionAddFieldOfStudy(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newFieldOfStudy.fxml"), bundle);
        Parent root = loader.load();
        FieldOfStudyController newWindow = loader.getController();
        stage.setTitle("Add new field of study");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddPublicationType(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newPublicationType.fxml"), bundle);
        Parent root = loader.load();
        PublicationTypeController newWindow = loader.getController();
        newWindow.lblStatusBar.setText("Add new type of publication");
        stage.setTitle("Add new type of publication");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddAuthor(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/author.fxml"), bundle);
        Parent root = loader.load();
        AuthorController newWindow = loader.getController();
        stage.setTitle("Add author");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionDeleteScientificWork(ActionEvent actionEvent) {
        checkSelection();

        ScientificWork scientificWork = tableView.getSelectionModel().getSelectedItem();
        if (scientificWork == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Deleting \"" + scientificWork.getTitle()+"\"");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteScientificWork(scientificWork);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

    public void actionDeleteFieldOfStudy(ActionEvent actionEvent) {
        checkSelection();
        String field = tableView.getSelectionModel().getSelectedItem().getField();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Are you sure you want to delete field " + field.toUpperCase() + "?");
        alert.setContentText("Deleting \"" + field + "\" will delete works in that field");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteField(field);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

    public void actionDeletePublicationType(ActionEvent actionEvent) {
        checkSelection();
        String type = tableView.getSelectionModel().getSelectedItem().getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Are you sure you want to delete field " + type.toUpperCase() + "?");
        alert.setContentText("Deleting \"" + type + "\" will delete works that are published in books");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteType(type);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

    public void actionDeleteAuthor(ActionEvent actionEvent) {
        checkSelection();
        String author = tableView.getSelectionModel().getSelectedItem().getAuthor();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Are you sure you want to delete author " + author.toUpperCase() + "?");
        alert.setContentText("This will delete " + author + " and all scientific works published by " + author);
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK){
            instance.deleteAuthor(author);
            scientificWorksList.setAll(instance.scientificWorks());
        }
    }

}
