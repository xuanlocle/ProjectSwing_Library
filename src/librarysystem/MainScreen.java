package librarysystem;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccessFacade;

import javax.swing.*;

public class MainScreen extends JFrame {
    private JPanel panelMain;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton checkoutButton;
    private JButton bookButton;
    private JButton memberButton;
    private JPanel mainPanel;
    private JPanel menuPanel;
    public final static MainScreen INSTANCE = new MainScreen();


    public JPanel getPanelMain() {
        return panelMain;
    }

    void init(){
        ControllerInterface controller = new SystemController(new DataAccessFacade());
        this.setContentPane(new MembersScreen(controller).getPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800,600);

        initListener();
    }

    private void initListener() {
        memberButton.addActionListener(e -> {
        });
    }
}