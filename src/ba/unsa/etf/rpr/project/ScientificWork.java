package ba.unsa.etf.rpr.project;

import javafx.beans.property.SimpleStringProperty;

public class ScientificWork {
    private int id, year;
    private SimpleStringProperty title, tags;
    private SimpleStringProperty type, field;
    private SimpleStringProperty content;
    private SimpleStringProperty author;
    private SimpleStringProperty additional;

    public ScientificWork() {
    }

    public ScientificWork(String title, String author, int year, String field, String type, String additional) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = year;
        this.field = new SimpleStringProperty(field);
        this.type = new SimpleStringProperty(type);
        this.additional = new SimpleStringProperty(additional);
        this.tags = new SimpleStringProperty();
    }

/*    public ScientificWork(int id, int year, String title, String tags, String type, String field, String content, String additional) {
        this.id = id;
        this.year = year;
        this.title = new SimpleStringProperty(title);
        this.tags = new SimpleStringProperty(tags);
        this.type = new SimpleStringProperty(type);
        this.field = new SimpleStringProperty(field);
        this.content = new SimpleStringProperty(content);
        this.additional = new SimpleStringProperty(additional);
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTags() {
        return tags.get();
    }

    public SimpleStringProperty tagsProperty() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags.set(tags);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getField() {
        return field.get();
    }

    public SimpleStringProperty fieldProperty() {
        return field;
    }

    public void setField(String field) {
        this.field.set(field);
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getAdditional() {
        return additional.get();
    }

    public SimpleStringProperty additionalProperty() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional.set(additional);
    }

    @Override
    public String toString() {
        return title.get() + ", " + author.get() + " " + year;
    }
}
