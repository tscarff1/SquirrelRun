package src2;

import java.awt.Image;

public class Toon extends Actor {
	private boolean invincible = false;
	
	private int moveSpeed = 13;
	private boolean jumping= false;
	private boolean riding = false;//Are we riding on the car?
	
	private short initJump = 60;
	private short initHyperJump = 75;
	private short gravity = 8;
	private final int LOWY = 340;
	
	private int ySpeed = 0;
	
	private boolean tripping = false;
	private final int TRIPTIME = 30;
	private final short HYPERTRIPTIME = 10;
	private int tripTimer = 0;
	
	private boolean hyper = false;
	
	private HitBox cBox;//The hitbox that will be used for collision testing
	private int cBufferX1 = 30;//left buffer
	private int cBufferX2 = 20;//right buffer
	private int cBufferY1 = 30;
	private int cBufferY2 = 10;
	
	//Creating the Images to be associated with Toon
	public static Image[] iRunning = Artist.registerImageArray(
			new String[]{"Toon_Running_1", "Toon_Running_2", "Toon_Running_3"});
	public static Image iTripping = Artist.registerImage("Toon_Tripping");
	public static Image[] iSitting = Artist.registerImageArray(
			new String[]{"ToonSitting_1"});
	
	public Toon(SquirrelRunPanel srp) {
		//Of the form:  x, y, width, height, (scalar), srp
		super(30, 580, 180, 120, .6, srp); 
		cBox = new HitBox(
				x() + cBufferX1, 
				y() + cBufferY1, 
				(int) ((getWidth() - cBufferX2) * scalar), 
				(int) ((getHeight() - cBufferY2) * scalar)
				);
	}

	//Getter Methods
	public boolean isHyper(){
		return hyper;
	}
	public boolean isJumping(){
		return jumping;
	}
	
	public boolean isTripping(){
		return tripping;
	}
	
	public boolean isRiding(){
		return riding;
	}

	@Override
	public HitBox getHitBox(){
		return cBox;
	}
	
	@Override
	public boolean collidesWith(Actor a){
		boolean collision = false;
		if(cBox.intersects(a.getHitBox()))
				collision = true;
		return collision;
	}
	
	private boolean invincible(){
		return invincible;
	}
	
	//Setter Methods
	public void setTripping(boolean b){
		if(!invincible){
			tripping = b;
			tripTimer = 0;
		}
	}
	
	//Methods to move Toon
	public void moveLeft(){
		if(hitbox.x() > 0){
			setX(x() - moveSpeed);
		}
	}
	
	public void moveRight(){
		if(x() + getWidth() < panel.getWidth() - 100){
			setX(x() + moveSpeed);
		}
	}
	
	//Just means toon is stuck moving back with the road
	public void moveTripping(){
		int triptime;
		if(!hyper)
			triptime = TRIPTIME;
		else
			triptime = HYPERTRIPTIME;
		if(tripTimer < triptime){
			updateX(-1 * panel.getRoadSpeed());
			if(x() < 0)
				setX(0);
			tripTimer++;
		}
		else{
			tripping = false;
		}
	}
	
	public void moveWithCar(EnemyCar carl){
		if(x() < panel.getWidth() - 100)
			updateX(carl.getSpeed());
		
		else{
			jump();
			riding = false;
			panel.stopRiding();
		}
	}
	
	public void jump(){
		if(!tripping){
			if(!hyper)
				ySpeed = initJump;
			else
				ySpeed = initHyperJump;
			
			jumping = true;
		}
		if(riding){
			riding = false;
		}
	}
	
	public void moveY(){
		if(y() < 370)
			ySpeed -= gravity;
		if(y() - ySpeed > LOWY){
			ySpeed = 0;
			setY(LOWY);
			jumping = false;
		}
		setY(y() - ySpeed);
	}
	
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
	
	public void setHyper(boolean h){
		hyper = h;
	}
	
	public void startRiding(){
		riding = true;
		jumping = false;
	}
}
