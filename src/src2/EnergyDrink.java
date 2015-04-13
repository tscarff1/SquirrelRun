package src2;

import java.awt.Image;

public class EnergyDrink extends Actor{ 
	
	private short minSpeed = 12;
	private short maxSpeed = 4;
	private int moveSpeed;
	private boolean moving;
	
	//Min and max heights where drinks can appear
	private int minY;
	private int maxY;
	
	public static Image iDrink = Artist.registerImage("EnergyDrink");
	
	public EnergyDrink(SquirrelRunPanel srp) {
		super(901, 0, iDrink.getWidth(null), iDrink.getHeight(null), .8, srp);
		
		minY = 200;
		maxY = 440 - getHeight();
	
		moveSpeed = (int) (Math.random() * (maxSpeed- minSpeed)) + minSpeed;
	}

	public void move(){
		if(moving)
			updateX(-1 * moveSpeed);
		if(x() < - getWidth())
			reset();
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public void startMoving(){
		moving = true;
		int tempY = (int) (minY + Math.random() * (maxY - minY));
		setY(tempY);
	}
	
	public void reset(){
		moving = false;
		setX(901);
	}
	
}
