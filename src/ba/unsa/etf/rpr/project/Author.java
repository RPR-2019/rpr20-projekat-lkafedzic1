package ba.unsa.etf.rpr.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Author extends Person implements Comparable<Author> {
        private List<ScientificWork> works;

    public List<ScientificWork> getWorks() {
        return works;
    }

    public void setWorks(List<ScientificWork> works) {
        this.works = works;
    }

    public Author() {
        works = new ArrayList<>();
    }

    public Author(String name, LocalDate dateOfBirth, Gender gender) {
        super.setName(name);
        super.setDateOfBirth(dateOfBirth);
        super.setGender(gender);
        works = new ArrayList<>();
    }

    public Author(String name, LocalDate dateOfBirth, Gender gender, ArrayList<ScientificWork> works) {
        super.setName(name);
        super.setDateOfBirth(dateOfBirth);
        super.setGender(gender);
        this.works = works;
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
