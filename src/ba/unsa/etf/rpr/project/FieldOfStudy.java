package ba.unsa.etf.rpr.project;

import java.io.Serializable;

public class FieldOfStudy extends ChoiceField {

    public FieldOfStudy(int id, String title) {
        super(id, title);
    }

    public FieldOfStudy() {
    }

    @Override
    public String getTitle() {
        String res = super.getTitle();
        return res.toUpperCase();
    }
}
