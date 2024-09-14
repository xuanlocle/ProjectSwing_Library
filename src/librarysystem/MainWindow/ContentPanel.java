package librarysystem.MainWindow;
import librarysystem.*;
import librarysystem.CheckoutPanelForm;

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
        homePanel = new HomeScreen(Main.userService).getPanel();
        add(homePanel, "Home");
    }

    private void initLoginPanel() {
        loginPanel = new LoginScreen(Main.userService).getPanel();
        add(loginPanel, "Login");
    }

    private void initCheckoutPanel() {
        checkoutPanel = new CheckoutPanelForm(Main.checkoutService).getPanel();
        add(checkoutPanel, "Checkout");
    }

    private void initMemberPanel() {
        memberPanel = new MembersScreen(Main.userMgmtService).getPanel();
        add(memberPanel, "Member");
    }

    private void initBookPage() {
        bookPanel = new BookMgmtView(Main.bookService);
        add(bookPanel.getMainPanel(), "ManageBook");
    }

    // Method to switch to the requested panel
    public void showPanel(String panelName) {
        if (panelName.equals("ManageBook")) {
            bookPanel.reload();
        }
        cardLayout.show(this, panelName);
    }
}