package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PublicationTypeController {
    public TextField title;

    public void actionAddNewType(ActionEvent actionEvent) {
        ScientificWorkDAO database = ScientificWorkDAO.getInstance();
        database.addPublicationType(title.getText());
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
    }
}
