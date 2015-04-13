package src2;

import java.awt.Image;

public class EnemyCrow extends Actor {
	private HitBox cBox;
	private int cBufferX1 = 25;
	private int cBufferX2 = 45;
	private int cBufferY1 = 25;
	private int cBufferY2 = 35;
	
	private int crowPos = 0;
	
	private int xSpeed = 9;
	private int flightPattern;//decides which equation will be used to make the crow fly
	private int t;//time. used in the parametric equations which calculate the crow's position
	private int imagePos = 0;
	
	private boolean moving = false;
	
	public static Image[] iCrow1 = Artist.registerImageArray(new String[]{"Crow_1", "Crow_2"});
	public static Image[] iCrow = Artist.registerImageArray(new String[]{"Crow_1_1", "Crow_2_1"});
	
	public EnemyCrow(SquirrelRunPanel srp, int pattern){
		super(900, 0, 100, 100, 1.0, srp);
		cBox = new HitBox(x() + cBufferX1, y() + cBufferY1, 
				(int) ((getWidth() -  cBufferX2) * scalar), (int) ((getHeight() - cBufferY2) * scalar)
				);
		flightPattern = pattern;
		t = 0;
		imagePos = 0;
		
		if(flightPattern != 1)
			setY((int) (Math.random() * 500));
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public void move(){
		int nX = 901 - t;
		int nY = 0;
		switch(flightPattern){
		
			//flight pattern 1 is a parabola
			case 1:
				nY = (int) (-.0007 * Math.pow(2 * (901 - nX)-800, 2) + 330);
				break;
			//default flight pattern is a line
			default: 
				nY = 250;
				break;
		}
		setX(nX);
		setY(nY);
		t += xSpeed;
		imagePos++;
		if(imagePos%5 == 0)
			crowPos = t%2;
	}
	
	public int getCrowPos(){
		return crowPos;
	}
	
	public void reset(){
		setX(901);
		setY(0);
		t = 0;
		flightPattern = -1;
	}
	
	//Overridden methods
	@Override
	public void setX(int nX){
		hitbox.update(nX, y());
		cBox.update(nX + cBufferX1, y() + cBufferY1);
	}
	@Override
	public void setY(int nY){
		hitbox.update(x(), nY);
		cBox.update(x() + cBufferX1, nY + cBufferY1);
	}
	
	@Override
	public HitBox getHitBox(){
		return cBox;
	}
}
