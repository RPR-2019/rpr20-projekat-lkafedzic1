package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.controller.Validation;

import java.time.LocalDate;
import java.util.Date;

public class Person implements Validation {
    private int id;
    private String firstName, lastName;
    private Gender gender;
    private LocalDate dateOfBirth;

    public Person() {
    }

    public Person(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        setFirstName(firstName);
        setLastName(lastName);
        this.gender = gender;
        setDateOfBirth(dateOfBirth);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (isValidName(firstName))
            this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (isValidName(lastName))
            this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isBefore(LocalDate.now()))
            this.dateOfBirth = dateOfBirth;
    }
}
