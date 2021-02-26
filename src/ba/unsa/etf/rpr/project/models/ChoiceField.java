package ba.unsa.etf.rpr.project.models;

public class ChoiceField {
    private int id;
    private String title;

    public ChoiceField(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public ChoiceField() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
