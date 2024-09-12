package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;

public interface DataAccess { 
	HashMap<String,Book> readBooksMap();
	HashMap<String,User> readUserMap();
	HashMap<String, LibraryMember> readMemberMap();
	void saveNewMember(LibraryMember member);

	HashMap<String, Author> getAuthors();
	void saveAuthor(Author author);

	HashMap<String, Book> getBooks();
	void saveBook(Book book);
	void addACopy(Book book);

	void cleanUpTestStorage();
}
