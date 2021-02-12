package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ScientificWorkController {
    public TextField title;
    public TextField author;
    public ComboBox<String> publicationType;
    public Spinner<Integer> yearOfPublishing;
    public TextArea tags;
    public Button btnAdd, btnCancel;
    public ComboBox<String> fieldOfStudy;

    public void actionAddScienceWork(ActionEvent actionEvent) {
        ScientificWorkDAO database = ScientificWorkDAO.getInstance();

        database.addScienceWork();
    }

    @FXML
    private void initialize() {
        publicationType.getItems().addAll(
                "book",
                "journal article",
                "letter",
                "pattent",
                "other"
        );
        fieldOfStudy.getItems().addAll(
                "computer science",
                "engineering",
                "psychology",
                "mathematics",
                "history",
                "medicine",
                "art",
                "business",
                "sociology",
                "biology",
                "other"
        );

    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) author.getScene().getWindow();
        stage.close();
    }
}
