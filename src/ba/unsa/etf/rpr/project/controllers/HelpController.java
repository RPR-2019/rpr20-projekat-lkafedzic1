package ba.unsa.etf.rpr.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.ResourceBundle;

public class HelpController {

    public TextArea txtAreaInstructions;

    @FXML
    public void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        txtAreaInstructions.setText(bundle.getString("instructionsText"));
    }


}
