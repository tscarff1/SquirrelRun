package src2;

import java.awt.Image;

public class Acorn extends Actor{
	
	private short maxSpeed = 10;
	private short minSpeed = 4;
	private int moveSpeed;
	private boolean moving = false;
	
	//Min and max heights where acorns can appear
	private short minY;
	private int maxY;
	
	public static Image iAcorn = Artist.registerImage("acorn");
	
	public Acorn(SquirrelRunPanel srp){
		super(901, 0, iAcorn.getWidth(null), iAcorn.getHeight(null), .8, .015, srp);
		
		minY = 200;
		maxY = 425 - getHeight();
		
		moveSpeed = (int) (Math.random() * (maxSpeed - minSpeed)) + minSpeed; 
	}
	
	public void move(){
		//Artist.msg(this.getClass(),"moving");
		if(moving)
				updateX(-1 *  moveSpeed);
		if(x() < 0 - getWidth())
			reset();
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public void startMoving(){
		moving = true;
		int tempY =(int) (minY + Math.random() * (maxY - minY));
		setY(tempY);
	}
	
	public void reset(){
		moving = false;
		setX(901);
	}
}
