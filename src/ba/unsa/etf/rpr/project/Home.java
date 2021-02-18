package ba.unsa.etf.rpr.project;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

public class Home {
    private ObservableList<String> types = FXCollections.observableArrayList();
    private SimpleObjectProperty<String> selectedType = new SimpleObjectProperty<>();

    private ObservableList<String> works = FXCollections.observableArrayList();

}
