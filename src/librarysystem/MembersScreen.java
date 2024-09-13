package librarysystem;

import business.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MembersScreen implements IDialogEventListenter {
    private JPanel membersPanel;
    private JTextField memberIDTextField;
    private JButton searchButton;
    private JButton addNewMemberButton;
    private JTable membersTable;
    private ControllerInterface controller;
    private final String[] MEMBER_COLUMNS = {"Member ID", "First Name", "Last Name", "Phone", "Address"};
    private List<LibraryMember> memberList = new ArrayList<>();

    public MembersScreen(ControllerInterface controller) {
        this.controller = controller;
        initMembersTable();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initMembersTable();
            }
        });
        addNewMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLibraryMemberBtnPressed();
            }
        });
    }

    private void addLibraryMemberBtnPressed() {
        IValidator validator = new Validator();
        JDialog dialog = new AddLibraryMemberDialog(this, controller, validator);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        initMembersTable();
    }

    public JPanel getPanel() {
        return this.membersPanel;
    }

    private void getMembers() {
        this.memberList = this.controller.getLibraryMembers();
    }

    private static Object[] getRow(LibraryMember member) {
        String memberID = member.getMemberId();
        String firstName = member.getFirstName();
        String lastName = member.getLastName();
        String phone = member.getTelephone();
        Address address = member.getAddress();
        String addressString = address.getStreet() + ", " + address.getCity() + ", " + address.getState() + ", " + address.getZip();
        return new Object[] {
                memberID,
                firstName,
                lastName,
                phone,
                addressString
        };
    }

    private void initMembersTable() {
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, MEMBER_COLUMNS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are non-editable
                return false;
            }
        };
        getMembers();
        String filteredMemberId = memberIDTextField.getText();
        for (LibraryMember member : this.memberList) {
            if (filteredMemberId.isEmpty() || member.getMemberId().equals(filteredMemberId)) {
                Object[] row = getRow(member);
                model.addRow(row);
            }
        }
        membersTable.setModel(model);
    }

    @Override
    public void onOKButtonClicked() {
        initMembersTable();
    }

    @Override
    public void onCancelButtonClicked() {

    }
}
