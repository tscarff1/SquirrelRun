package src2;

public class HitBox {
	private int x, y, x2, y2;
	private int width, height;
	private int bufferX, bufferY;
	
	public HitBox(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		x2 = x + w;
		y2 = y + h;
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}
	
	public int x2(){
		return x + width;
	}
	
	public int y2(){
		return y + height;
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}

	
	public int getBufferX(){
		return bufferX;
	}
	
	public int getBufferY(){
		return bufferY;
	}
	
	public void update(int nX, int nY){
		x = nX;
		y = nY;
		x2 = x + width;
		y2 = y + height;
	}
	
	public void updatePos(int xDif, int yDif){
		x += xDif;
		y += yDif;
	}
	
	public void update(int nX, int nY, int nX2, int nY2){
		x = nX;
		y = nY;
		x2 = nX2;
		y2 = nY2;
		
		width = Math.abs(x - x2);
		height = Math.abs(y - y2);
	}
	
	public void update(int xDif, int yDif, int nW, int nH, int yes){//Once again yes is there simply to differentiate shit
		x += xDif;
		y += yDif;
		width = nW;
		height = nH;
	}
	
	//test for intersection with another hitbox
	public boolean intersects(HitBox b){
		if(this.x < b.x() + b.width() && this.x + this.width() > b.x()){
			if(this.y() + this.height > b.y() && this.y() < b.y() + b.height())
				return true;
		}
		
		return false;
	}
	//check for intersection with a point
	public boolean intersects(int x, int y){
		if(x > this.x && x < this.x2){
			if(y > this.y && y < this.y2)
				return true;
		}
		
		return false;
	}
}
