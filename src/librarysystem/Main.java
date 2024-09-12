package librarysystem;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

public class Main {

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
		EventQueue.invokeLater(() -> {
			MainScreen.INSTANCE.setTitle("LibBook");
			MainScreen.INSTANCE.init();
			centerFrameOnDesktop(MainScreen.INSTANCE);

//			DataAccess dataAccess = new DataAccessFacade();
//			ControllerInterface controller = new SystemController(dataAccess);
//
//			BookMgmtView mgmtView = new BookMgmtView(controller);
//			mgmtView.setVisible(true);
//			mgmtView.setLocationRelativeTo(null);
		});
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