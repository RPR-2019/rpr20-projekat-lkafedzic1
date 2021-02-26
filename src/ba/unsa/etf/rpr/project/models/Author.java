package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;

public class Author extends Person implements Comparable<Author> {

    public Author() {
    }

    @Override
    public int compareTo(Author a) {
        /*0 if equal, -1 if this is less than a, 1 if this is greater then a*/
        return this.toString().compareTo(a.toString());
    }
    
}
