package librarysystem;

import business.Author;
import business.Book;
import business.ControllerInterface;
import librarysystem.common.ComboItem;
import librarysystem.common.ISBNWithHyphenDocumentFilter;
import librarysystem.common.NumericDocumentFilter;
import librarysystem.main.TableButtonRender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static librarysystem.Util.isFieldValid;
import static librarysystem.Util.isValidISBN;

public class BookMgmtView extends JPanel implements IAuthorHolderView {
    ControllerInterface controller;

    private JPanel mainPanel;
    private JTextField txtISBN;
    private JTextField txtTitle;
    private JComboBox comboRule;
    private JTextField txtCopies;
    private JButton btnAdd;
    private JButton btnReset;
    private JTable tableBook;
    private JButton btnAddAuthor;
    private JTable tableAuthor;
    private JPanel authorPanel;

    private List<ComboItem> comboItems = List.of(new ComboItem("21 days", 21), new ComboItem("7 days", 7));
    private String[] authorColumns = {"First Name", "Last Name", "Short Bio"};
    private String[] bookColumns = {"Add Copy", "ISBN", "Title", "Author", "Rule", "Copies", "Availability"};
    private List<Author> authors = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    public BookMgmtView(ControllerInterface controller) {
        this.controller = controller;

        ((AbstractDocument) txtISBN.getDocument()).setDocumentFilter(new ISBNWithHyphenDocumentFilter());
        ((AbstractDocument) txtCopies.getDocument()).setDocumentFilter(new NumericDocumentFilter(5));

        initButton();
        initRuleCombo();
        initTableAuthor();
        refreshBookTable();

        tableBook.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableBook.rowAtPoint(evt.getPoint());
                int col = tableBook.columnAtPoint(evt.getPoint());
                if (col == 0) {
                    addBookCopy(row);
                    refreshBookTable();
                }
            }
        });

        mainPanel.setPreferredSize(new Dimension(800, 600));
        add(mainPanel);
    }

    private void refreshBookTable() {
        getBook();
        initTableBook();
    }

    private void initTableAuthor() {
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, authorColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are non-editable
                return false;
            }
        };
        for (Author person : this.authors) {
            Object[] row = {person.getFirstName(), person.getLastName(), person.getBio()};
            model.addRow(row);
        }
        tableAuthor.setModel(model);
    }

    private void initTableBook() {
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, bookColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are non-editable
                return false;
            }
        };
        for (Book book : this.books) {
            Object[] row = getRow(book);
            model.addRow(row);
        }
        tableBook.setModel(model);
        tableBook.getColumn("Add Copy").setCellRenderer(new TableButtonRender());
    }

    private static Object[] getRow(Book book) {
        Author author = book.getAuthors().get(0);
        String authorFullName = author.getFirstName() + ", " + author.getLastName();
        String rule = book.getMaxCheckoutLength() + " days";
        String available = book.getNumCopies() > 0 ? "Available" : "Occupied";
        return new Object[] {
                "Add Copy",
                book.getIsbn(),
                book.getTitle(),
                authorFullName,
                rule,
                book.getNumCopies(),
                available};
    }

    private void initRuleCombo() {
        comboItems.forEach(item -> comboRule.addItem(item));
    }

    private void initButton() {
        btnAdd.addActionListener(e -> addBtnPressed());
        btnReset.addActionListener(e -> resetBtnPressed());
        btnAddAuthor.addActionListener(e -> addAuthorBtnPressed());
    }

    private boolean validateBook() {
        if (!isValidISBN(txtISBN.getText())) {
            JOptionPane.showMessageDialog(this, "ISBN is mandatory (Ex. 978-0-596-52068-7)");
            return false;
        }

        if (!isFieldValid(txtTitle)){
            JOptionPane.showMessageDialog(this, "Title is mandatory");
            return false;
        }

        if (!isFieldValid(txtCopies)){
            JOptionPane.showMessageDialog(this, "Copies is mandatory");
            return false;
        }

        if (authors.isEmpty()){
            JOptionPane.showMessageDialog(this, "Author is mandatory");
            return false;
        }

        return true;
    }

    private void getBook() {
        this.books = controller.getBooks();
    }

    private void saveBook() {
        Book book = new Book(
                txtISBN.getText(),
                txtTitle.getText(),
                comboItems.get(comboRule.getSelectedIndex()).getValue(),
                this.authors,
                Integer.parseInt(txtCopies.getText()));
        controller.saveBook(book);
    }

    private void addBookCopy(int index) {
        controller.addACopy(books.get(index));
    }

    private void addBtnPressed() {
        if (!validateBook())
            return;

        saveBook();
        resetBtnPressed();
        refreshBookTable();
    }

    private void resetBtnPressed() {
        txtISBN.setText("");
        txtTitle.setText("");
        txtCopies.setText("");
        comboRule.setSelectedIndex(0);
        this.authors = new ArrayList<>();
        initTableAuthor();
    }

    private void addAuthorBtnPressed() {
        AddAuthorDialog dialog = new AddAuthorDialog(this, controller);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @Override
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        this.initTableAuthor();
    }
}
