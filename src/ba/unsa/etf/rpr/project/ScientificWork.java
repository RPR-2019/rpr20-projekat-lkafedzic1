package ba.unsa.etf.rpr.project;

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class ScientificWork implements Validation {
    private int id, year;
    private SimpleStringProperty title;
    private SimpleStringProperty type, field; //id,id
    private SimpleStringProperty content;
    private SimpleStringProperty author; //id
    private SimpleStringProperty additional;
    private SimpleStringProperty tags;

    public ScientificWork(){
        this.title = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.field = new SimpleStringProperty();
        this.content = new SimpleStringProperty();
        this.tags = new SimpleStringProperty();
        this.additional = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
    }

/*    public ScientificWork(String title){
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty();
        this.field = new SimpleStringProperty();
        this.content = new SimpleStringProperty();
        this.tags = new SimpleStringProperty();
        this.additional = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
    }

    public ScientificWork(int year, String title, String author, String additional, String tags) {
        this.year = year;
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.additional = new SimpleStringProperty(additional);
        this.tags = new SimpleStringProperty(tags);
        this.content = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.field = new SimpleStringProperty();
    }*/

    public ScientificWork(String title, String author, int year, String fieldOfStudy, String publicationType, String additional, String tags, String content) {
        this.title = new SimpleStringProperty(title);
        if (year <= LocalDate.now().getYear()) {
            this.year = year;
        }
        this.type = new SimpleStringProperty(publicationType);
        this.field = new SimpleStringProperty(fieldOfStudy);
        this.tags = new SimpleStringProperty(tags);
        this.additional = new SimpleStringProperty(additional);
        this.author = new SimpleStringProperty(author);
        this.content = new SimpleStringProperty(content);
    }

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
        if (isValidTitle(title))
            this.title.set(title);
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

    public String getAdditional() {
        return additional.get();
    }

    public SimpleStringProperty additionalProperty() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional.set(additional);
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

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    @Override
    public String toString() {
        return title.get();
    }
}
