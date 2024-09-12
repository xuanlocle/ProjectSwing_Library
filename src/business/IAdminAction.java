package business;

import java.util.List;

public interface IAdminAction extends IUserAction {
    void addMember(Person p);

    void addBook(Book b);

    void addBookCopy(BookCopy bc);

    List<Book> getBooks();
}
