package ba.unsa.etf.rpr.project;

import java.time.LocalDate;

public class Author extends Person implements Comparable<Author> {

    public Author() {
    }

    public Author(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
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
