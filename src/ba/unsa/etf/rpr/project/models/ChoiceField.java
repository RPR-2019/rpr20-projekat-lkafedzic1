package ba.unsa.etf.rpr.project.models;

public class ChoiceField {
    private String title;

    public ChoiceField(int id, String title) {
        this.title = title;
    }

    public ChoiceField() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
