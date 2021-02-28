package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.enums.Gender;

import java.time.LocalDate;

public class Author extends Person implements Comparable<Author> {

    public Author() {
    }

    public Author(String name, LocalDate dateOfBirth, Gender gender) {
        super(name, dateOfBirth, gender);
    }

    @Override
    public int compareTo(Author a) {
        /*0 if equal, -1 if this is less than a, 1 if this is greater then a*/
        return this.toString().compareTo(a.toString());
    }

    @Override
    public boolean equals(Object obj) {
        Author a = (Author) obj;
        return getName().equals(a.getName()) && getDateOfBirth().equals(a.getDateOfBirth()) && getGender().equals(a.getGender());
    }
}
