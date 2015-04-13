package src2;

import java.awt.Image;

public class Cloud extends Actor{
	private int type;
	private int moveSpeed;
	public static Image[] iClouds = Artist.registerImageArray(new String[]{"Cloud1", "Cloud2", "Cloud3"});
	
	public Cloud(int t, SquirrelRunPanel srp) {
		super(901, 0, 200, 150, 1.0, srp);
		type = t;
		moveSpeed = 2 + (int) (Math.random() * 5);
		setY((int)(Math.random() * 200));
	}

	public int getType(){
		return type;
	}
	
	public void move(){
		setX(x() - moveSpeed);
	}
}
