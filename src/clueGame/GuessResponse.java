package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessResponse extends JPanel{
	
	private JTextField response = new JTextField(10);
	Board board = Board.getInstance();

	public GuessResponse() {
		setLayout(new GridLayout(1,1));

		JLabel title = new JLabel("Response");
		response.setEditable(false);
		add(title);
		add(response);
		setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
	}
	
	public void updateText() {
		if (board.getCurrentPlayerIndex() != -1) {
			response.setText("no");
		}
		else {
			response.setText("no");
		}
	}
	

}
