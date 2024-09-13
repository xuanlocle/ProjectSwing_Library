package business;

import java.util.*;
import java.util.stream.Collectors;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import javax.swing.*;

import static util.Util.isFieldValid;
import static util.ValidationHelper.isValidISBN;
import static util.ValidationHelper.isValidString;

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

	public static void deregisterAuthStateListener(IAuthStateListener listener) {
		authStateListeners.remove(listener);
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
	public void addUser(String id, String password, Auth auth) {
		this.dataAccess.addUser(id, password, auth);
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
	private boolean isValidAuthor(Author author) {
		if (!isValidString(author.getFirstName()))
			return false;
		if (!isValidString(author.getLastName()))
			return false;
		if (Objects.isNull(author.getAddress()))
			return false;
		if (!isValidString(author.getAddress().getStreet()))
			return false;
		if (!isValidString(author.getAddress().getCity()))
			return false;
		if (!isValidString(author.getAddress().getState()))
			return false;
		if (!isValidString(author.getAddress().getZip()))
			return false;
		if (!isValidString(author.getTelephone()))
			return false;
		if (!isValidString(author.getBio()))
			return false;
		return true;
	}

	@Override
	public boolean saveAuthor(Author author) {
		if (!isValidAuthor(author)) {
			return false;
		}

		HashMap<String, Author> authors = dataAccess.getAuthors();
		if (authors.containsKey(author.getFirstName() + author.getLastName())) {
			return false;
		}

		dataAccess.saveAuthor(author);
		return true;
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

	private boolean isValidBook(Book book) {
		if (!isValidISBN(book.getIsbn())) {
			return false;
		}

		if (!isValidString(book.getTitle())) {
			return false;
		}

		if (book.getAuthors().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean saveBook(Book book) {
		if (!isValidBook(book)) {
			return false;
		}

		HashMap<String, Book> books = dataAccess.getBooks();
		if (books.containsKey(book.getIsbn())) {
			return false;
		}

		dataAccess.saveBook(book);
		return true;
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
		IValidator validator = new Validator();
        validator.validateFirstName(firstName);
        validator.validateLastName(lastName);
        validator.validatePhoneNumber(phone);
        validator.validateStreet(street);
        validator.validateCity(city);
        validator.validateState(state);
        validator.validateZipCode(zip);
        String memberId = generateMemberId();
		Address address = new Address(street, city, state, zip);
		LibraryMember member = new LibraryMember(memberId, firstName, lastName, phone, address);
		dataAccess.saveNewMember(member);
	}

	@Override
	public HashMap<Integer, CheckoutEntry> getAllCheckoutEntries() {
		return dataAccess.getAllCheckoutEntries();
	}


	@Override
	public void handleCheckoutAction(String memberId, String isbn, ICheckoutView action) {
		if (memberId.isEmpty()) {
			action.showErrorDialog("Member ID is empty");
			return;
		}
		if (isbn.isEmpty()) {
			action.showErrorDialog("ISBN is empty");
			return;
		}

		//test
		//user: 1004
		//isbn: 978-0-596-52068-7
		try {
			if (dataAccess.isCheckoutAvailable(memberId, isbn)) {
				checkoutBook(memberId, isbn);
				action.showSuccessDialog("Checkout successful");
				action.updateTable(memberId, dataAccess.getCheckoutEntriesByMemberId(memberId));
			} else {
				action.showErrorDialog("The book " + isbn + " is not available");
			}
		} catch (CheckoutException e) {
			action.showErrorDialog(e.getMessage());
		}

	}

	@Override
	public void checkoutBook(String memberId, String isbn) {
		dataAccess.checkoutBook(memberId, isbn);
	}
}
