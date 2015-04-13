package src2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class SquirrelRunPanel extends JPanel implements ActionListener{
	private SquirrelRunFrame frame;
	
	private Timer timer;
	private Artist artist;
	
	private int toonPos;
	private int timerCount;
	
	private int gameMode;
	public static final short TITLE = 0;
	public static final short INSTRUCTIONS = 1;
	public static final short PLAYING = 2;
	public static final short GAMEOVER = 3;
	public static final short HIGHSCORE = 4;
	private int instructionsPage = 0;
	
	
	public Button STARTBUTTON;
	public Button INSTRUCTIONSBUTTON;
	public Button TITLEBUTTON;//For Instructions Screen
	public Button TITLEBUTTON2;//For gameplay screen
	public Button GAMEOVERTITLEBUTTON;
	public Button HIGHSCOREBUTTON;
	public Button TITLEBUTTON3;
	private Button[] buttons = new Button[7];
	
	
	public final int LEFT = 0;
	public final int UP = 1;
	public final int RIGHT = 2;
	public final int DOWN = 3;
	
	private int toonDir;
	
	//Listeners
	private MouseListener mouseListener;
	private KeyListener keyListener;
	
	private Toon toon;
	
	private Acorn[] acorns;
	private int maxAcorns = 12;
	private double acornChance = .02;
	private int acornsOnStage = 0;
	
	private EnergyDrink drink;
	private double drinkChance =.0006;
	
	
	//Declaring enemies
	private EnemyMole mole;
	private double moleChance = 0.009;
	
	private EnemyCrow crow;
	private double crowChance = 0.008;

	private EnemyBaseball ball;
	private double ballChance = .005;
	
	private EnemyCar car;
	private double carChance = .0008;
	
	private Actor[] enemies = new Actor[]{ball, crow, mole, car};
	
	private int score;
	private double scoreMult;
	private int scoreInc = 5;
	
	private int maxMultTime = 100;
	private int multTime = 0;
	
	private double roadSpeed = 6;
	
	private final int MENUTIME = 100;
	private final int STANDARDTIME = 76;
	private final int SPEEDTIME = 38;
	
	//Stuff that has to do with Toon having an energy drink
	private final int MAXHYPERTIME = 200;
	private int hyperTime = 0;
	
	private int winterPos;
	
	//Clouds
	private Cloud[] clouds = new Cloud[3];
	private double cloudChance = 0.01;
	private int timeSinceLastCloud = 500;
	
	//Day Night Manager
	SunAndMoon sam;
	
	//So pretty
	private Snowflake[] snowflakes = new Snowflake[25];
	private int timeSinceLastSnow = 100;
	private final int[] SNOWTIMELIMITS = new int[]{50, 200};//min and max times I will allow between snowflakes
	
	SoundPlayer player = new SoundPlayer();
	
	public SquirrelRunPanel(SquirrelRunFrame srf){

		frame = srf;
		gameMode = TITLE;
		HighScoreList.initScores();
		//HighScoreList.writeNames();
		//HighScoreList.addScore(15800, "Tyler");
		
		artist = new Artist(this);
		
		mouseListener = new MouseListener(this);
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
		
		keyListener = new KeyListener(this);
		frame.addKeyListener(keyListener);
		
		STARTBUTTON = new Button(this, "Start", 466, 309, TITLE);
		INSTRUCTIONSBUTTON = new Button(this, "Instructions", 650, 309, TITLE);
		TITLEBUTTON = new Button(this, "Title", 349, 415, INSTRUCTIONS);//Goes on Instructions screen
		TITLEBUTTON2 = new Button(this, "Title", 10, 10, PLAYING);//Goes on Playing screen
		GAMEOVERTITLEBUTTON = new Button(this, "Title", 349, 415, GAMEOVER);
		HIGHSCOREBUTTON = new Button(this, "HighScore", 590, 360, TITLE);
		TITLEBUTTON3 = new Button(this, "Title", 349, 415, HIGHSCORE);//Goes on Instructions screen
		buttons[0] = STARTBUTTON;
		buttons[1] = INSTRUCTIONSBUTTON;
		buttons[2] = TITLEBUTTON;
		buttons[3] = TITLEBUTTON2;
		buttons[4] = GAMEOVERTITLEBUTTON;
		buttons[5] = HIGHSCOREBUTTON;
		buttons[6] = TITLEBUTTON3;

		//This has to go last.
		//If timer is initialized with a delay of under ~10ms, other stuff will not load
		timer = new Timer(MENUTIME, this); 
		timer.start();
	}
	
	//Initializer methods
	public void initAcorns(){
		acorns = new Acorn[maxAcorns];
		for(int i = 0; i < maxAcorns; i++){
			acorns[i] = new Acorn(this);
		}
	}
	
	public void rotateButtons(){
		for(int i = 0; i < buttons.length; i++){
			buttons[i].rotate();
		}
	}

	public Button[] getButtons(){
		return buttons;
	}
	
	public void setGameMode(String mode){
		deleteActors();
		timer.stop();
		timer = null;
		frame.removeKeyListener(keyListener);
		instructionsPage = 0;
		sam = null;
		SoundPlayer.stopTheme();
		if(mode.toUpperCase().equals("TITLE")){
			gameMode = TITLE;
			timer = new Timer(MENUTIME, this);
			
		}
		
		if(mode.toUpperCase().equals("START")){
			gameMode = PLAYING;
			sam = new SunAndMoon();
			toon = new Toon(this);
			mole = new EnemyMole(this);
			drink = new EnergyDrink(this);
			ball = new EnemyBaseball(this);
			car = new EnemyCar(this);
			initAcorns();
			score = 0;
			scoreMult = 1;
			winterPos = -80;
			roadSpeed = 6;
					
			frame.addKeyListener(keyListener);
			timer = new Timer(STANDARDTIME, this);
			SoundPlayer.playTheme();
		}
		if(mode.toUpperCase().equals("INSTRUCTIONS")){
			gameMode = INSTRUCTIONS;
			timer = new Timer(MENUTIME, this);
			SoundPlayer.stopTheme();
			frame.addKeyListener(keyListener);
		}
		
		if(mode.toUpperCase().equals("GAMEOVER")){
			if(HighScoreList.getPlace(score) > -1)
				new HighScoreBox(score);
			gameMode = GAMEOVER;
			timer = new Timer(MENUTIME, this);
			SoundPlayer.playGameOverTheme();
			
		}
		
		if(mode.toUpperCase().equals("HIGHSCORE")){
			gameMode = HIGHSCORE;
			timer = new Timer(MENUTIME, this);
		}
		timer.start();
	}
	
	//Instructions Page methods
	public void nextInstructPage(){
		if(instructionsPage < 4)
			instructionsPage++;
	}
	
	public void prevInstructPage(){
		if(instructionsPage > 0)
			instructionsPage--;
	}
	
	public int getInstructPage(){
		return instructionsPage;
	}
	
	//In Game stuff
	public void setToonDir(int dir){
		toonDir = dir;
	}
	
	public void moveToon(){
		if(!toon.isRiding()){
			toon.moveY();
			if(!toon.isTripping()){
				if(toonDir == LEFT)
					toon.moveLeft();
				if(toonDir == RIGHT)
					toon.moveRight();
			}
			else
				toon.moveTripping();
		}
		else{
			toon.moveWithCar(car);
		}
	}
	
	public void makeToonJump(){
		if(toon.isRiding())
			stopRiding();
		
		if(!toon.isJumping())
			toon.jump();
	}
	
	//Acorn Stuff
	public boolean shouldAcornAppear(){
		if(Math.random() < acornChance)
			return true;
		return false;
	}
	
	public void decideIfAcornShouldAppear(){
		if(shouldAcornAppear() && acornsOnStage < 10){
			int acornNum = 0;
			while(acorns[acornNum].x() < 901){
				acornNum++;
			}
			makeAcornAppear(acornNum);
		}
	}
	
	public void makeAcornAppear(int acornNum){
		acorns[acornNum].startMoving();
	}
	
	public void moveAcorns(){
		for(int i = 0; i < maxAcorns; i++){
			acorns[i].move();
		}
	}
	public void decideIfDrinkShouldAppearAndMakeItAppear(){
		if(!drink.isMoving() && Math.random() < drinkChance)
				drink.startMoving();
	} 
	
	public void checkForAndReactToDrinkCollision(){
		if(toon.collidesWith(drink) && !toon.isRiding()){
			drink.reset();
			drinkEnergy();
		}
	}
	
	private void drinkEnergy(){
		if(!toon.isHyper()){
			SoundPlayer.playYahoo();
			//jukeboxHero.stopTheme();
			//jukeboxHero.playEnergyTheme();
			timer.stop();
			timer = null;
			timer = new Timer(SPEEDTIME, this);
			timer.start();
			toon.setHyper(true);
			scoreMult += 5;
		}
	}
	
	public void decideIfMoleShouldAppearAndMakeHimAppear(){
		double rMoleChance = moleChance + Math.pow(((score)/90000.0),2);
		if(!mole.isMoving() && Math.random() < rMoleChance){
				mole.startMoving();
		}
	}

	public void checkForAndReactToMoleCollision(){
		if(toon.collidesWith(mole) && !mole.hasTrippedToon()){
			toon.setTripping(true);
			mole.setTrippedToon(true);
		}
	}

	public void decideIfCrowShouldAppearAndMakeHimAppear(){
		double rCrowChance = crowChance + Math.pow(score/95000,2);
		if(crow == null && Math.random() < rCrowChance){
			int flightPattern = (int)(Math.random() + .5)%2;
			crow = new EnemyCrow(this, flightPattern);
		}
	}

	private void moveCrow(){
		if(crow != null){
			crow.move();
			if(crow.x() + crow.getWidth() < 0)
				crow = null;
		}
	}
	
	private void decideIfCarShouldAppearAndMakeItAppear(){
		if(!car.getMoving() && Math.random() < carChance){
			car.startMoving();
		}
	}
	
	public void stopRiding(){
		timer.stop();
		timer = new Timer(STANDARDTIME, this);
		timer.start();
	}
	
	private void ballAppearStuff(){
		if(!ball.getMoving() && Math.random() < ballChance){
			ball.startMoving();
		}
	}
	
	private void moveCar(){
		if(car != null)
			car.move();
	}
	
	private void increaseScore(){
		score += (int) (scoreInc * scoreMult);
	}
	
	//Returns a value 0 - 9 depending on which acorn last collides with toon
	//-1 means none are colliding
	private int checkForAcornCollisions(){
		int acornNum = -1;
		for(int i = 0; i < maxAcorns; i++){
			if(toon.collidesWith(acorns[i]))
					acornNum = i;
		}
		return acornNum;
	}
	
	private void accountForAcornCollisions(){
		int toDelete = checkForAcornCollisions();
		if(toDelete != -1){
			deleteAcorn(toDelete);
		}
	}
	
	private void deleteAcorn(int toDelete){
		acorns[toDelete].reset();
		scoreMult += 0.5;
		SoundPlayer.playPop();
	}
	
	private void adjustMultiplier(){
		if(scoreMult > 1){
			multTime++;
			if(multTime == maxMultTime){
				scoreMult -= .5;
				multTime = 0;
			}
		}
	}
	
	private void cloudStuff(){
		if(timeSinceLastCloud > 120 && Math.random() < cloudChance || timeSinceLastCloud > 300){
			if(clouds[0] == null)
				clouds[0] = new Cloud(0, this);
			else if(clouds[1] == null)
				clouds[1] = new Cloud(1, this);
			else if(clouds[2] == null)
				clouds[2] = new Cloud(2, this);
			timeSinceLastCloud = 0;
		}
		timeSinceLastCloud++;
		moveClouds();
	}
	
	private void moveClouds(){
		for(int i = 0; i < 3; i++){
			if(clouds[i] != null){
				clouds[i].move();
				if(clouds[i].x() < 0 - clouds[i].getWidth())
					clouds[i] = null;
			}
		}
	}
	
	private void enemyCollisionStuff(){
		enemies = new Actor[]{ball, crow, mole, car, ball};
		
		for(int i = 0; i < enemies.length; i++){
			if(enemies[i] != null && enemies[i] == car && car.collidesWith(toon, 1)){
					toon.startRiding();
					SoundPlayer.playRevving();
					car.ignoreMoveDelay();
					timer.stop();
					timer = new Timer(SPEEDTIME, this);
					timer.start();
			}
			
			else if(enemies[i] != null && enemies[i].collidesWith(toon) && !toon.isTripping() && !toon.isRiding()){
				if(enemies[i] == mole){//mole has some special actions
					if(!mole.hasTrippedToon()){
						mole.setTrippedToon(true);				
						toon.setTripping(true);
						SoundPlayer.playOw();
					}
				}//done with mole
				
				//otherwise just trip Toon
				else{
					toon.setTripping(true);
					SoundPlayer.playOw();
					//Artist.msg(enemies[i].getClass(), "collided with Toon");
				}
			}
		}
	}
	
	private void moveWinter(){
		//System.out.println("[SRP] Winter is at: " + winterPos);
		int frameDelay = 3;
		if(toon.isHyper())
			winterPos -= 2* ((timerCount % frameDelay+1)/frameDelay);
		else if(winterPos < 150 && !toon.isRiding())
			winterPos += 1* ((timerCount % frameDelay+1)/frameDelay);
		
		//Now for snowflake stuff
		for(int i = 0; i < snowflakes.length; i++){
			if(snowflakes[i] == null){
				if(timeSinceLastSnow > SNOWTIMELIMITS[0]&& timeSinceLastSnow < SNOWTIMELIMITS[1]){
					snowflakes[i] = new Snowflake();
					timeSinceLastSnow = 0;
				}
				else
					timeSinceLastSnow++;
			}
			else{
				snowflakes[i].move();
				
				if(snowflakes[i].y() >SquirrelRunFrame.HEIGHT){
					snowflakes[i] = null;
				}
			}
		}
		
	}
	
	//GETTER Methods
	public Toon getToon(){
		if(toon == null)
			return new Toon(this);
		else
			return toon;
	}
	
	public int getScore(){
		return score;
	}
	
	public double getScoreMult(){
		return scoreMult;
	}

	
	public int getRoadSpeed(){
		return (int) roadSpeed;
	}
	
	public int getHyperTime(){
		return hyperTime;
	}
	
	public int getMaxHyperTime(){
		return MAXHYPERTIME;
	}
	
	public int getWinterPos(){
		return winterPos;
	}
	
	public Snowflake[] getSnowflakes(){
		return snowflakes;
	}
	
	public int getGameMode(){
		return gameMode;
	}
	
	//DELETE METHODS
	public void deleteAcorns(){
		acorns = null;
	}
	
	public void deleteClouds(){
		for(int i = 0; i < 3; i++)
			clouds[i] = null;
	}
	
	public void deleteActors(){
		deleteAcorns();
		deleteClouds();
		toon = null;
		mole = null;
		crow = null;
		drink = null;
		car = null;
	}
	
	//The method to move everything
	private void moveActors(){
		moveToon();
		
		if(toon.isRiding() && timerCount%2 == 0)
				sam.move();
		if(!toon.isHyper()){
			if(!toon.isRiding())
				sam.move();//the day won't move faster just because toon is on a car
			mole.move();
			drink.move();
			moveCar();
			moveCrow();
			ball.move();
			moveAcorns();
		}
		else//If Toon is hyper, move everything at halfspeed
			if(toon.isHyper() && timerCount%2 == 0){
				sam.move();
				mole.move();
				drink.move();
				ball.move();
				moveCrow();
				moveCar();
				moveAcorns();
			}
		
	}
	
	//Stuff to do while Toon is hyper
	//Pre-Condition: Toon is hyper
	private void hyperStuff(){
		if(hyperTime < MAXHYPERTIME)
			hyperTime++;
		else{
			turnOffHyper();
		}
	}
	
	private void turnOffHyper(){
		hyperTime = 0;
		toon.setHyper(false);
		timer.stop();
		timer = null;
		timer = new Timer(STANDARDTIME, this);
		timer.start();
		scoreMult -= 5;
		if(scoreMult < 1)
			scoreMult = 1;
	}
	
	private void checkForAndReactToGameOver(){
		if(toon.x() + 30 < winterPos)
			setGameMode("GAMEOVER");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		toonPos = timerCount%3;
		timerCount++;
		rotateButtons();
		
		if(gameMode == PLAYING){
			cloudStuff();
			moveActors();
			moveWinter();
			decideIfAcornShouldAppear();
			increaseScore();
			accountForAcornCollisions();
			adjustMultiplier();
						
			decideIfDrinkShouldAppearAndMakeItAppear();
			checkForAndReactToDrinkCollision();
			
			decideIfMoleShouldAppearAndMakeHimAppear();
			
			decideIfCrowShouldAppearAndMakeHimAppear();
			
			decideIfCarShouldAppearAndMakeItAppear();
			
			ballAppearStuff();
			
			enemyCollisionStuff();
			if(toon.isHyper())
				hyperStuff();
			
			//slowly accelerate
			roadSpeed += .02;
			
			//MUST come last. If it's a game over and we still want to do things while playing...
			//Just don't do it
			checkForAndReactToGameOver();
		}
		
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//Artist.msg(this.getClass(), "testing");
		if(gameMode == TITLE){
			artist.drawTitleScreen(g);
			g.setColor(Color.BLACK);
			g.drawString("Squirrel Run (Almost-Final): High score list refuses to write to file and is receiving a stern talking to.", 10, 15);
			artist.drawButton(INSTRUCTIONSBUTTON, g);
			artist.drawButton(STARTBUTTON, g);
			artist.drawButton(HIGHSCOREBUTTON, g);
		}

		if(gameMode == INSTRUCTIONS){
			artist.drawInstructionsScreen(g, instructionsPage);
			
			artist.drawButton(TITLEBUTTON, g);
		}
		
		if(gameMode == PLAYING){
			artist.drawBG(g);
			artist.drawSunAndMoon(g, sam);
			artist.drawHills(g);
			artist.drawClouds(g, clouds);
			artist.drawRoad(g);
			
			
			
			artist.drawAcorns(g,acorns);
			artist.drawDrink(g, drink); 
			
			artist.drawMoleEnemy(g, mole);
			artist.drawCrowEnemy(g, crow);
			artist.drawCarEnemy(g, car);
			artist.drawBallEnemy(g, ball);
			artist.drawToon(g, toonPos, toon);
			
			artist.drawWinter(g);
			
			//artist.drawHitBoxes(g, new Actor[]{toon, mole, crow, car, ball});
			//if(car != null)
			//artist.drawHitBox(g, car.getGoodBox());
			//artist.drawHitBox(g, car.getBadBox());
			//artist.drawHitBox(g, toon.getHitBox());
			
			//artist.drawHitBox(g, drink.getHitBox());
			
			artist.drawButton(TITLEBUTTON2, g);
			artist.drawScoreStuff(g);
			
			if(toon.isHyper()){
				artist.drawHyperStuff(g);
			}
			
		}
		
		if(gameMode == GAMEOVER){
			artist.drawGameOverScreen(g);
			artist.drawButton(GAMEOVERTITLEBUTTON, g);
		}
		
		if(gameMode == HIGHSCORE){
			artist.drawHighScoreScreen(g);
			artist.drawButton(TITLEBUTTON3,g);
		}
	}
}