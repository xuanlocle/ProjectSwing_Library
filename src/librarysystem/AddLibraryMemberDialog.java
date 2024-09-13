package librarysystem;

import business.ControllerInterface;

import javax.swing.*;
import java.awt.event.*;

public class AddLibraryMemberDialog extends JDialog {
    private ControllerInterface controller;
    private IDialogEventListenter listenter;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField streetTextField;
    private JComboBox stateComboBox;
    private JTextField phoneTextField;
    private JTextField cityTextField;
    private JTextField zipTextField;

    public AddLibraryMemberDialog(IDialogEventListenter listenter, ControllerInterface controller) {
        this.listenter = listenter;
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600, 400);

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
    }

    private void onOK() {
        // add your code here
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String street = streetTextField.getText();
        String state = stateComboBox.getSelectedItem().toString();
        String phone = phoneTextField.getText();
        String city = cityTextField.getText();
        String zip = zipTextField.getText();
        controller.addLibraryMember(firstName, lastName, phone, street, state, city, zip);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
