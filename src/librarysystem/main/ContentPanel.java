package librarysystem.main;
import librarysystem.checkout.CheckoutPanel;

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
        JPanel checkoutPanel = createPanel(new CheckoutPanel());
        JPanel memberPanel = createPanel("Member Screen", Color.PINK);
        JPanel helpPanel = createPanel("Help Screen", Color.YELLOW);

        // Add the panels to the card layout
        add(loginPanel, "Login");
        add(checkoutPanel, "Checkout");
        add(memberPanel, "Member");
        add(helpPanel, "Help");
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