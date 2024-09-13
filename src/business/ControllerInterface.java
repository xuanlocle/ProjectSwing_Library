package business;

import java.util.List;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void logout();
	List<String> allMemberIds();
	List<String> allBookIds();

	List<Author> getAuthors();
	void saveAuthor(Author author);

	List<Book> getBooks();
	void saveBook(Book book);
	void addACopy(Book book);

	List<LibraryMember> getLibraryMembers();
	void addLibraryMember(String firstName, String lastName, String phone, String street, String city, String state, String zip);
}
