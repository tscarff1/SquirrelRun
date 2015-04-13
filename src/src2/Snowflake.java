package src2;

import java.awt.Image;

public class Snowflake {
	public static Image[] iSnowflake = Artist.registerImageArray(new String[]{"flake1", "flake2", "flake3", "flake4"});
	private int x, y, speed, img;
	
	public Snowflake(){
		reset();
	}
	
	public Snowflake reset(){
		this.img = (int) (Math.random() * Snowflake.iSnowflake.length);
		this.x = (int) (Math.random() * Artist.iDarkCloud.getWidth(null) - 300) - Snowflake.iSnowflake[img].getWidth(null)/2 + 300;
		this.y = -1 * iSnowflake[img].getHeight(null);
		speed = (int) (Math.random() * 6) + 3;
		return this;//Don't know if there is any benefit to this line, but doing it cuz I'm that cool
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}
	
	public Image getImage(){
		return iSnowflake[img];
	}
	
	public void move(){
		y += speed;
	}
}
