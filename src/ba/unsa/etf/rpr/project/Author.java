package ba.unsa.etf.rpr.project;

import java.text.SimpleDateFormat;

public class Author implements Person, Comparable<Author> {
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
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstname() {
        return firstName;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstName = firstName;
    }

    @Override
    public String getLastname() {
        return lastName;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastName = lastName;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return getFirstname() + " " + getLastname();
    }


    @Override
    public int compareTo(Author a) {
        /*0 if equal, -1 if this is less than a, 1 if this is greater then a*/
        return this.toString().compareTo(a.toString());
    }
}
