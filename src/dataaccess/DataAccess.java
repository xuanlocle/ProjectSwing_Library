package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;

public interface DataAccess { 
	HashMap<String,Book> readBooksMap();
	HashMap<String,User> readUserMap();
	HashMap<String, LibraryMember> readMemberMap();
	HashMap<Integer, CheckoutRecord> readCheckoutRecordMap();
	void saveNewMember(LibraryMember member);

	HashMap<String, Author> getAuthors();
	void saveAuthor(Author author);

	HashMap<String, Book> getBooks();
	void saveBook(Book book);
	void addACopy(Book book);

	boolean isCheckoutAvailable(String memberId, String isbn);
	void checkoutBook(String memberId, String isbn);

	void cleanUpTestStorage();
}
