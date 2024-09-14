package librarysystem;

import business.exception.LoginException;
import business.logic.IUser;

import javax.swing.*;

public class LoginScreen {

    private JPanel panel;
    private JTextField IDTextField;
    private JButton loginButton;
    private JPasswordField passwordField;
    private IUser controller;

    public LoginScreen(IUser controller) {
        this.controller = controller;

        this.loginButton.addActionListener(e -> {
            String ID = IDTextField.getText();
            String password = passwordField.getText();
            try {
                this.controller.login(ID, password);
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage());
            } finally {
                // Reset password
                passwordField.setText("");
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
