package librarysystem;

import business.Address;
import business.Author;
import business.ControllerInterface;
import librarysystem.common.NumericDocumentFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static util.Util.isFieldValid;

public class AddAuthorDialog extends JDialog {
    ControllerInterface controller;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtLName;
    private JTextField txtFName;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtState;
    private JTextField txtZip;
    private JTable tableAuthor;
    private JButton btnReset;
    private JTextField txtPhone;
    private JTextArea txtBio;
    private JButton btnAdd;
    private JCheckBox chkExpert;
    private IAuthorHolderView main;

    private final String[] AUTHOR_COLUMNS = {"First Name", "Last Name", "Short Bio"};
    List<Author> authors;

    public AddAuthorDialog(IAuthorHolderView mainWindow, ControllerInterface controller) {
        this.main = mainWindow;
        this.controller = controller;

        setSize(800, 600);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        ((AbstractDocument) txtZip.getDocument()).setDocumentFilter(new NumericDocumentFilter(5));
        ((AbstractDocument) txtPhone.getDocument()).setDocumentFilter(new NumericDocumentFilter(10));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onReset();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        getAuthors();
        refreshTable();
    }

    private void getAuthors() {
        this.authors = controller.getAuthors();
    }

    private void refreshTable() {
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, AUTHOR_COLUMNS) {
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

    private boolean validateForm() {
        if (!isFieldValid(txtFName))
            return false;
        if (!isFieldValid(txtLName))
            return false;
        if (!isFieldValid(txtStreet))
            return false;
        if (!isFieldValid(txtCity))
            return false;
        if (!isFieldValid(txtState))
            return false;
        if (!isFieldValid(txtZip))
            return false;
        if (!isFieldValid(txtPhone))
            return false;
        if (!isFieldValid(txtBio))
            return false;
        return true;
    }

    private void onReset() {
        txtFName.setText("");
        txtLName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtState.setText("");
        txtZip.setText("");
        txtPhone.setText("");
        txtBio.setText("");
        chkExpert.setSelected(false);
    }

    private void onAdd() {
        if (!validateForm())
            return;
        Address address = new Address(
                txtStreet.getText(),
                txtCity.getText(),
                txtState.getText(),
                txtZip.getText());
        Author author = new Author(
                txtFName.getText(),
                txtLName.getText(),
                txtPhone.getText(),
                address,
                txtBio.getText(),
                chkExpert.isSelected());

        boolean saved = controller.saveAuthor(author);
        if (!saved) {
            JOptionPane.showMessageDialog(
                    this, "Please check Name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        getAuthors();
        refreshTable();
    }

    private void onOK() {
        int[] ids = tableAuthor.getSelectedRows();

        List<Author> list = new ArrayList<>();
        for (int index : ids) {
            list.add(this.authors.get(index));
        }
        main.setAuthors(list);

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
