package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import ba.unsa.etf.rpr.project.exceptions.IllegalChoiceException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ScientificWorkController implements Validation {

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
    public CheckBox checkBoxAdditional;

    private ScientificWorkDAO instance = null;

    private ScientificWork scientificWork;
    private File chosenFile = null;
    private ObservableList<ScientificWork> scientificWorksList;


    public ScientificWork getScientificWork() {
        return scientificWork;
    }

    public void setScientificWork(ScientificWork scientificWork) {
        this.scientificWork = scientificWork;
    }

    @FXML
    private void initialize() {
        instance = ScientificWorkDAO.getInstance();
        instance.loadChoices(choiceFieldOfStudy);
        instance.loadTypeChoices(choicePublicationType);
        instance.loadAuthorChoices(choiceAuthor);
        spinnerYear.getEditor().setText("1900");
        spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000,Year.now().getValue(),1900,1));
        //validation
        fldTitle.getStyleClass().add("fieldNotValid");
        fldTitle.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldTitle.getText().trim().isEmpty() || !isValidTitle(fldTitle.getText())) {
                        fldTitle.getStyleClass().removeAll("fieldValid");
                        fldTitle.getStyleClass().add("fieldNotValid");
                    } else {
                        fldTitle.getStyleClass().removeAll("fieldNotValid");
                        fldTitle.getStyleClass().add("fieldValid");
                    }
                }
        );
        txtAreaTags.setText("tag1, tag2, tag3...");
        fldPublishedIn.setText("Journal or Conference");
        fldPublishedIn.getStyleClass().add("fieldNotValid");
        fldPublishedIn.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (checkBoxAdditional.isSelected() && (fldPublishedIn.getText().trim().isEmpty() || !(isValidTitle(fldPublishedIn.getText()) || fldPublishedIn.getText().equals("")) || fldPublishedIn.getText().equals("Journal or Conference"))) {
                        fldPublishedIn.getStyleClass().removeAll("fieldValid");
                        fldPublishedIn.getStyleClass().add("fieldNotValid");
                    } else {
                        fldPublishedIn.getStyleClass().removeAll("fieldNotValid");
                        fldPublishedIn.getStyleClass().add("fieldValid");
                    }
                }
        );
        spinnerYear.getStyleClass().add("fieldNotValid");
        spinnerYear.getEditor().textProperty().addListener(
                (observableValue, o, n) -> {
                    if (spinnerYear.getValue() > LocalDate.now().getYear()) throw new IllegalChoiceException("Date can't be in the future");
                    else if (spinnerYear.getEditor().getText().trim().isEmpty() || spinnerYear.getValue() > LocalDate.now().getYear()) {
                        spinnerYear.getEditor().getStyleClass().removeAll("fieldValid");
                        spinnerYear.getEditor().getStyleClass().add("fieldNotValid");
                    } else {
                        spinnerYear.getEditor().getStyleClass().removeAll("fieldNotValid");
                        spinnerYear.getEditor().getStyleClass().add("fieldValid");
                    }
                }
        );
        txtAreaTags.getStyleClass().add("fieldNotValid");
        txtAreaTags.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (txtAreaTags.getText().trim().isEmpty()) {
                        txtAreaTags.getStyleClass().removeAll("fieldValid");
                        txtAreaTags.getStyleClass().add("fieldNotValid");
                    } else {
                        txtAreaTags.getStyleClass().removeAll("fieldNotValid");
                        txtAreaTags.getStyleClass().add("fieldValid");
                    }
                }
        );
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

    public void actionAddScientificWork(ActionEvent actionEvent) throws IOException {
        if (choiceAuthor.getSelectionModel() == null || choiceFieldOfStudy.getSelectionModel() == null || choicePublicationType.getSelectionModel() == null ) {
            throw new IllegalChoiceException("Nothing selected");
        }
        if (isInputValid(fldTitle) && isAdditionalInfoValid() && isInputValid(spinnerYear.getEditor()) && (txtAreaTags.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid")))) {
            //String[] individualTags = txtAreaTags.getText().split(",");
            String title = fldTitle.getText();
            String author = choiceAuthor.getValue();
            if (instance.isDupe(title,author)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Scientific work already exists");
                alert.setContentText(title + " by author " + author + " is already added");
                lblStatusBar.setText(title + " already exists");

                alert.showAndWait();
            }
            else {
                if (scientificWork == null) scientificWork = new ScientificWork();
                scientificWork.setTitle(title);
                scientificWork.setType(choicePublicationType.getValue());
                scientificWork.setField(choiceFieldOfStudy.getValue());
                // scientificWork.setContent(Files.readString(Path.of(chosenFile.toURI())));//todo load file
                scientificWork.setYear(spinnerYear.getValue());
                scientificWork.setAuthor(author);
                scientificWork.setAdditional(fldPublishedIn.getText());
                scientificWork.setTags(txtAreaTags.getText());

                instance.addScientificWork(scientificWork);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Scientific work successfully added");

                alert.showAndWait();
                lblStatusBar.setText("Successfully added");
            }
        }
        else {
            lblStatusBar.setText("Please, fill the form properly");
        }
    }
    //todo add citations/references

    private boolean isAdditionalInfoValid() {
        return checkBoxAdditional.isSelected() && isInputValid(fldPublishedIn) || ! checkBoxAdditional.isSelected();
    }

    private boolean isInputValid(TextField fld) {
        return fld.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"));
    }

    public void actionUpload(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt")); //can add also xml and pdf extensions
        chosenFile = fileChooser.showOpenDialog(new Stage());
        if (chosenFile == null) { //is any file selected
            lblNothingChosen.setVisible(true);
            return;
        }
        lblNothingChosen.setText("Uploaded");
        lblNothingChosen.setStyle("-fx-text-fill: green;");
        lblNothingChosen.setVisible(true);
        fldTitle.setText(chosenFile.getName());
    }

    public void actionClose(ActionEvent actionEvent) {
        fldTitle.getScene().getWindow().hide();
    }

    public void actionCheckBox(ActionEvent actionEvent) {
        fldPublishedIn.setVisible(checkBoxAdditional.isSelected());
        if (!checkBoxAdditional.isSelected()) fldPublishedIn.setText("");
    }
}
