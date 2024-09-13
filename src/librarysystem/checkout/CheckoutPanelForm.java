package librarysystem.checkout;

import business.CheckoutRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;

public class CheckoutPanelForm extends JPanel implements ICheckoutView {
    private ICheckoutPresenter mPresenter;

    private JTable table1;
    private JTextField txtMemberId;
    private JTextField txtISBN;
    private JButton btnCheckout;
    private JPanel checkoutPanel;
    private final String[] columnNames = {"ISBN", "Title", "Checkout Date", "Due Date"};

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
        LocalDate today = LocalDate.now();
//        Object[][] data = {
//                {"Book 1B", 1, today},
//                {"Book 2", 2, today},
//                {"Book 3", 1, today},
//                {"Book 4", 3, today}
//        };

//        List<String> datas = new ArrayList<>();

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
    public void updateTable(HashMap<Integer, CheckoutRecord> cr) {

        Object[][] rows = new Object[][]{};

        DefaultTableModel tableModel = new DefaultTableModel(rows, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < cr.size(); i++) {
            CheckoutRecord record = cr.get(i);
            tableModel.addRow(record.toRowData());
        }

        table1.setModel(tableModel);
//        ((AbstractTableModel) table1.getModel()).fireTableDataChanged();
    }
}
