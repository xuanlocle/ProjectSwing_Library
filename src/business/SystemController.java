package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	DataAccess dataAccess;

	public SystemController() {}

	public SystemController(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public List<Author> getAuthors() {
		HashMap<String, Author> authors = dataAccess.getAuthors();
		if (Objects.isNull(authors)) {
			return List.of();
		}
		return authors.values().stream().toList();
	}

	@Override
	public void saveAuthor(Author author) {
		dataAccess.saveAuthor(author);
	}

	@Override
	public List<Book> getBooks() {
		HashMap<String, Book> books = dataAccess.getBooks();
		if (Objects.isNull(books)) {
			return List.of();
		}
		return books.values().stream().toList();
	}

	@Override
	public void saveBook(Book book) {
		dataAccess.saveBook(book);
	}
}
