package librarysystem.MainWindow;

import librarysystem.Main;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private static MenuPanel menuPanel;
    private static ContentPanel rightPanel;

    public MainScreen() {
        setTitle("Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 700);
        menuPanel = new MenuPanel(Main.userService);

        rightPanel = new ContentPanel();
        rightPanel.setBackground(Color.WHITE); // Default color for the right panel

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, rightPanel);
        splitPane.setDividerLocation(150); // Set the initial divider location
        splitPane.setResizeWeight(0.25); // Left panel resizes less

        menuPanel.initListener(rightPanel);
        add(splitPane);
        setVisible(true);
    }
}