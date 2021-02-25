package ba.unsa.etf.rpr.project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DocumentController {
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblYear;
    public Label lblTags;
    public Label lblReference;
    public TextArea txtArea;
    public Label lblStatusBar;

    @FXML
    public void initialize() {
/*        String name = "James Cizui";
        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        System.out.println(lastName + ", " + firstName.charAt(0) + "." + "(" + year + "). " + titleSentenceCase + ". " + nameOfJorunal );*/
    }

    public void actionDownload(ActionEvent actionEvent) {

    }

    public void actionClose(ActionEvent actionEvent) {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }

    public void actionDelete(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
    }
}
