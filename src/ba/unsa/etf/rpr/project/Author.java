package ba.unsa.etf.rpr.project;

import java.util.Date;

public class Author extends Person implements Comparable<Author> {
    private String firstName, lastName;
    private int id;
    private String dateOfBirth;
    private Gender gender;

    public Author(String firstName, String lastName, String dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Author(String firstName, String lastName, int id, String dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    @Override
    public int compareTo(Author a) {
        /*0 if equal, -1 if this is less than a, 1 if this is greater then a*/
        return this.toString().compareTo(a.toString());
    }
}
