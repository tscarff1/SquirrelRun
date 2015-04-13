package src2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseListener extends MouseAdapter {
	
	private SquirrelRunPanel panel;
	
	public MouseListener(SquirrelRunPanel srp){
		panel = srp;
	}



	public void mouseClicked(MouseEvent e){
		if(!HighScoreList.addingScore){
			//System.out.println(e.getX() + " " + e.getY());
			Button[] buttons = panel.getButtons();
			for(int i = 0; i < buttons.length; i++){	
				if(buttons[i] != null && buttons[i].getHitBox().intersects(e.getX(), e.getY()) 
						&& buttons[i].getAllowedPress() == panel.getGameMode()){
					panel.setGameMode(buttons[i].getText());
				}
			}//end for loop
		}
	}
	
	public void mouseMoved(MouseEvent e){
		Button[] buttons = panel.getButtons();
		for(int i = 0; i < buttons.length; i++){
			
			if(panel.getGameMode() == buttons[i].getAllowedPress() && 
					buttons[i].getHitBox().intersects(e.getX(), e.getY())){
				if(!buttons[i].isRotating()){
					buttons[i].startRotation();
				}
			}
			else{
				buttons[i].stopRotation();
			}	
			
		}//end for loop
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseExited(arg0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseReleased(arg0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(arg0);
	}
}
