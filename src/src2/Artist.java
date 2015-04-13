package src2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Artist {
	private SquirrelRunPanel panel;
	
	//The graphics Folder
	private static String gFolder = "Graphics/";
	
	private String strTitleImg = "Title";
	private Image iTitle;
	
	private String strToonStanding = "Toon the Squirrel Standing";
	private Image iToonStanding;
	
	private String strRoad = "road";
	private Image iRoad;
	private int roadOffset = 0;
	
	private Image iHills = Artist.registerImage("hills");
	private int hillsOffset = 0;

	public static Image iDarkCloud = Artist.registerImage("Winter_1");
	
	private Image iWinterWarning = Artist.registerImage("Caution");

	private String strHyperBarPanel = "Hyper Bar Panel";
	private Image iHyperBarPanel;
	
	private String strHyperBarMeter = "Hyper Bar Meter";
	private Image iHyperBarMeter;
	
	private String strSleepingToon = "Sleeping Toon";
	private Image iSleepingToon;
	
	private Image iAcorns = Artist.registerImage("acorns");
	
	private Image iPanel = Artist.registerImage("panel");
	
	//Instructions Images
	private double instructAnim = 0;
	private Image[] GIFToonRunning = new Image[3];
	private Image[] iLRKeys = new Image[3];
	private Image[] iSpaceKey = new Image[2];
	
	
	
	//Fonts and things
	private Font titleFont = new Font("TimesRoman", Font.BOLD, 40);
	private Font plainfont = new Font("Arial", Font.PLAIN, 12);
	private Font boldfont = new Font("Arial", Font.BOLD, 12);
	
	
	public Artist(SquirrelRunPanel srp){
		panel = srp;
		
		registerImages();
	}
	
	public void registerImages(){
		iRoad = registerImage(strRoad);
		//These exist solely to improve readability
		initNonPlayScreenImages();
		initBonusImages();
		}  
	
	public static Image registerImage(String imgStr){
		Image img;
		try {
			img = (new ImageIcon(Artist.class.getResource(gFolder + imgStr + ".png"))
			.getImage());
		}
		catch(NullPointerException e){
			img = new ImageIcon(Artist.class.getResource(gFolder + "error.png"))
			.getImage();
			System.out.println("Image: " + imgStr + " not found.");
		}
		return img;
	}
	
	public static Image registerImage(String imgStr, String imgType){
		Image img;
		try {
			img = (new ImageIcon(Artist.class.getResource(gFolder + imgStr +"."+ imgType))
			.getImage());
		}
		catch(NullPointerException e){
			img = new ImageIcon(Artist.class.getResource(gFolder + "error.png"))
			.getImage();
			System.out.println("Image: " + imgStr + " not found.");
		}
		return img;
	}
	
	public static Image[] registerImageArray(String[] imgStr){
		Image[] img = new Image[imgStr.length];
		for(int i = 0; i < imgStr.length; i++){
			try {
				img[i] = (new ImageIcon(Artist.class.getResource(gFolder + imgStr[i] + ".png"))
				.getImage());
			}
			catch(NullPointerException e){
				img[i] = new ImageIcon(Artist.class.getResource(gFolder + "error.png"))
				.getImage();
				System.out.println("Image: " + imgStr + " not found.");
			}
		}
		return img;
	}
	
	//These methods exist only for readability
	private void initNonPlayScreenImages(){
		// ***** This section registers the images for the title screen *****
				//Specifically, the text
				iTitle = registerImage(strTitleImg);
				//Now Toon Standing
				iToonStanding = registerImage(strToonStanding);
				
				//-------------- Game Over Screen -------------------
				iSleepingToon = registerImage(strSleepingToon);
				
				//-------------- Instructions -----------------------
				for(int i = 0; i < 3; i++){
					GIFToonRunning[i] = registerImage("Toon_Running_" + (int)(i+1));
					iLRKeys[i] = registerImage("lrkeys_" + i);
						
				}
				
				iSpaceKey[0] = new ImageIcon(getClass().getResource(gFolder + "spacekey_0.png")).getImage();
				iSpaceKey[1] = new ImageIcon(getClass().getResource(gFolder + "spacekey_1.png")).getImage();
			
	}

	private void initBonusImages(){
		iHyperBarPanel = registerImage(strHyperBarPanel);
		iHyperBarMeter = registerImage(strHyperBarMeter);	
	}
	
	//drawing screens do NOT involve drawing buttons
	public void drawTitleScreen(Graphics g){
		g.setColor(new Color(89, 92, 255));
		g.fillRect(0, 0, SquirrelRunFrame.WIDTH, SquirrelRunFrame.HEIGHT);
		g.drawImage(iHills, 0, 150, SquirrelRunFrame.WIDTH * 2, iHills.getHeight(null) + 100, null);

		g.drawImage(iTitle, 20,20, panel);
		
		g.drawImage(iToonStanding, 230, 250, panel);
	}
	
	public void drawInstructionsScreen(Graphics g, int page){
		//Some variables to keep the format consistent
		int textBuffer = 50;
		int leftBuffer = 80;
		int topBuffer = 100;
		int textOffsetTop = 25;
		int spaceBWImages = 20;	

		g.drawImage(iPanel, -70, -50, SquirrelRunFrame.WIDTH + 140, SquirrelRunFrame.HEIGHT + 100, null);
		g.setColor(new Color(200,200,200, 80));
		g.fillRect(0,0, SquirrelRunFrame.WIDTH, SquirrelRunFrame.HEIGHT);
		
		switch(page){
		case 0:
			int gtrW = GIFToonRunning[0].getWidth(null);
			int gtrH = GIFToonRunning[0].getHeight(null);
			g.setColor(Color.WHITE);
			g.fillRect(50, 50, gtrW, gtrH);
			g.setColor(Color.BLACK);
			g.drawRect(48, 48, gtrW + 2, gtrH+ 2);
			g.drawImage(GIFToonRunning[((int)instructAnim)%3], 50,50, gtrW, gtrH,null);
			//g.drawImage(GIFToonRunning2, 50,50, gtrW, gtrH, null);
			//Set Font here
			g.setColor(Color.BLACK);
			g.drawString("To the left is TOON the Squirrel. He is trying to collect acorns for Winter.", 
					50 + gtrW + 20, 50 + 20);
			g.drawString("Try to collect as many acorns as you can while avoiding enemies.", 
					50 + gtrW + 20, 50 + 20 + 30);
			g.drawString("If you do get hit by an enemy, you will trip and slide closer to Winter!.", 
					50 + gtrW + 20, 50 + 20 + 30+ 30);

			g.drawImage(iLRKeys[(((int) instructAnim) / 5)%3], 80, 200, 
					iLRKeys[0].getWidth(null), iLRKeys[0].getHeight(null), null);
			g.drawString("Use the left and right keys to move left and right, and to scroll through these instructions!",
					50 + gtrW + 20, 220);

			g.drawImage(iSpaceKey[(((int) instructAnim) / 5)%2], 70, 300,
					iSpaceKey[0].getWidth(null), iSpaceKey[0].getHeight(null), null);
			g.drawString("Press space to jump! (You can still move while in the air)",
					50 + gtrW + 20, 320);
			break;
		case 1:

			FontMetrics metrics = g.getFontMetrics(titleFont);
			g.setColor(Color.BLACK);
			g.setFont(titleFont);
			g.drawString("BONUSES", panel.getWidth()/2 - metrics.stringWidth("BONUSES")/2, 40);

			g.setColor(Color.BLACK);

			spaceBWImages += 40;

			//Acorn Image and description
			int acornW = (int) (Acorn.iAcorn.getWidth(null) * 1.5);
			int acornH = (int) (Acorn.iAcorn.getHeight(null) * 1.5);
			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer, acornW + 4, acornH);
			g.drawImage(Acorn.iAcorn, leftBuffer + 2, topBuffer, acornW, acornH, null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer, acornW + 4,acornH);
			g.setFont(plainfont);
			g.drawString("This is an acorn. Collect it to increase your SCORE MULTPLIER", 
					leftBuffer + textBuffer + acornW, topBuffer + textOffsetTop);

			//Energy Drink Image and description
			int energyW = (int) (EnergyDrink.iDrink.getWidth(null) * 0.9);
			int energyH = (int) (EnergyDrink.iDrink.getHeight(null) * 0.9);

			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer + acornH + spaceBWImages, energyW + 20, energyH);
			g.drawImage(EnergyDrink.iDrink, leftBuffer + 10, topBuffer + acornH + spaceBWImages, energyW, energyH, null);

			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer, topBuffer + acornH + spaceBWImages, energyW + 20, energyH);
			g.drawString("This is an energy drink. Collect it to become HYPER for a short time!", 
					leftBuffer + textBuffer + acornW, topBuffer + acornH + spaceBWImages + textOffsetTop);
			g.drawString("When Hyper, you move faster, (this means winter will fall behind), you gain more points, and trip for less time.", 
					leftBuffer + textBuffer + acornW, topBuffer + acornH + spaceBWImages + textOffsetTop + 30);

			break;
		case 2:
			FontMetrics m2 = g.getFontMetrics(titleFont);
			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("ENEMIES", panel.getWidth()/2 - m2.stringWidth("ENEMIES")/2, 40);
			spaceBWImages = 60;

			//Mole Image and description
			int moleW = (int) (EnemyMole.iMole.getWidth(null));
			int moleH = (int) (EnemyMole.iMole.getHeight(null));
			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer, moleW + 4, moleH);
			g.drawImage(EnemyMole.iMole, leftBuffer + 2, topBuffer, moleW, moleH, null);
			g.drawImage(EnemyMole.iDirt, leftBuffer + 2, topBuffer + moleH - EnemyMole.iDirt.getHeight(null), moleW, EnemyMole.iDirt.getHeight(null), null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer, moleW + 4,moleH);
			g.setFont(plainfont);
			g.drawString("This is Menace the Mole. He pops out of the ground when you get near.", 
					leftBuffer + textBuffer + moleW, topBuffer + textOffsetTop);
			g.drawString("Try to jump when you see the disturbed dirt getting close!", 
					leftBuffer + textBuffer + moleW, topBuffer + textOffsetTop + 25);

			//Crow Image and description
			int crowW = (int) (EnemyCrow.iCrow[0].getWidth(null) * 0.2);
			int crowH = (int) (EnemyCrow.iCrow[0].getHeight(null) * 0.2);
			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer + moleH + spaceBWImages, crowW + 4, crowH);
			g.drawImage(EnemyCrow.iCrow[0], leftBuffer + 2, topBuffer + moleH + spaceBWImages, crowW, crowH, null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer + moleH + spaceBWImages, crowW + 4,crowH);
			g.setFont(plainfont);
			g.setColor(Color.BLACK);
			g.drawString("This is the Cranky Crow. He has two flight patterns he might take: ", 
					leftBuffer + textBuffer + moleW, topBuffer + textOffsetTop + moleH + spaceBWImages);
			g.drawString("He will either fly in a downwards arc, or simply fly in a straight horizontal line.", 
					leftBuffer + textBuffer + moleW, topBuffer + textOffsetTop + 25 + moleH + spaceBWImages);

			break;
		case 3:
			FontMetrics m3 = g.getFontMetrics(titleFont);
			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("ENEMIES CONTINUED", panel.getWidth()/2 - m3.stringWidth("ENEMIES CONTINUED")/2, 40);
			spaceBWImages = 60;


			//Basball Image and description
			int ballW = (int) (EnemyBaseball.img.getWidth(null));
			int ballH = (int) (EnemyBaseball.img.getHeight(null));
			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer, ballW, ballH);
			g.drawImage(EnemyBaseball.img, leftBuffer + 2, topBuffer, ballW, ballH, null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer, ballW,ballH);
			g.setFont(plainfont);
			g.drawString("This is the Brutal Baseball. It will bounce as it travels across the screen.", 
					leftBuffer + textBuffer + ballW, topBuffer + textOffsetTop);
			g.drawString("Remember, you can jump over the ball, or walk under it depending on your timing.", 
					leftBuffer + textBuffer + ballW, topBuffer + textOffsetTop + 25);


			//Car Image and description
			int carW = (int) (EnemyCar.iCar[0].getWidth(null));
			int carH = (int) (EnemyCar.iCar[0].getHeight(null));
			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer + ballH + spaceBWImages, carW, carH);
			g.drawImage(EnemyCar.iCar[0], leftBuffer + 2, topBuffer + ballH + spaceBWImages, carW, carH, null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer + ballH + spaceBWImages, carW, carH);
			g.setFont(plainfont);
			g.drawString("The is the Tricky Truck. You can either hitch a ride, or get tripped up underneath!", 
					leftBuffer + textBuffer + carW, topBuffer + textOffsetTop + ballH + spaceBWImages);
			g.drawString("You will not be able to interact with the car more than once, which can be a good or bad thing.", 
					leftBuffer + textBuffer + carW, topBuffer + textOffsetTop + ballH + 25 + spaceBWImages);
			break;

		case 4:
			FontMetrics m4 = g.getFontMetrics(titleFont);
			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("WINTER", panel.getWidth()/2 - m4.stringWidth("WINTER")/2, 40);
			spaceBWImages = 60;

			int cautionW = (int) (iWinterWarning.getWidth(null) * 1.5);
			int cautionH = (int) (iWinterWarning.getHeight(null) * 1.5);
			g.setColor(Color.WHITE);
			g.fillRect(leftBuffer, topBuffer, cautionW, cautionH);
			g.drawImage(iWinterWarning, leftBuffer, topBuffer, cautionW, cautionH, null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer, cautionW, cautionH);
			g.setFont(plainfont);
			g.drawString("When winter is far away, you will see this, and you will see how far away winter is.", 
					leftBuffer + textBuffer + cautionW, topBuffer + textOffsetTop);
			
			
			int winterW = (int) (iDarkCloud.getWidth(null) * 0.5);
			int winterH = (int) (iDarkCloud.getHeight(null) * 0.5);
			g.setColor(Color.BLUE);
			g.fillRect(leftBuffer, topBuffer + cautionH + spaceBWImages, winterW, winterH + 80);
			g.setColor(new Color(0,0,0,200));
			g.drawImage(Snowflake.iSnowflake[0], leftBuffer + 40, topBuffer + cautionH + spaceBWImages + 70, null);
			g.drawImage(Snowflake.iSnowflake[2], leftBuffer + 270, topBuffer + cautionH + spaceBWImages + 40, null);
			g.drawImage(Snowflake.iSnowflake[1], leftBuffer + 120, topBuffer + cautionH + spaceBWImages + 90, null);
			g.drawImage(iDarkCloud, leftBuffer, topBuffer + cautionH + spaceBWImages, winterW, winterH, null);
			g.setColor(Color.BLACK);
			g.drawRect(leftBuffer,topBuffer + cautionH + spaceBWImages, winterW, winterH + 80);
			g.setFont(plainfont);
			g.drawString("When it gets close enough, you will see the dark clouds and Snowflakes", 
					leftBuffer + textBuffer + winterW, topBuffer + textOffsetTop + cautionH + spaceBWImages);
			g.drawString("If you run into this, it's GAME OVER.", 
					leftBuffer + textBuffer + winterW, topBuffer + textOffsetTop + cautionH + spaceBWImages + 30);
			
			break;
		}
		// /4 is the number of pages. I'm guessing
		g.setFont(plainfont);
		g.setColor(Color.BLACK);
		g.drawString("Page " + (page + 1) + "/5", panel.getWidth() - 70, panel.getHeight() - 40);
		//if(page == 0 || page == 2 || page == 3)
			//instructAnim+=1;
	}

	public void drawHighScoreScreen(Graphics g){
		g.drawImage(iPanel, -70, -50, SquirrelRunFrame.WIDTH + 140, SquirrelRunFrame.HEIGHT + 100, null);
		g.setColor(new Color(200,200,200, 100));
		g.fillRect(0,0, SquirrelRunFrame.WIDTH, SquirrelRunFrame.HEIGHT);
		int[] scores = HighScoreList.getScores();
		String[] names = HighScoreList.getNames();
		int startX = 360;
		int startY = 120;
		int dY = 30;

		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("HIGH SCORES", startX - 70, 70);

		for(int i=0; i < scores.length; i++){
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString((i+1)+ ":", startX - 80, startY + dY * i);
			g.setFont(new Font("Arial", Font.PLAIN, 14));
			g.drawString(scores[i] + "", startX + 50, startY + dY * i);
			g.drawString(names[i], startX + 100 + 100, startY + dY * i);
		}
	}
	

	public void drawGameOverScreen(Graphics g){
		g.setColor(new Color(89, 92, 255));
		g.fillRect(0, 0, 900, 500);
		g.setColor(new Color(0,0,0, 200));
		g.fillRect(0,0,SquirrelRunFrame.WIDTH, SquirrelRunFrame.HEIGHT);
		g.drawImage(SunAndMoon.iMoon, 700, 50, null);
		
		g.drawImage(iPanel, 340, 70, 220, 40, null);
		g.setColor(Color.BLACK);
		g.drawImage(iAcorns, 0, 250, null);
		g.drawImage(iSleepingToon, 250, 240, panel);
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		g.drawString("You scored: " + panel.getScore(), 350, 100);
	}
	
	public void drawButton(Button b, Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		if(b != null){
			g2d.rotate(Math.toRadians(b.getRotation() * Button.DROTATE), b.getHitBox().x() + 75, b.getHitBox().y() + 24);
			g.drawImage(b.getImage(), b.getHitBox().x(), b.getHitBox().y(), panel);
			g2d.rotate(-1 *Math.toRadians(b.getRotation() * Button.DROTATE), b.getHitBox().x() + 75, b.getHitBox().y() + 24);
		}
	}
	
	public void drawToon(Graphics g, int pos, Toon toon){
		if(!toon.isRiding()){
			if(!toon.isTripping()){	
				if(!toon.isJumping())//If Toon is not jumping
					g.drawImage(Toon.iRunning[pos], toon.x(), toon.y(), toon.getWidth(), toon.getHeight(), panel);
				else//If he is
					g.drawImage(Toon.iRunning[0], toon.x(), toon.y(), toon.getWidth(), toon.getHeight(), panel);
			}
			else
				g.drawImage(Toon.iTripping, toon.x(), toon.y(), toon.getWidth(), toon.getHeight(), panel);
		}
		else{
			g.drawImage(Toon.iSitting[0], toon.x(), toon.y(), 
					(int) (Toon.iSitting[0].getWidth(null) * 0.3), 
					(int) (Toon.iSitting[0].getHeight(null) * 0.3), null);
		}
	}
	
	public void drawAcorn(Graphics g, Acorn acorn){
		g.drawImage(Acorn.iAcorn, acorn.x(), acorn.y(), acorn.getWidth(), acorn.getHeight(), panel);
	}
	
	public void drawAcorns(Graphics g, Acorn[] acorns){
		for(int i = 0; i < acorns.length; i++){
			drawAcorn(g, acorns[i]);
		}
	}
	
	public void drawDrink(Graphics g, EnergyDrink d){
		g.drawImage(EnergyDrink.iDrink, d.x(), d.y(), d.getWidth(), d.getHeight(), panel);
	}
	
	public void drawScoreStuff(Graphics g){
		g.drawImage(iPanel, 260, 2, 300, 60, null);
		g.setColor(Color.BLACK);
		g.drawString("Score: "  + panel.getScore(), 300, 20);
		g.drawString("Score Multiplier: " + panel.getScoreMult(), 400, 20);

		g.drawImage(iHyperBarPanel, 280, 30, panel);
	}
	
	
	public void drawHyperStuff(Graphics g){
		int barLength = (int) (((double)panel.getMaxHyperTime() - panel.getHyperTime())/panel.getMaxHyperTime() * 227);

		g.drawImage(iHyperBarMeter, 290, 32, barLength, 15, null);
	}
	
	public void drawRoad(Graphics g){
		g.drawImage(iRoad, 0 - roadOffset, 300, null);
		roadOffset+=panel.getRoadSpeed();
		if(roadOffset > 176)
			roadOffset = 0;
		
	}
	
	public void drawBG(Graphics g){
		//drawing the sky
		g.setColor(new Color(89, 92, 255));
		g.fillRect(0, 0, 900, 500);
	}
	
	public void drawHills(Graphics g){
		g.drawImage(iHills, 0 - hillsOffset, 150, SquirrelRunFrame.WIDTH * 2, iHills.getHeight(null), null);
		g.drawImage(iHills, 0 - hillsOffset + SquirrelRunFrame.WIDTH*2, 150, SquirrelRunFrame.WIDTH*2, iHills.getHeight(null), null);
		hillsOffset += panel.getRoadSpeed() * .5;
		
		if(hillsOffset > SquirrelRunFrame.WIDTH * 2)
			hillsOffset = 0;
	}
	
	public void drawClouds(Graphics g, Cloud[] clouds){
		for(int i = 0; i < clouds.length; i++){
			drawCloud(g, clouds[i]);
		}
	}
	
	private void drawCloud(Graphics g, Cloud floatle){
		if(floatle != null)
			g.drawImage(Cloud.iClouds[floatle.getType()], floatle.x(), floatle.y(), panel);
			//g.drawImage(iClouds[0], floatle.x(), floatle.y(), panel);
	}
	
	public void drawSunAndMoon(Graphics g, SunAndMoon sam){
		g.setColor(new Color(0,0,0, sam.getDayNightScalar()));
		g.fillRect(0,0, SquirrelRunFrame.WIDTH, SquirrelRunFrame.HEIGHT);
		g.drawImage(SunAndMoon.iSun,
				sam.sX() - SunAndMoon.iSun.getWidth(null)/2, 
				sam.sY() - SunAndMoon.iSun.getHeight(null)/2, null);
		
		g.drawImage(SunAndMoon.iMoon,
				(sam.mX() - SunAndMoon.iMoon.getWidth(null)/2), 
				(sam.mY() - SunAndMoon.iMoon.getHeight(null)/2), null);
	}
		
	public void drawMoleEnemy(Graphics g, EnemyMole mole){
		//g.drawImage(iMoleEnemy, mole.x(), mole.y(), mole.getWidth(), mole.getRealHeight(), panel);
		g.drawImage(EnemyMole.iMole, 
				mole.x(), mole.y(), mole.x() + mole.getWidth(), mole.y() + mole.getRealHeight(),
				0, 0, mole.getWidth(), mole.getRealHeight(), 
				panel);
		
		g.drawImage(EnemyMole.iDirt, mole.x(), mole.y() + mole.getRealHeight() - 8, panel);
	}
	
	public void drawCrowEnemy(Graphics g, EnemyCrow crow){
		if(crow != null){
			int drawY = crow.y(); //to account for the difference in height created by the different frames			
			if(crow.getCrowPos() == 1)
				drawY -= 16;
			g.drawImage(EnemyCrow.iCrow[crow.getCrowPos()],
				crow.x(), drawY,
				crow.getWidth(), crow.getHeight(), panel);
		}
	}

	public void drawBallEnemy(Graphics g, EnemyBaseball ball){
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(Math.toRadians(ball.getRotation()), 
				ball.getHitBox().x() + ball.getWidth()/2, 
				ball.getHitBox().y() + ball.getHeight()/2);
		
		g.drawImage(EnemyBaseball.img, ball.x(), ball.y(), ball.getWidth(), ball.getHeight(), null);
		
		g2d.rotate(Math.toRadians(-1 * ball.getRotation()), 
				ball.getHitBox().x() + ball.getWidth()/2, 
				ball.getHitBox().y() + ball.getHeight()/2);
		
	}
	
	public void drawCarEnemy(Graphics g, EnemyCar car){
		if(car != null){
			g.setColor(Color.GRAY);
			//g.fillRect(car.x(), car.y(), car.getWidth(), car.getHeight());
			g.drawImage(EnemyCar.iCar[car.getMovePos()], car.x(), car.y(), car.getWidth(), car.getHeight(), null);
		}
	}

	
	public void drawWinter(Graphics g){
		if(panel.getWinterPos() >= 0){
			drawSnowflakes(g);
			//g.drawImage(iWinter, panel.getWinterPos(), 50, panel);
			g.drawImage(iDarkCloud, panel.getWinterPos() - iDarkCloud.getWidth(null), 0, null);
		}
		else{
			g.setColor(Color.black);
			g.drawString(-1 * panel.getWinterPos() + "m", 20, 130);
			g.drawImage(iWinterWarning, 10, 150, panel);
		}
	}
	
	public void drawSnowflakes(Graphics g){
		Snowflake[] snowflakes = panel.getSnowflakes();
		for(int i = 0; i < snowflakes.length; i++){
			if(snowflakes[i] != null){
				g.drawImage(snowflakes[i].getImage(), panel.getWinterPos() + snowflakes[i].x() - iDarkCloud.getWidth(null), snowflakes[i].y(), null);
			}
		}
	}
	
	
	//METHODS FOR TESTING PURPOSES
	
	public void drawHitBox(Graphics g, HitBox h){
		g.setColor(Color.GRAY);
		g.fillRect(h.x(), h.y(), h.width(), h.height());
	}
	
	//A method so that i can see where the hitboxes are
	//used for error checking. Will not be used when game is completed
	public void drawHitBoxes(Graphics g, Actor[] actors){
		g.setColor(Color.GRAY);
		for(int i = 0; i < actors.length; i++){
			if(actors[i] != null)
			g.fillRect(actors[i].getHitBox().x(),
					   actors[i].getHitBox().y(), 
					   actors[i].getHitBox().width(), 
					   actors[i].getHitBox().height());
		}
	}
	
	//Write a message to the console
	//This is in artist because it deals with visuals, even if it is the console
	@SuppressWarnings("rawtypes")
	public static void msg(Class c, String s){
		System.out.println("[" + c.getName() + "]: " + s);
	}
	
	public static void msg(String s){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTraceElements[2];
		System.out.println("[" + element.getClassName() + "]: " + s);
	}
}