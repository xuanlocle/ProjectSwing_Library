package dataaccess;

import business.*;
import business.exception.CheckoutException;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;


public class DataAccessFacade implements DataAccess {
    private boolean isTest = false;
	enum StorageType {
		BOOKS, MEMBERS, USERS, AUTHOR;
	}

	private static String OUTPUT_DIR = System.getProperty("user.dir") + "/src/dataaccess/storage";

	public DataAccessFacade() {

	}

	public DataAccessFacade(boolean isTest) {
		if (isTest) {
			this.isTest = true;
			OUTPUT_DIR = System.getProperty("user.dir") + "/src/dataaccess/test_storage";
		}
	}

	@Override
	public void cleanUpTestStorage() {
		if (isTest) {
			Arrays.stream(Objects.requireNonNull(new File(OUTPUT_DIR).listFiles())).forEach(File::delete);
			new File(OUTPUT_DIR).delete();
		}
	}

	@Override
	public HashMap<Integer, CheckoutEntry> getAllCheckoutEntries() {
		HashMap<String, LibraryMember> members = readMemberMap();
		HashMap<Integer, CheckoutEntry> checkoutEntries = new HashMap<>();
		int i = 0;
		for (LibraryMember member : members.values()) {
			for (CheckoutEntry entry : member.getRecord().getCheckoutEntries()) {
				checkoutEntries.put(i++, entry);
			}
		}
		return checkoutEntries;
	}

    @Override
    public HashMap<Integer, CheckoutEntry> getCheckoutEntriesByMemberId(String memberId) {
        HashMap<String, LibraryMember> members = readMemberMap();
        HashMap<Integer, CheckoutEntry> checkoutEntries = new HashMap<>();

        if (members.containsKey(memberId)) {
            LibraryMember member = members.get(memberId);
            int i = 0;
            for (CheckoutEntry entry : member.getRecord().getCheckoutEntries()) {
                checkoutEntries.put(i++, entry);
            }
        }
        return checkoutEntries;
    }

    @Override
    public void migrationMemberRecord() {
        HashMap<String, LibraryMember> mems = readMemberMap();
		if (mems == null) {
			mems = new HashMap<>();
		}
        for (LibraryMember member : mems.values()) {
            if (member.getRecord() == null) {
                member.setRecord(new CheckoutRecord(new ArrayList<>()));
            }
        }
        saveToStorage(StorageType.MEMBERS, mems);
    }

    //implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	@Override
	public void addUser(String id, String password, Auth auth) {
		User user = new User(id, password, auth);
		HashMap<String, User> userMap = readUserMap();
		if (userMap == null) {
			userMap = new HashMap<>();
		}
		userMap.put(user.getId(), user);
		loadUserMap(new ArrayList<User>(userMap.values()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Author> getAuthors() {
		HashMap<String, Author> authors = (HashMap<String, Author>) readFromStorage(StorageType.AUTHOR);
		if (Objects.isNull(authors)) {
			authors = new HashMap<>();
		}
		return authors;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAuthor(Author author) {
		HashMap<String, Author> authors = (HashMap<String, Author>) readFromStorage(StorageType.AUTHOR);
		if (Objects.isNull(authors)) {
			authors = new HashMap<>();
		}

		authors.put(author.getFirstName() + author.getLastName(), author);
		saveToStorage(StorageType.AUTHOR, authors);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Book> getBooks() {
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		if (Objects.isNull(books)) {
			books = new HashMap<>();
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveBook(Book book) {
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		if (Objects.isNull(books)) {
			books = new HashMap<>();
		}

		books.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, books);
	}

	@Override
	public void addACopy(Book book) {
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		if (Objects.isNull(books)) {
			books = new HashMap<>();
		}

		books.get(book.getIsbn()).addCopy();
		saveToStorage(StorageType.BOOKS, books);
	}

	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}


	/////load methods - these place test data into the storage area
	///// - used just once at startup


	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	@Override
	public boolean isCheckoutAvailable(String memberId, String isbn) throws CheckoutException {
		HashMap<String, LibraryMember> mems = readMemberMap();
		if (!mems.containsKey(memberId)) {
			//not exists memberId
			throw new CheckoutException("Not existing member " + memberId);
		}
		HashMap<String, Book> books = readBooksMap();
		if (!books.containsKey(isbn)) {
			//not exists isbn
			throw new CheckoutException("Not existing book with isbn " + isbn);
		}
		Book bookNeedCheckout = books.get(isbn);
		if (!bookNeedCheckout.isAvailable()) {
			//none of the copies of book are avaialble
			throw new CheckoutException("This book is not available!");
		}
		return true;
	}

	@Override
	public void checkoutBook(String memberId, String isbn) {
        //get member
        HashMap<String, LibraryMember> members = readMemberMap();
        LibraryMember member = members.get(memberId);

        //get book
        HashMap<String, Book> books = getBooks();
        Book b = books.get(isbn);
        BookCopy currentCopy = b.getNextAvailableCopy();

        //create new entry
        CheckoutEntry newEntry = new CheckoutEntry(LocalDate.now().plusDays(currentCopy.getBook().getMaxCheckoutLength()),
                currentCopy);
        //add entry to member's record
        member.getRecord().getCheckoutEntries().add(newEntry);

        //savebook
        currentCopy.changeAvailability();
        b.updateCopies(currentCopy);
        books.put(isbn, b);
        saveToStorage(StorageType.BOOKS, books);

        //save members
        members.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, members);
    }

	/////load methods - these place test data into the storage area
	///// - used just once at startup

	static void createFileIfNotExist(String fileName) {
		try {
			Files.createDirectories(FileSystems.getDefault().getPath(OUTPUT_DIR));
			Files.createFile(FileSystems.getDefault().getPath(OUTPUT_DIR, fileName));
		} catch(IOException e) {
		}
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			createFileIfNotExist(type.toString());

			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			createFileIfNotExist(type.toString());

			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(EOFException e) {
			System.out.println("EOF error");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}



	final static class Pair<S,T> implements Serializable{

		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}

}
