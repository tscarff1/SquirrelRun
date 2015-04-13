package src2;
import java.awt.Image;

public class EnemyBaseball extends Actor {

	public static Image img = Artist.registerImage("baseball");
	private int dX = 8, dY = 0;
	private final int GRAVITY = 2;
	private int JUMPSPEED = 30;
	private final int LOWY = 380;
	private int rotation = 0;
	private final int RSPEED = -3;
	private boolean moving = false;
	
	public EnemyBaseball(SquirrelRunPanel srp) {
		super(SquirrelRunFrame.WIDTH, 300, img.getWidth(null), img.getHeight(null), 1.0, srp);
	}
	
	public void move(){
		if(moving){
			rotation += RSPEED;
			dY -= GRAVITY;
			updateX(-1 * dX);
			updateY(-1 * dY);
			if(y() > LOWY){
				setY(LOWY);
				dY = JUMPSPEED;
			}
			
			if(x() + width < 0){
				setX(SquirrelRunFrame.WIDTH + 10);
				rotation = 0;
				moving = false;
			}
		}
	}

	public void startMoving(){
		moving = true;
	}
	
	public int getRotation(){
		return rotation;
	}
	
	public boolean getMoving(){
		return moving;
	}
}
