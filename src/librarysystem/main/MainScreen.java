package librarysystem.main;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    private static MenuPanel menuPanel;
    private static ContentPanel rightPanel;


    public MainScreen() {
        setTitle("Split Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        menuPanel = new MenuPanel();

        rightPanel = new ContentPanel();
        rightPanel.setBackground(Color.WHITE); // Default color for the right panel

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, rightPanel);
        splitPane.setDividerLocation(150); // Set the initial divider location
        splitPane.setResizeWeight(0.25); // Left panel resizes less

        menuPanel.initListener(rightPanel);
        this.add(splitPane);
        this.setVisible(true);
    }
}