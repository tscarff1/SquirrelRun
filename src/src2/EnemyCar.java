package src2;

import java.awt.Image;

public class EnemyCar extends Actor {
	private HitBox goodBox;
	private HitBox badBox;
	private int gBoxOffsetY = 80;
	private int bBoxOffsetY = 90;
	private int imagePos = 0;
	private final int MAXIMAGEPOS = 20;
	public static Image[] iCar = Artist.registerImageArray(new String[]{"truck_0", "truck_1"});
	//When the car reaches a certain x value, I want it to pause. These help with that
	private int moveDelay = 0;
	private int MAXMOVEDELAY = 100;
	private int XDELAY = -200;
	
	private boolean collided = false;
	private boolean moving = false;
	
	//Because I want the car to move faster when it gets past the pause point
	private int[] speed = new int[]{2, 5};	

	public EnemyCar(SquirrelRunPanel srp) {
		super(-1 * EnemyCar.iCar[0].getWidth(null) - 200, 205, EnemyCar.iCar[0].getWidth(null), EnemyCar.iCar[0].getHeight(null), 1.8, srp);
		goodBox = new HitBox(x() + 10, y() + gBoxOffsetY, width - 20, 10);	
		badBox = new HitBox(x() + 2, y() + bBoxOffsetY, width-4, 80);
	}

	public void move(){	
		if(x() > SquirrelRunFrame.WIDTH)
			stopMoving();
		if(moving){
			//imagePos is to slow the animation
			imagePos++;
			if(imagePos >= MAXIMAGEPOS)
				imagePos = 0;

			if(x() < XDELAY){
				setX(x() + speed[0]);
				goodBox.update(x() + speed[0] + 10, y() + gBoxOffsetY);
				badBox.update(x() + speed[0] + 2, y() + bBoxOffsetY);
			}

			if(x() >= XDELAY){
				if(moveDelay < MAXMOVEDELAY)
					moveDelay++;
				else
					setX(x() + speed[1]);
				goodBox.update(x() + speed[1] + 10, y() + gBoxOffsetY);
				badBox.update(x() + speed[1] + 2, y() + bBoxOffsetY);
			}
		}
	}
	
	public int getMovePos(){
		return imagePos/10;
	}
	
	public HitBox getGoodBox(){
		return goodBox;
	}
	
	public HitBox getBadBox(){
		return badBox;
	}
	
	public boolean collidesWith(Actor TomCruise, int h){//TomCruise is because it's 2am right now, h is which hitbox
		switch(h){//0 is bad, 1 is good
			case 0:
				return (!collided && super.collidesWith(TomCruise));
			case 1:
					if(goodBox.intersects(TomCruise.getHitBox()) && !collided){
						collided = true;
						return true;
					}
				return false;
		}
		
		return false;
	}
	
	public int getSpeed(){
		if(x() < XDELAY)
			return speed[0];
		else{
			if(moveDelay < MAXMOVEDELAY)	
				return 0;
			else
				return speed[1];
		}
		
	}
	
	@Override
	public boolean collidesWith(Actor WillFerrel){
		if(!collided && badBox.intersects(WillFerrel.getHitBox())){
			collided = true;
			return true;
		}
		else 
			return false;
				
	}
	
	public void resetCollided(){
		collided = false;
	}
	
	public void ignoreMoveDelay(){
		moveDelay = 9001;//Gotta make it big, so might as well do this
	}
	
	public void startMoving(){
		moving = true;
	}
	
	public void stopMoving(){
		moving = false;
		setX(-1 * EnemyCar.iCar[0].getWidth(null) - 200);
		goodBox.update(x(), y() + gBoxOffsetY);
		badBox.update(x(), y() + bBoxOffsetY);
		collided = false;
	}
	
	public boolean getMoving(){
		return moving;
	}
}
