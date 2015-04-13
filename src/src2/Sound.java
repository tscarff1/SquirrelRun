package src2;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private final String SDIR = "Sounds/";//Sound DIRectory
	private AudioClip clip;
	
	public Sound(String filename){
		try{
			//String stringF = Sound.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			//Artist.msg(this.getClass().getResource(SDIR).getPath() + "");

			clip = Applet.newAudioClip(this.getClass().getResource(SDIR+filename));
			
		}catch(NullPointerException e){
			System.out.println("[SOUND] File specified does not exist.");
			e.printStackTrace();
		}
	}
	
	public void play(){
		clip.play();
	}
	
	public void loop(){
		clip.loop();
	}
	
	public void stop(){
		clip.stop();
	}
}
