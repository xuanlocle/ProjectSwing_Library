package librarysystem.main;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import librarysystem.BookMgmtView;
import librarysystem.LoginScreen;
import librarysystem.MembersScreen;
import librarysystem.checkout.CheckoutPanelForm;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;

    public ContentPanel() {
        // Initial panel setup
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create different panels for each screen
        JPanel loginPanel = createPanel("Login Screen", Color.LIGHT_GRAY);
        JPanel memberPanel = createPanel("Member Screen", Color.PINK);
        JPanel helpPanel = createPanel("Help Screen", Color.YELLOW);

        // Add the panels to the card layout
        initLoginPanel();
        initCheckoutPanel();
        initMemberPanel();
        add(helpPanel, "Help");
        initBookPage();
    }

    private void initLoginPanel() {
        DataAccess dataAccess = new DataAccessFacade();
        ControllerInterface controller = new SystemController(dataAccess);
        JPanel loginPanel = new LoginScreen(controller).getPanel();
        add(loginPanel, "Login");
    }

    private void initCheckoutPanel() {
        JPanel checkoutPanel = new CheckoutPanelForm();
        add(checkoutPanel, "Checkout");
    }

    private void initMemberPanel() {
        DataAccess dataAccess = new DataAccessFacade();
        ControllerInterface controller = new SystemController(dataAccess);
        JPanel memberPanel = new MembersScreen(controller).getPanel();
        add(memberPanel, "Member");
    }

    private void initBookPage() {
        DataAccess dataAccess = new DataAccessFacade();
        ControllerInterface controller = new SystemController(dataAccess);

        BookMgmtView bookPanel = new BookMgmtView(controller);
        add(bookPanel, "ManageBook");
    }

    // Method to switch to the requested panel
    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
    }

    // Helper method to create a simple panel with a label
    private JPanel createPanel(String text, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // Helper method to create a simple panel with a label
    private JPanel createPanel(JPanel panel) {
//        panel.setBackground(backgroundColor);
        panel.setLayout(new BorderLayout());
//        JLabel label = new JLabel(, SwingConstants.CENTER);
//        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}