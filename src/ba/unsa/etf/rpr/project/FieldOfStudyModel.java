package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FieldOfStudyModel {
    private ObservableList<FieldOfStudy> fields = FXCollections.observableArrayList();

    public ObservableList<FieldOfStudy> getFields() {
        return fields;
    }

    public void setFields(ObservableList<FieldOfStudy> fields) {
        this.fields = fields;
    }

    public FieldOfStudyModel() {
        ScientificWorkDAO database = ScientificWorkDAO.getInstance();
        fields = database.getFields();
    }
}
