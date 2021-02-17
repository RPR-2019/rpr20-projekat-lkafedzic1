package ba.unsa.etf.rpr.project;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Home {
    private ObservableList<PublicationType> types = FXCollections.observableArrayList();
    private SimpleObjectProperty<PublicationType> selectedType = new SimpleObjectProperty<>();
    //todo iskoristi set
    //todo table i tablecolumn

    public ObservableList<PublicationType> getTypes() {
        return types;
    }

    public void setTypes(ObservableList<PublicationType> types) {
        this.types = types;
    }

    public PublicationType getSelectedType() {
        return selectedType.get();
    }

    public SimpleObjectProperty<PublicationType> selectedTypeProperty() {
        return selectedType;
    }

    public void setSelectedType(PublicationType selectedType) {
        this.selectedType.set(selectedType);
    }
}
