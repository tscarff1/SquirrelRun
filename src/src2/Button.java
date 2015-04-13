package src2;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Button {
	public static Image iHighScore = Artist.registerImage("ButtonHighScore");
	public static Image iInstructions = Artist.registerImage("ButtonInstructions");
	public static Image iStart = Artist.registerImage("ButtonStart");
	public static Image iTitle = Artist.registerImage("ButtonTitle");
	
	private Image image;
	
	private String text; //used for filename
	private SquirrelRunPanel panel;
	
	private int rotation;// < 0 means tilted left, > 0 means tilted right
	private int rotating; //will equal 1 of the following
		private final int LEFT = -1;
		private final int NONE = 0;
		private final int RIGHT = 1;
	
		
	private HitBox h;
	private final int WIDTH = 150;
	private final int HEIGHT = 48;
	
	public static final int DROTATE = 3;
	
	private final int allowedPress;//Gamemode in which the button is allowed to be pressed
	//g in both constructors -> allowedPress
	public Button(SquirrelRunPanel srp, String t, int g){
		init(srp, t);
		allowedPress = g;
		h = new HitBox(0, 0, image.getWidth(null), image.getHeight(null));
	}
	
	public Button (SquirrelRunPanel srp, String t, int x, int y, int g){
		init(srp, t);
		allowedPress = g;
		h = new HitBox(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	private void init(SquirrelRunPanel srp, String t){
		text = t;
		panel = srp;
		
		rotation = 0;
		rotating = 0;
		
		registerImage();
	}
	
	private boolean registerImage(){
		switch(text.toLowerCase()){
			case "highscore":
				image = iHighScore;
				return true;
			case "instructions":
				image = iInstructions;
				return true;
			case "start":
				image = iStart;
				return true;
			case "title":
				image = iTitle;
				return true;
		}
		return false;
	}
	
	public String getText(){
		return text;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getRotation(){
		return rotation;
	}
	
	public HitBox getHitBox(){
		return h;
	}
	public void rotate(){
		//Artist.msg(this.getClass(), "ROTATING");
		int speed = 1;
		if(image == iHighScore)
			speed = 2;
		
		if(rotating == RIGHT){
			if(rotation < 5)
				rotation += speed;
			else
				rotating = LEFT;
		}
		
		else if(rotating == LEFT){
			if(rotation > -5)
				rotation -= speed;
			else
				rotating = RIGHT;
		}
		else if(rotating == NONE){
			if(rotation < 0)
				rotation++;
			if(rotation > 0)
				rotation--;
		}
	}//end method
	
	public void startRotation(){
		//Artist.msg(this.getClass(), "starting rotation");
		if(rotating == NONE)
			rotating = RIGHT;
	}
	
	public void stopRotation(){
		rotating = NONE;
	}
	
	public boolean isRotating(){
		if(rotation == 0)
			return false;
		else
			return true;
	}
	
	//return the gamemode in which this button is allowed to be pressed
	public int getAllowedPress(){
		return allowedPress;
	}
}
