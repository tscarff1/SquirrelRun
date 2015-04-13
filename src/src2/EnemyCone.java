package src2;

public class EnemyCone extends Actor{
	private HitBox cBox;//The hitbox that will be used for collision testing
	private int cBufferX = 45;
	private int cBufferYT = 5;
	private int cBufferYB = 95;
	
	int moveSpeed;
	public EnemyCone(SquirrelRunPanel srp){
		super(901, 240, 180, 203, .9, srp);
		
		moveSpeed = panel.getRoadSpeed();
		
		cBox = new HitBox(x() + cBufferX, y() + cBufferYT, getWidth() - cBufferX, getHeight() - cBufferYB);
	}
	
	public void move(){
		setX(x() - moveSpeed);
	}
	
	@Override
	public void setX(int nX){
		hitbox.update(nX, y());
		cBox.update(nX + cBufferX, y() + cBufferYT);
	}
	@Override
	public void setY(int nY){
		hitbox.update(x(), nY);
		cBox.update(x() + cBufferX, nY + cBufferYT);
	}
	
	public boolean collidesWith(Actor CharlieSheen){
		if(this.cBox.intersects(CharlieSheen.getHitBox()))
			return true;
		return false;
	}
	
	public HitBox getHitBox(){
		return cBox;
	}
	

}
