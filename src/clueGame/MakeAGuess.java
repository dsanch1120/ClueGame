package clueGame;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MakeAGuess extends JFrame{
	private ArrayList<String> peopleList = new ArrayList<String>();
	private ArrayList<String> roomList = new ArrayList<String>();
	private ArrayList<String> weaponList = new ArrayList<String>();
	JTextField yourRoomLabel;
	JTextField yourRoom;
	JTextField personLabel;
	JTextField weaponLabel;
	JComboBox<String> personCombo;
	JComboBox<String> weaponCombo;
	JButton submitButton;
	JButton cancelButton;
	Board board = Board.getInstance();


	public MakeAGuess() {
		makeLists();
		setSize(400, 400);
		
		setLayout(new GridLayout(4,2));
		yourRoomLabel = new JTextField(20);
		yourRoomLabel.setText("Your room: ");
		yourRoomLabel.setEditable(false);
		yourRoom = new JTextField(20);
		yourRoom.setText(board.getLegend().get(board.getCellAt(board.getPlayers()[board.getCurrentPlayerIndex()].getRow(), board.getPlayers()[board.getCurrentPlayerIndex()].getColumn()).getInitial()));
		yourRoom.setEditable(false);
		personLabel = new JTextField(20);
		personLabel.setText("Person: ");
		personLabel.setEditable(false);
		weaponLabel = new JTextField(20);
		weaponLabel.setText("Weapon: ");
		weaponLabel.setEditable(false);
		personCombo = new JComboBox<String>();
		weaponCombo = new JComboBox<String>();
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		for (int i = 0; i < peopleList.size(); i++) {
			personCombo.addItem(peopleList.get(i));
		}
		for (int i = 0; i < weaponList.size(); i++) {
			weaponCombo.addItem(weaponList.get(i));
		}
		
		add(yourRoomLabel);
		add(yourRoom);
		add(personLabel);
		add(personCombo);
		add(personCombo);
		add(weaponLabel);
		add(weaponCombo);
		add(submitButton);
		add(cancelButton);

		setVisible(true);
		
	
	}

	private void makeLists() {
		try {
			File file = new File("data/cards.txt");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(file);
			int counter = 0;

			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				if (counter < 9) {
					roomList.add(line[0]);
				}
				else if (counter >= 9 && counter < 15) {
					peopleList.add(line[0]);
				}
				else {
					weaponList.add(line[0]);
				}
				counter++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}






}
