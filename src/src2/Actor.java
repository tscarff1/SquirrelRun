package src2;

public abstract class Actor {
	
	public static final int BG = 0;
	public static final int NEUTRAL = 1;
	public static final int ENEMY = 2;
	
	private int type;
	
	protected HitBox hitbox;
	 
	protected int width;
	protected int height;
	
	protected double scalar = 1;
	protected double appearChance;
	
	protected SquirrelRunPanel panel;
	
	private boolean fisting = true;
	private boolean enjoyingIt = false;

	//Constructor containing x, y, width, height, the size scalar, and the panel
	public Actor(int x, int y, int width, int height, double scalar, SquirrelRunPanel srp){
		
		this.scalar = scalar;
		
		this.width = (int)(width * scalar);
		this.height = (int)(height * scalar);

		hitbox = new HitBox(x, y, (int) (width * scalar),(int) (height * scalar));
		panel = srp;
	}

	//Constructor containing x, y, width, height, the size scalar, the appear chance, and the panel
	public Actor(int x, int y, int width, int height, double scalar, double chance, SquirrelRunPanel srp){
		
		this.scalar = scalar;
		appearChance = chance;
		this.width = (int)(width * scalar);
		this.height = (int)(height * scalar);

		hitbox = new HitBox(x, y, (int) (width * scalar),(int) (height * scalar));
		panel = srp;
	}
	
	//Getter Methods
	public HitBox getHitBox(){
		return hitbox;
	}
	
	public int x(){
		return hitbox.x();
	}
	
	public int y(){
		return hitbox.y();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public double getScalar(){
		return scalar;
	}
	
	public boolean collidesWith(Actor a){
		boolean collision = false;
		if(this.hitbox.intersects(a.getHitBox()))
				collision = true;
		return collision;
	}

	//Setter Methods
	public void setX(int nX){
		hitbox.update(nX, y());
	}
	
	public void updateX(int offset){
		hitbox.updatePos(offset, 0);
	}
	
	public void setY(int nY){
		hitbox.update(x(), nY);
	}
	
	public void updateY(int offset){
		hitbox.updatePos(0, offset);
	}
	
	public void setPos(int nX, int nY){
		hitbox.update(nX, nY);
	}
}
