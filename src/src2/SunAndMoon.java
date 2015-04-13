package src2;

import java.awt.Image;

public class SunAndMoon {
	public static Image iSun = Artist.registerImage("sun");
	public static Image iMoon = Artist.registerImage("moon");
	
	private final int CENTERX = SquirrelRunFrame.WIDTH/2;
	private final int CENTERY = SquirrelRunFrame.HEIGHT;
	private final double AV = 0.3; //Angular velocity. Cuz Im flippin fancy.
	private final int RADIUS = SquirrelRunFrame.WIDTH/2;
	
	private int sX, sY, mX, mY;
	private double angle = 0;
	
	public SunAndMoon(){
		setCoords();

	}
	
	public void move(){
		//This if else isnt completely needed, but keeps things a little nicer
		if(angle > -360)
			angle -= AV;
		else
			angle = 0;
		
		setCoords();
	}
	
	private void setCoords(){
		sX = CENTERX + (int) (RADIUS * Math.cos(angle * Math.PI/180));
		sY = CENTERY + (int) (RADIUS * Math.sin(angle * Math.PI/180));
		mX = CENTERX - (int) (RADIUS * Math.cos(angle * Math.PI/180));
		mY = CENTERY - (int) (RADIUS * Math.sin(angle * Math.PI/180));
	}
	
	public int sX(){
		return sX;
	}
	
	public int sY(){
		return sY;
	}
	
	public int mX(){
		return mX;
	}
	
	public int mY(){
		return mY;
	}
	
	//To be used in artist to determine how dark to draw the sky
	public int getDayNightScalar(){
		return (int) (((double) sY/RADIUS) * 100);
	}

}