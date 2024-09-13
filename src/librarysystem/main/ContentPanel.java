package librarysystem.main;
import librarysystem.*;
import librarysystem.checkout.CheckoutPanelForm;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel homePanel;
    private JPanel loginPanel;
    private JPanel checkoutPanel;
    private JPanel memberPanel;
    private BookMgmtView bookPanel;

    public ContentPanel() {
        // Initial panel setup
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Add the panels to the card layout
        initLoginPanel();
        initHomePanel();
        initCheckoutPanel();
        initMemberPanel();
        initBookPage();
    }

    private void initHomePanel() {
        homePanel = new HomeScreen(Main.controller).getPanel();
        add(homePanel, "Home");
    }

    private void initLoginPanel() {
        loginPanel = new LoginScreen(Main.controller).getPanel();
        add(loginPanel, "Login");
    }

    private void initCheckoutPanel() {
        checkoutPanel = new CheckoutPanelForm().getPanel();
        add(checkoutPanel, "Checkout");
    }

    private void initMemberPanel() {
        memberPanel = new MembersScreen(Main.controller).getPanel();
        add(memberPanel, "Member");
    }

    private void initBookPage() {
        bookPanel = new BookMgmtView(Main.controller);
        add(bookPanel, "ManageBook");
    }

    // Method to switch to the requested panel
    public void showPanel(String panelName) {
        if (panelName.equals("ManageBook")) {
            bookPanel.reload();
        }
        cardLayout.show(this, panelName);
    }
}