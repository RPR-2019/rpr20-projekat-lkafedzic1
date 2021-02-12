package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;

import java.time.Year;

public class ScientificWorkController {
    public TextField title;
    public TextField author;
    public ComboBox<PublicationType> publicationType;
    public Spinner<Year> yearOfPublishing;
    public TextArea tags;
    public Button btnAdd, btnCancel;
    public ChoiceBox<FieldOfStudy> fieldOfStudy;

    @FXML
    private void initialize() {

    }

    public void actionAddScienceWork(ActionEvent actionEvent) {
    }

    public void actionCancel(ActionEvent actionEvent) {
    }
}
