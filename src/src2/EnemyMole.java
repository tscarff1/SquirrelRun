package src2;

import java.awt.Image;

public class EnemyMole extends Actor{
	
	private boolean isPopping = false;
	private boolean isFalling = false;
	private boolean moving;
	private int minY = 300;
	
	private int ySpeed = 5;
	
	private int realHeight = 0; //Since he pops up by revealing himself
	
	private boolean hasTrippedToon = false; //To prevent tripping toon twice
	
	private HitBox cBox;//The hitbox that will be used for collision testing
	private int cBufferX = 10;
	private int cBufferY = 5;
	
	public static Image iMole = Artist.registerImage("MoleEnemy");
	public static Image iDirt = Artist.registerImage("MoleDirt");
	
	public EnemyMole(SquirrelRunPanel srp) {
		super(900, 430, 75, 75, 1.0, srp);
		cBox = new HitBox(x() + cBufferX, y() + cBufferY, getWidth() - cBufferX, getHeight() - cBufferY);
	}

	
	public void popUp(){	
		if(realHeight < getHeight() && isPopping){
			setY(y() - ySpeed);
			realHeight += ySpeed;
		}
		else
			isPopping = false;
	}
	
	private void checkPopUp(){
		if(panel.getToon().getHitBox().x2() + 200 > this.x() && y() > minY && !hasTrippedToon){
			isPopping = true;
		}
		else
			isPopping = false;
	}
		
	public void fall(){
		if(realHeight > 0){
			setY(y() + ySpeed);
			realHeight -= ySpeed;

		}
		else{
			isFalling = false;
		}
	}
	
	public void startMoving(){
		moving = true;	
	}
	
	public void move(){
		if(moving){
			setX(x() - panel.getRoadSpeed());
		}
		checkPopUp();
		if(isPopping){
			popUp();
		}
		
		if(isFalling)
			fall();
		
		if(x() < 0 - getWidth())
			reset();
	}
	
	public void reset(){
		moving = false;
		isFalling = false;
		setX(901);
		setY(430);
		realHeight = 0;
		hasTrippedToon = false;
		isPopping = false;
	}
	
	public void setTrippedToon(boolean b){
		hasTrippedToon = b;
		if(b)
			isFalling = true;
	}
	
	@Override
	public void setX(int nX){
		hitbox.update(nX, y());
		cBox.update(nX + cBufferX, y() + cBufferY);
	}
	@Override
	public void setY(int nY){
		hitbox.update(x(), nY);
		cBox.update(x() + cBufferX, nY + cBufferY);
	}
	
	//GETTER METHODS
	public int getRealHeight(){
		return realHeight;
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public boolean hasTrippedToon(){
		return hasTrippedToon;
	}
	
	@Override
	public HitBox getHitBox(){
		return cBox;
	}
}
