package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.models.ChoiceField;

public class PublicationType extends ChoiceField {
    @Override
    public String getTitle() {
        return "Publication type: " + super.getTitle();
    }
}
