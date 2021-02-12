package ba.unsa.etf.rpr.project;

public class PublicationType {
    private int id;
    private String type;

    public PublicationType() {
    }

    public PublicationType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
