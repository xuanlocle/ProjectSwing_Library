package business.logic.impl;

import business.Author;
import business.Book;
import business.logic.IBook;
import dataaccess.DataAccess;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static util.ValidationHelper.isValidISBN;
import static util.ValidationHelper.isValidString;

public class BookService implements IBook {
    DataAccess dataAccess;

    public BookService() {}
    public BookService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
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

    private List<Book> convertBookToList(HashMap<String, Book> books) {
        if (Objects.isNull(books)) {
            return List.of();
        }
        return books.values().stream().toList();
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

    @Override
    public List<Book> getBooks() {
        HashMap<String, Book> books = dataAccess.getBooks();
        return convertBookToList(books);
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
}
