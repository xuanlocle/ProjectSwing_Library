package business;

import java.util.*;
import java.util.stream.Collectors;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static List<IAuthStateListener> authStateListeners = new ArrayList<IAuthStateListener>();
	DataAccess dataAccess;

	public SystemController() {}

	public SystemController(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
		migration();
	}

	private void migration() {
		dataAccess.migrationMemberRecord();
	}

	public static void registerAuthStateListener(IAuthStateListener listener) {
		authStateListeners.add(listener);
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

		for (IAuthStateListener l : authStateListeners) {
			l.onLogin(currentAuth);
		}
	}

	@Override
	public void logout() {
		currentAuth = null;
		for (IAuthStateListener l : authStateListeners) {
			l.onLogout();
		}
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

	private List<Book> convertBookToList(HashMap<String, Book> books) {
		if (Objects.isNull(books)) {
			return List.of();
		}
		return books.values().stream().toList();
	}

	@Override
	public List<Book> getBooks() {
		HashMap<String, Book> books = dataAccess.getBooks();
		return convertBookToList(books);
	}

	@Override
	public void saveBook(Book book) {
		dataAccess.saveBook(book);
	}

	@Override
	public void addACopy(Book book) {
		dataAccess.addACopy(book);
	}

	@Override
	public List<LibraryMember> getLibraryMembers() {
		HashMap<String, LibraryMember> members = dataAccess.readMemberMap();
		if (Objects.isNull(members)) {
			return List.of();
		}
		return members.values().stream().toList();
	}

	private String generateMemberId() {
		List<Integer> memberIds = allMemberIds().stream().map(Integer::parseInt).toList();;
		if (memberIds.isEmpty()) {
			return "1";
		}
		Integer maxMemberId = Collections.max(memberIds);
		return String.valueOf( maxMemberId + 1);
	}

	@Override
	public void addLibraryMember(String firstName, String lastName, String phone, String street, String city, String state, String zip) {
		String memberId = generateMemberId();
		Address address = new Address(street, city, state, zip);
		LibraryMember member = new LibraryMember(memberId, firstName, lastName, phone, address);
		dataAccess.saveNewMember(member);
	}
}
