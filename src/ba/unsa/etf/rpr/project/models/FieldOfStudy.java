package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.models.ChoiceField;

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
