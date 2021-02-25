package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.ScientificWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeAdminController extends HomeController {

    public void actionAddScientificWork(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scientific.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newFieldOfStudy.fxml"));
        Parent root = loader.load();
        FieldOfStudyController newWindow = loader.getController();
        stage.setTitle("Add new field of study");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void actionAddPublicationType(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newPublicationType.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/author.fxml"));
        Parent root = loader.load();
        AuthorController newWindow = loader.getController();
        stage.setTitle("Add author");
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

    public void actionDeleteAuthor(ActionEvent actionEvent) {
    }

}
