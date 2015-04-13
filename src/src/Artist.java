package src;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Artist {
	private SquirrelRunBoard board;
	
	private String gFolder = "Graphics/";
	
	private String ToonRunning[] = {"Toon Running_1", "Toon Running_2", "Toon Running_3"};
	private Image[] ToonImages = new Image[3];
	
	
	public Artist(SquirrelRunBoard srb){
		board = srb;
	}
	
	//Convert the strings above into useable images
	public void registerIcons(){
	//This section creates the images for toon running	
		ImageIcon[] ii = new ImageIcon[3];
		for(int i = 0; i < 3; i++){
			ii[i] = new ImageIcon(this.getClass().getResource(gFolder + ToonRunning[i] + ".png"));
			ToonImages[i] = ii[i].getImage();
		}
	}
	
	//********** Drawing Methods Begin Here ************
	public void drawToonRunning(Graphics g, int par2, int x, int y){
		//par2 is the place in the image array to draw
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(ToonImages[par2], x, y, board);
		System.out.println("HELLO");
	}
}
