package src2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 *This class does not need to be instantiated, however getScores() should be used first 
 * 
 */

public class HighScoreList {
	
	private static String scoresFile = HighScoreList.class.getClassLoader().getResource("src2/scores.txt").getFile();
	private static String namesFile = HighScoreList.class.getClassLoader().getResource("src2/names.txt").getFile();
	private static int size = 10; //Size of the high scores list
	private static int[] scores = new int[]{30000,28000, 27500, 25000, 22000, 20000, 15000, 10000, 7500, 5000};
	private static String[] names = new String[]{"Toon", "Toon", "Toon", "Toon", "Toon", "Toon", "Toon", "Toon", "Toon", "Toon"};
	public static boolean addingScore = false;
	
	
	public static void initScores(){
		try{
			//Artist.msg(HighScoreList.class.getClassLoader().getResource("src2").getFile());
			//addScore(24500, "Tyler");
			//writeScores();
			//writeNames();
			Scanner scanner = new Scanner(new File(scoresFile));
			int i = 0;
			//scanner.next();
			while(scanner.hasNext() && i < size){
				scores[i++] = Integer.parseInt(scanner.next());
				//Artist.msg(scanner.next() + "");
				//scanner.next();
			}
			scanner.close();
			
			Scanner scanner2 = new Scanner(new File(namesFile));
			i = 0;
			while(scanner2.hasNext() && i < size){
				names[i++] = scanner2.next();
				//Artist.msg(names[i-1]);
			}
			
			scanner2.close();
		}
		catch(IOException e){
			Artist.msg("Problem with initScores");
		}
	}

	public static void writeScores(){
		try{
			FileWriter writer = new FileWriter(new File(scoresFile));
			BufferedWriter bWriter = new BufferedWriter(writer);
			for(int i = 0; i < size; i++){
				bWriter.write(Integer.toString(scores[i]));
				bWriter.newLine();
				//Artist.msg("writing " + scores[i] +" to scores");
			}
			
			bWriter.close();
		}
		catch(IOException e){
			System.out.println("[HIGHSCORES] Write error with Scores");
		}
	}
	
	public static void writeNames(){
		try{
			FileWriter writer = new FileWriter(new File(namesFile));
			BufferedWriter bWriter = new BufferedWriter(writer);
			for(int i = 0; i < size; i++){
				bWriter.write(names[i]);
				bWriter.newLine();
			}
			
			bWriter.close();
		}
		catch(IOException e){
			System.out.println("[HIGHSCORES] Write error with Names");
		}
	}


	//Also checks if score should be added
	//This one also adds the name
	public static void addScore(int newScore, String newName){
		//start at the back and find where the new score goes
		int pos = size + 1;
		for(int i = size-1; i >= 0; i--){
			if(newScore > scores[i]){
				pos = i;

			}
		}
		if(pos < size){
			for(int j = size-1; j > pos; j--){
				scores[j] = scores[j-1];
				names[j] = names[j-1];
			}
			names[pos] = newName;
			scores[pos] = newScore;
		}
		
		writeScores();
		writeNames();
		addingScore = false;
	}

	
	public static int[] getScores(){
		return scores;
	}
	
	public static String[] getNames(){
		return names;
	}
	
	public static void printScores(){
		for(int i=0; i < size; i++){
			System.out.println(scores[i]);
		}
	}
	
	//get the high score placement of a new score
	//returns -1 if you suck
	public static int getPlace(int score){
		//start at the back and find where the new score goes
		int pos = -1;
		for(int i = size - 1; i >= 0; i--){
			if(score > scores[i]){
				pos = i;
			}
		}
		return pos;
	}
	
}
