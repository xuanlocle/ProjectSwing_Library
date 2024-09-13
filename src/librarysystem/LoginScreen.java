package librarysystem;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;

import javax.swing.*;

public class LoginScreen {

    private JPanel panel;
    private JTextField IDTextField;
    private JButton loginButton;
    private JPasswordField passwordField;
    private ControllerInterface controller;

    public LoginScreen(ControllerInterface controller) {
        this.controller = controller;

        this.loginButton.addActionListener(e -> {
            String ID = IDTextField.getText();
            String password = passwordField.getText();
            SystemController sc = new SystemController();
            try {
                sc.login(ID, password);
                JOptionPane.showMessageDialog(panel,"Successful Login");
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage());
                // Reset password
                passwordField.setText("");
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
