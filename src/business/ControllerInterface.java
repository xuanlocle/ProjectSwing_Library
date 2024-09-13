package business;

import dataaccess.Auth;
import dataaccess.User;

import java.util.HashMap;
import java.util.List;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void logout();
	void addUser(String id, String password, Auth auth);

	List<String> allMemberIds();
	List<String> allBookIds();

	List<Author> getAuthors();
	boolean saveAuthor(Author author);

	List<Book> getBooks();
	boolean saveBook(Book book);
	void addACopy(Book book);

	List<LibraryMember> getLibraryMembers();
	void addLibraryMember(String firstName, String lastName, String phone, String street, String city, String state, String zip);

	HashMap<Integer, CheckoutEntry> getAllCheckoutEntries();
	void handleCheckoutAction(String memberId, String isbn, ICheckoutView action);
	void checkoutBook(String memberId, String isbn);



}
