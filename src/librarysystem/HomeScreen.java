package librarysystem;

import business.ControllerInterface;
import business.IAuthStateListener;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen implements IAuthStateListener {
    private ControllerInterface controller;
    private JPanel panel;
    private JLabel welcomeLabel;
    private JLabel roleLabel;
    private JButton logOutButton;
    private Auth currentAuth;

    public HomeScreen(ControllerInterface controller) {
        this.controller = controller;

        SystemController.registerAuthStateListener(this);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogOut();
            }
        });
    }

    private void doLogOut() {
        this.controller.logout();
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void onLogin(Auth auth) {
        currentAuth = auth;
        this.roleLabel.setText("You are logged in as " + currentAuth);
        panel.updateUI();
    }

    @Override
    public void onLogout() {
        currentAuth = null;
        panel.updateUI();
    }
}
