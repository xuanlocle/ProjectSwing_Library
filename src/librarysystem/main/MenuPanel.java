package librarysystem.main;

import business.IAuthStateListener;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel implements IAuthStateListener {
    private JButton btnLogin;
    private JButton btnHome;
    private JButton btnBook;
    private JButton btnCheckout;
    private JButton btnMember;

    public MenuPanel() {
        this.setLayout(new GridLayout(4, 1)); // 5 buttons in a column layout

        btnLogin = new JButton("Login");
        btnHome = new JButton("Home");
        btnBook = new JButton("Manage Book");
        btnCheckout = new JButton("Checkout");
        btnMember = new JButton("Member");

        initMenu();
//        this.add(btnLogin);
//        this.add(btnBook);
//        this.add(btnCheckout);
//        this.add(btnMember);

        SystemController.registerAuthStateListener(this);
    }

    private void initMenu() {
        //default not login
        this.add(btnLogin);
    }

    private void updateMenu(Auth auth) {
        //by role
        if (auth == null) {
            this.removeAll();
            this.add(btnLogin);
            return;
        }
        switch (auth) {
            case LIBRARIAN:
                this.add(btnCheckout);
                break;
            case ADMIN:
                this.add(btnBook);
                this.add(btnMember);
                break;
            case BOTH:
                this.add(btnBook);
                this.add(btnCheckout);
                this.add(btnMember);
                break;
        }
    }

    public void initListener(ContentPanel rightPanel) {

        btnLogin.addActionListener(e -> rightPanel.showPanel("Login"));
        btnHome.addActionListener(e -> rightPanel.showPanel("Home"));
        btnBook.addActionListener(e -> rightPanel.showPanel("ManageBook"));
        btnCheckout.addActionListener(e -> rightPanel.showPanel("Checkout"));
        btnMember.addActionListener(e -> rightPanel.showPanel("Member"));

    }

    @Override
    public void onLogin(Auth auth) {
        this.remove(btnLogin);
        this.add(btnHome, 0);
        btnHome.doClick();
        updateMenu(auth);
        this.updateUI();
    }

    @Override
    public void onLogout() {
        this.remove(btnHome);
        this.add(btnLogin, 0);
        btnLogin.doClick();
        updateMenu(null);
        this.updateUI();
    }
}
