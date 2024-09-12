package librarysystem.main;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton btnLogin;
    private JButton btnCheckout;
    private JButton btnMember;
    private JButton btnHelp;

    public MenuPanel() {
        this.setLayout(new GridLayout(4, 1)); // 4 buttons in a column layout

        btnLogin = new JButton("Login");
        btnCheckout = new JButton("Checkout");
        btnMember = new JButton("Member");
        btnHelp = new JButton("Help");

        this.add(btnLogin);
        this.add(btnCheckout);
        this.add(btnMember);
        this.add(btnHelp);

    }

    public void initListener(ContentPanel rightPanel) {

        btnLogin.addActionListener(e -> rightPanel.showPanel("Login"));
        btnCheckout.addActionListener(e -> rightPanel.showPanel("Checkout"));
        btnMember.addActionListener(e -> rightPanel.showPanel("Member"));
        btnHelp.addActionListener(e -> rightPanel.showPanel("Help"));

    }
}
