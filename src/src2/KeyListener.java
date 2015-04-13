package src2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter{
	private SquirrelRunPanel panel;
	
	public KeyListener(SquirrelRunPanel srp){
		panel = srp;
	}

	public void keyPressed(KeyEvent e){
		//System.out.println("[KEYLISTENER] keyPressed");
		int keyPressed = e.getKeyCode();
		if(panel.getGameMode() == SquirrelRunPanel.PLAYING){

			if(keyPressed == KeyEvent.VK_LEFT){
				panel.setToonDir(panel.LEFT);
			}
			else if(keyPressed == KeyEvent.VK_RIGHT)
				panel.setToonDir(panel.RIGHT);

			if (keyPressed == KeyEvent.VK_SPACE){
				panel.makeToonJump();
			}
		}
		
		if(panel.getGameMode() == SquirrelRunPanel.INSTRUCTIONS){
			if(keyPressed == KeyEvent.VK_RIGHT)
				panel.nextInstructPage();
			else if(keyPressed == KeyEvent.VK_LEFT)
				panel.prevInstructPage();
		}
		
		if(keyPressed == KeyEvent.VK_HOME){
			panel.setGameMode("GAMEOVER");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyReleased = e.getKeyCode();
		if(keyReleased == KeyEvent.VK_RIGHT || keyReleased == KeyEvent.VK_LEFT)
			panel.setToonDir(-1);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("[KEYLISTENER] KeyTyped");
		int keyPressed = e.getKeyCode();
		
	}
}
