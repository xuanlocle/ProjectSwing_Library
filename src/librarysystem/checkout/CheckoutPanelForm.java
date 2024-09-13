package librarysystem.checkout;

import business.CheckoutEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CheckoutPanelForm extends JPanel implements ICheckoutView {
    private ICheckoutPresenter mPresenter;

    private JTable table1;
    private JTextField txtMemberId;
    private JTextField txtISBN;
    private JButton btnCheckout;
    private JPanel checkoutPanel;
    private JLabel lblCheckoutRecord;
    private final String[] columnNames = {"No.", "ISBN", "Title", "Checkout Date", "Due Date"};

    public CheckoutPanelForm() {
        mPresenter = new CheckoutPresenter(this);
        add(checkoutPanel);

        // Create a table with 4 columns
        initTable();

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mPresenter.handleCheckoutAction(txtMemberId.getText(), txtISBN.getText());
            }
        });
        mPresenter.fetchData();
    }

    private void initTable() {
        // Create a table model and set it to the table
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

    }

    @Override
    public void showErrorDialog(String text) {
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSuccessDialog(String text) {
        JOptionPane.showMessageDialog(this, text, "Success", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void updateTable(String memberId, HashMap<Integer, CheckoutEntry> cr) {

        if(memberId == null) {
            lblCheckoutRecord.setText("All checkout entries of library");
        } else {
            lblCheckoutRecord.setText("Checkout Records of member: " + memberId);
        }

        Object[][] rows = new Object[][]{};

        DefaultTableModel tableModel = new DefaultTableModel(rows, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < cr.size(); i++) {
            CheckoutEntry record = cr.get(i);
            Object[] row = record.toRowData();
            row[0] = i + 1;
            tableModel.addRow(row);
        }

        table1.setModel(tableModel);
    }
}
