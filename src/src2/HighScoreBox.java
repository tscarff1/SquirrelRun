package src2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HighScoreBox extends JFrame{
	
	private final int WIDTH = 300;
	private final int HEIGHT = 100;
	
	public HighScoreBox(final int score){
		HighScoreList.addingScore = true;
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Squirrel Run - New High Score!");
		
		setResizable(false);
		setVisible(true);
		setLayout(new BorderLayout());
		JLabel label = new JLabel("Congratulations! You are number  " + (HighScoreList.getPlace(score) + 1) + "!");
		label.setSize(WIDTH, 60);
		JLabel label2 = new JLabel("Please enter your name:");
		label2.setSize((int) (WIDTH*1.0/3), 10);
		final JTextField nameField = new JTextField();
		nameField.setSize((int) (WIDTH * (2.0/3)), 10);
		
		JButton button = new JButton("SUBMIT");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				HighScoreList.addScore(score, nameField.getText());
				dispose();
			}
		});
		
		add(label, BorderLayout.PAGE_START);
		add(nameField, BorderLayout.CENTER);
		add(button, BorderLayout.PAGE_END);
		add(label2, BorderLayout.LINE_START);
	
	}
}
