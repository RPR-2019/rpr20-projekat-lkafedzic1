package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PublicationTypeModel {
    private ObservableList<PublicationType> types = FXCollections.observableArrayList();

    public PublicationTypeModel() {
/*        ScientificWorkDAO database = ScientificWorkDAO.getInstance();
        types = database.getTypes();*/
    }

    public ObservableList<PublicationType> getTypes() {
        return types;
    }

    public void setTypes(ObservableList<PublicationType> types) {
        this.types = types;
    }
}
