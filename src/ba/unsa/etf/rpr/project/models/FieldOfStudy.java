package ba.unsa.etf.rpr.project.models;

public class FieldOfStudy extends ChoiceField {

    public FieldOfStudy(String title) {
        super(title);
    }

    public FieldOfStudy() {
    }

    @Override
    public String getTitle() {
        String res = super.getTitle();
        return res.toUpperCase();
    }
}
