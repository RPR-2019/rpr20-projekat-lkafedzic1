package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.Validation;
import ba.unsa.etf.rpr.project.enums.Gender;

import java.time.LocalDate;

public class Person implements Validation {
    private int id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;

    public Person() {
    }

    public Person(String name, LocalDate dateOfBirth, Gender gender) {
        setName(name);
        this.gender = gender;
        setDateOfBirth(dateOfBirth);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
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
