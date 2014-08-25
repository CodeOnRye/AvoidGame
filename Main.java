import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;



@SuppressWarnings("unused")
public class Main {

	public static void main(String[] args) {
		JFrame mainWindow = new JFrame();
		mainWindow.setSize(500, 500);

		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		Container content = mainWindow.getContentPane();

		Game gamePad = new Game();

		content.add(gamePad, BorderLayout.CENTER);

		mainWindow.setVisible(true);


	}

}
