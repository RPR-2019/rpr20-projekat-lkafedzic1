package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FieldOfStudyModel {
    private ObservableList<FieldOfStudy> fields = FXCollections.observableArrayList();

    public FieldOfStudyModel() {
        ScientificWorkDAO database = ScientificWorkDAO.getInstance();
        fields = database.getFields();
    }
}
