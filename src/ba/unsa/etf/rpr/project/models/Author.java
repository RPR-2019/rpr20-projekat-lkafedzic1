package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Author extends Person implements Comparable<Author> {

    public Author() {
    }

    public Author(String name, LocalDate dateOfBirth, Gender gender) {
        super.setName(name);
        super.setDateOfBirth(dateOfBirth);
        super.setGender(gender);
    }

    public Author(String name, LocalDate dateOfBirth, Gender gender, ArrayList<ScientificWork> works) {
        super.setName(name);
        super.setDateOfBirth(dateOfBirth);
        super.setGender(gender);
    }

    @Override
    public int compareTo(Author a) {
        /*0 if equal, -1 if this is less than a, 1 if this is greater then a*/
        return this.toString().compareTo(a.toString());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
