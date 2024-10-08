package librarysystem;

import business.logic.*;
import business.logic.impl.BookService;
import business.logic.impl.CheckoutService;
import business.logic.impl.UserMgmtService;
import business.logic.impl.UserService;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import librarysystem.MainWindow.MainScreen;

import javax.swing.*;
import java.awt.Component;
import java.awt.Toolkit;

import static util.ValidationHelper.getResourceDir;

public class Main {
    public static final DataAccess dataAccess = new DataAccessFacade();
    public static final IBook bookService = new BookService(dataAccess);
    public static final ICheckout checkoutService = new CheckoutService(dataAccess);
    public static final IUser userService = new UserService(dataAccess);
    public static final IUserMgmt userMgmtService = new UserMgmtService(dataAccess);

    private static void setWindowsStyle() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        setWindowsStyle();
        MainScreen frame = new MainScreen();
        frame.setIconImage(new ImageIcon(getResourceDir() + "/image/book.png").getImage());
        frame.setVisible(true);
        centerFrameOnDesktop(frame);
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }
}
