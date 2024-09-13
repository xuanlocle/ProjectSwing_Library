package librarysystem;

import business.ControllerInterface;
import business.IValidator;

import javax.swing.*;
import java.awt.event.*;

public class AddLibraryMemberDialog extends JDialog {
    private ControllerInterface controller;
    private IValidator validator;

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
    private JTextArea errorMessagesTextArea;

    public AddLibraryMemberDialog(ControllerInterface controller, IValidator validator) {
        this.controller = controller;
        this.validator = validator;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600, 400);

        errorMessagesTextArea.setVisible(false);

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

    private Boolean validateFields(String firstName, String lastName, String street, String city, String state, String zip, String phone) {
        String errorMessages = "";
        errorMessagesTextArea.setText("");
        errorMessagesTextArea.setVisible(false);
        try {
            validator.validateFirstName(firstName);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        try {
            validator.validateLastName(lastName);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        try {
            validator.validateStreet(street);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        try {
            validator.validateCity(city);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        try {
            validator.validateState(state);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        try {
            validator.validateZipCode(zip);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        try {
            validator.validatePhoneNumber(phone);
        } catch (Exception e) {
            errorMessages += "- " + e.getMessage() + " \n";
        }
        if (!errorMessages.isEmpty()) {
            errorMessagesTextArea.setText(errorMessages);
            errorMessagesTextArea.setVisible(true);
            return false;
        }
        return true;
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
        if (!validateFields(firstName, lastName, street, city, state, zip, phone)) {
            return;
        }
        controller.addLibraryMember(firstName, lastName, phone, street, state, city, zip);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
