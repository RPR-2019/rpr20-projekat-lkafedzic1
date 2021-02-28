package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.Gender;
import ba.unsa.etf.rpr.project.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    Author author = null;

    @BeforeEach
    void before() {
        author = new Author("Ela Kagocic", LocalDate.of(1997,12,20), Gender.FEMALE);
    }

    @Test
    void getId() {
        assertEquals(0, author.getId());
    }

    @Test
    void setName() {
        author.setName("Ella");
        assertEquals("Ella", author.getName());
    }

    @Test
    void testEquals() {
        Author other = new Author("Ela Kagocic", LocalDate.of(1997,12,20), Gender.FEMALE);
        assertEquals(author, other);
    }

    @Test
    void getGender() {
        assertEquals(Gender.FEMALE, author.getGender());
    }

}
