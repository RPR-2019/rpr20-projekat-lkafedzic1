package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewChoiceController {
    //ovdje je polimorfizam, može biti za FieldOfStudy ili za PublicationType
    public Button btnAdd, btnCancel;
    public Label lblStatusBar;
    public TextField fldTitle;


    public void actionAddNewField(ActionEvent actionEvent) {
/*        ScientificWorkDAO database = ScientificWorkDAO.getInstance();
        database.addField(fldTitle.getText());*/
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) fldTitle.getScene().getWindow();
        stage.close();
    }
}
