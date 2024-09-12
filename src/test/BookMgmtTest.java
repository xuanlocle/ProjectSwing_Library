package test;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookMgmtTest {
    static DataAccess dataAccess;
    static ControllerInterface controller;

    @BeforeAll
    static void setup() {
        dataAccess = new DataAccessFacade(true);
        controller = new SystemController(dataAccess);
    }

    @AfterEach
    void clean() {
        dataAccess.cleanUpTestStorage();
    }

    private List<Author> generateAuthors(Integer count) {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < count; i ++) {
            Address address = new Address("Street" + i, "City" + i, "State" + i, "Zip" + i);
            Author author = new Author(
                    "First Name" + i, "Last Name" + i, "Phone" + i, address, "Short Bio" + i, true);
            authors.add(author);
        }
        return authors;
    }

    @Test()
    public void addAuthorSuccess() {
        Author author = generateAuthors(1).get(0);
        controller.saveAuthor(author);

        List<Author> authors = controller.getAuthors();
        assertEquals(1, authors.size());
        assertEquals(author, authors.get(0));
    }

    @Test()
    public void addBookSuccess() {
        List<Author> authors = generateAuthors(2);

        Book book = new Book("978-0-596-52068-7", "Book", 7, authors, 100);
        controller.saveBook(book);

        List<Book> books = controller.getBooks();
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));

        Book book2 = new Book("978-0-596-52068-7", "Book 2", 7, authors, 100);
        controller.saveBook(book2);
        books = controller.getBooks();
        assertEquals(1, books.size());
        assertEquals(book2, books.get(0));
    }
}
