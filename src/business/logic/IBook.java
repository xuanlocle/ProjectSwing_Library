package business.logic;

import business.Author;
import business.Book;

import java.util.List;

public interface IBook {
    List<Author> getAuthors();
    boolean saveAuthor(Author author);

    List<Book> getBooks();
    boolean saveBook(Book book);
    void addACopy(Book book);
}
