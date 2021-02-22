package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.Author;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.Year;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ScientificWorkController {

    public Button btnAdd, btnCancel;
    public TextField fldTitle;
    public Spinner<Integer> spinnerYear;
    public TextArea txtAreaTags;
    public ChoiceBox<String> choiceFieldOfStudy;
    public TextField fldPublishedIn;
    public Button btnUpload;
    public Label lblStatusBar;
    public ChoiceBox<String> choicePublicationType;
    public Label lblNothingChosen;
    public ChoiceBox<String> choiceAuthor;

    private ScientificWorkDAO instance = ScientificWorkDAO.getInstance();

    private String tags = null;
    private ScientificWork work = null;

    @FXML
    private void initialize() {
        instance = ScientificWorkDAO.getInstance();
        instance.loadChoices(choiceFieldOfStudy);
        instance.loadTypeChoices(choicePublicationType);
        instance.loadAuthorChoices(choiceAuthor);
        spinnerYear.getEditor().setText("1900");
        spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000,Year.now().getValue(),1900,1));
        //validation

    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }

    public void actionDownload(ActionEvent actionEvent) {
    }

    public void actionDelete(ActionEvent actionEvent) {
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

    public void actionAddScienceWork(ActionEvent actionEvent) {

    }

    public void actionUpload(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.txt")); //can add also xml and pdf extensions
        File chosenFile = fileChooser.showOpenDialog(new Stage());
        if (chosenFile == null) { //is any file selected
            lblNothingChosen.setVisible(true);
        }
        else {
            lblNothingChosen.setText("Uploaded");
            lblNothingChosen.setStyle("-fx-text-fill: green;");
            lblNothingChosen.setVisible(true);
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        Stage stage = (Stage) fldTitle.getScene().getWindow();
        stage.close();
    }

}
