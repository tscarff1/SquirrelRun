package src2;

import javax.swing.JFrame;


public class SquirrelRunFrame extends JFrame {
	public static final int WIDTH = 900;
	public static final int HEIGHT = 500;
	
	public SquirrelRunFrame(){
		add (new SquirrelRunPanel(this));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Squirrel Run");
		
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String args[]){
		new SquirrelRunFrame();
	}
}
