package lecture10_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener {

	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JLabel wordLabel;
	private JTextField wordTextField;
	private JButton startbtn;
	private JButton stopbtn;

	private int timerLeft;
	private int score;
	private boolean running;
	String[] data;
	Timer timer;

	public TypingTutor(String feeder) {
		GridLayout gd = new GridLayout(3, 2);
		super.setLayout(gd);

		Font font = new Font("Comic Sans MS", 1, 50);

		timerLabel = new JLabel();
		timerLabel.setFont(font);
		timerLabel.setText("Timer:");
		super.add(timerLabel);

		scoreLabel = new JLabel();
		scoreLabel.setFont(font);
		scoreLabel.setText("Score:");
		super.add(scoreLabel);

		wordLabel = new JLabel();
		wordLabel.setOpaque(true);
		wordLabel.setForeground(Color.white);
		wordLabel.setBackground(Color.orange);
		wordLabel.setFont(font);
		wordLabel.setText("");
		super.add(wordLabel);

		wordTextField = new JTextField();
		wordTextField.setFont(font);
		wordTextField.setText("");
		super.add(wordTextField);

		startbtn = new JButton();
		startbtn.setFont(font);
		startbtn.addActionListener(this);
		startbtn.setText("START");
		super.add(startbtn);

		stopbtn = new JButton();
		stopbtn.setFont(font);
		stopbtn.addActionListener(this);
		stopbtn.setText("STOP");
		super.add(stopbtn);

		super.setResizable(false); // to disable maximize button
		super.setTitle("Typing Tutor");
		super.setVisible(true);
		super.setSize(1000, 1000);

		data = feeder.split(" ");
		intitGame();
	}

	private void intitGame() {
		timerLeft = 50;
		running = false;
		score = 0;
		timer = new Timer(2000, this);

		timerLabel.setText("Timer:" + timerLeft);
		scoreLabel.setText("Score" + score);
		wordLabel.setText("");
		wordTextField.setText("");

		startbtn.setText("START");
		stopbtn.setText("STOP");
		stopbtn.setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startbtn) {
			handleStart();
		} else if (e.getSource() == stopbtn) {
			handleStop();
		} else if (e.getSource() == timer) {
			handleTimer();
		}
	}

	private void handleStart() {

		if (running) {
			running = false;

			timer.stop();
			wordTextField.setEditable(false);
			timerLabel.setText("Timer:" + timerLeft);
			scoreLabel.setText("Score" + score);

			startbtn.setText("RESUME");
			stopbtn.setText("STOP");
		} else {
			running = true;

			timer.start();
			stopbtn.setEnabled(true);
			wordTextField.setEditable(true);
			timerLabel.setText("Timer:" + timerLeft);
			scoreLabel.setText("Score" + score);

			startbtn.setText("PAUSE");
			stopbtn.setText("STOP");

		}
	}

	private void handleStop() {
		JOptionPane options = new JOptionPane();
		int choice = options.showConfirmDialog(this, "GAME OVER. Your Score is:" + score + " .Restart?");
		if (choice == JOptionPane.YES_OPTION) {
			intitGame();
		} else {
			dispose();
		}
	}

	private void handleTimer() {
		if (timerLeft > 0) {
			timerLeft--;

			wordTextField.setFocusable(true);

			String expected = wordLabel.getText();
			String actual = wordTextField.getText();

			if (expected.equals(actual) && expected.length() > 0) {
				score++;
			}

			String nextWord = data[(int) (Math.random() * data.length)];
			wordLabel.setText(nextWord);
			wordTextField.setText("");
			timerLabel.setText("Timer:" + String.valueOf(timerLeft));
			String scoreString = String.valueOf(score);
			scoreLabel.setText("Score:" + scoreString);

		} else {
			handleStop();
		}
	}

}
