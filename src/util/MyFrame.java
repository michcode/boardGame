package util;

import java.io.IOException;

import javax.swing.*;

public class MyFrame extends JPanel{

	public static void main(String[] args) {
		JFrame frame = new JFrame("My dropping-disk game");				
		//terminate the program when the frame is closed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		//enter the frame on the screen
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
		frame.setContentPane(new DroppingDisk(6,7));
		frame.pack(); //refer to "Java notes" for more details
		
	}

}
