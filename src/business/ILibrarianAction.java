package business;

import java.util.List;

public interface ILibrarianAction extends IUserAction {
    void checkout(Book book);

    List<Book> getBookList(int limit);
}
