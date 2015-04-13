package src2;


public class SoundPlayer {
	private static SquirrelRunPanel panel;
	private static Sound theme = new Sound("Squirrel_Run_Theme.wav");
	private static Sound gameOverTheme = new Sound("GameOverTheme.wav");
	private static Sound yahoo = new Sound("yahoo.wav");
	private static Sound energyTheme = new Sound("EnergyTheme.wav");
	
	private static Sound ow = new Sound("ow.wav");
	private static Sound revving = new Sound("engine.wav");
	private static Sound pop = new Sound("pop.wav");

	public static void playTheme(){
		theme = new Sound("Squirrel_Run_Theme.wav");
		theme.loop();
	}
	
	public static void stopTheme(){
		theme.stop();
	}
	
	public static void playYahoo(){
		yahoo.play();
	}
	
	public static  void playEnergyTheme(){
		energyTheme.play();
	}
	
	public static void playGameOverTheme(){
		gameOverTheme = new Sound("GameOverTheme.wav");
		gameOverTheme.play();
	}
	
	public static void playOw(){
		ow = new Sound("ow.wav");
		ow.play();
	}
	
	public static void playRevving(){
		revving = new Sound("engine.wav");
		revving.play();
	}
	
	public static void playPop(){
		pop = new Sound("pop.wav");
		pop.play();
	}
}
