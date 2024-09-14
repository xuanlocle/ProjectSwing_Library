package dataaccess;

import java.util.HashMap;

import business.*;
import business.exception.CheckoutException;

public interface DataAccess { 
	HashMap<String,Book> readBooksMap();
	HashMap<String,User> readUserMap();
	HashMap<String, LibraryMember> readMemberMap();
	void saveNewMember(LibraryMember member);
	void addUser(String id, String password, Auth auth);

	HashMap<String, Author> getAuthors();
	void saveAuthor(Author author);

	HashMap<String, Book> getBooks();
	void saveBook(Book book);
	void addACopy(Book book);

	boolean isCheckoutAvailable(String memberId, String isbn) throws CheckoutException;
	void checkoutBook(String memberId, String isbn);

	void cleanUpTestStorage();

	HashMap<Integer, CheckoutEntry> getAllCheckoutEntries();
	HashMap<Integer, CheckoutEntry> getCheckoutEntriesByMemberId(String memberId);

	void migrationMemberRecord();

}
