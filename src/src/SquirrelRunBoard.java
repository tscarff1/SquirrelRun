package src;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class SquirrelRunBoard  extends JPanel implements ActionListener{
	private Artist artist;
	private Timer timer;
	public SquirrelRunBoard(SquirrelRunApplet sra){
		artist = new Artist(this);
		timer = new Timer(50, this);
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
	}
	
	
	public void paint(Graphics g){
		artist.drawToonRunning(g, 0, 50, 50);

	}
	
}
