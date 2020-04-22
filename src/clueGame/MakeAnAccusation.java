/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//Class to control the window when the player clicks the "make accusation" button
public class MakeAnAccusation extends JFrame {
	private ArrayList<String> peopleList = new ArrayList<String>();
	private ArrayList<String> roomList = new ArrayList<String>();
	private ArrayList<String> weaponList = new ArrayList<String>();
	private JTextField yourRoomLabel;
	private JTextField yourRoom;
	private JTextField personLabel;
	private JTextField weaponLabel;
	private JComboBox<String> personCombo;
	private JComboBox<String> weaponCombo;
	private JComboBox<String> roomCombo;
	private JButton submitButton;
	private JButton cancelButton;
	private Board board = Board.getInstance();
	private String person = "";
	private String weapon = "";
	public String getPerson() {
		return person;
	}

	public String getWeapon() {
		return weapon;
	}

	public String getRoom() {
		return room;
	}

	private String room = "";

	public MakeAnAccusation() {
		makeLists();
		setSize(400, 400);
		ComboListener listener = new ComboListener();
		ButtonListener buttonListener = new ButtonListener();
		setLayout(new GridLayout(4,2));
		yourRoomLabel = new JTextField(20);
		yourRoomLabel.setText("Room: ");
		yourRoomLabel.setEditable(false);
		yourRoom = new JTextField(20);
		if (board.getCurrentPlayerIndex() != -1) {
			yourRoom.setText(board.getLegend().get(board.getCellAt(board.getPlayers()[board.getCurrentPlayerIndex()].getRow(), board.getPlayers()[board.getCurrentPlayerIndex()].getColumn()).getInitial()));
		} else {
			yourRoom.setText("Press \"Next Player\" Button");
		}
		yourRoom.setEditable(false);
		personLabel = new JTextField(20);
		personLabel.setText("Person: ");
		personLabel.setEditable(false);
		weaponLabel = new JTextField(20);
		weaponLabel.setText("Weapon: ");
		weaponLabel.setEditable(false);
		personCombo = new JComboBox<String>();
		personCombo.addActionListener(listener);
		weaponCombo = new JComboBox<String>();
		weaponCombo.addActionListener(listener);
		roomCombo = new JComboBox<String>();
		roomCombo.addActionListener(listener);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(buttonListener);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(buttonListener);
		for (int i = 0; i < peopleList.size(); i++) {
			personCombo.addItem(peopleList.get(i));
		}
		for (int i = 0; i < weaponList.size(); i++) {
			weaponCombo.addItem(weaponList.get(i));
		}
		for (int i = 0; i < roomList.size(); i++) {
			roomCombo.addItem(roomList.get(i));
		}

		add(yourRoomLabel);
		add(roomCombo);
		add(personLabel);
		add(personCombo);
		add(weaponLabel);
		add(weaponCombo);
		add(submitButton);
		add(cancelButton);
		if ( board.getCurrentPlayerIndex() > -1) {
			setVisible(true);
		}


	}

	public void destroyWindow() {
		setVisible(false);
		dispose();
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


	private class ComboListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == personCombo) {
				person = personCombo.getSelectedItem().toString();
			}
			if (e.getSource() == weaponCombo) {
				weapon = weaponCombo.getSelectedItem().toString();
			}
			if (e.getSource() == roomCombo) {
				room = roomCombo.getSelectedItem().toString();
			}
		}
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submitButton) {

				Solution solution = new Solution(person, room, weapon);
				if (board.checkAccusation(solution)) {
					JOptionPane.showMessageDialog(submitButton, "You have won the game!");
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(submitButton, "That is incorrect!");
				}
				destroyWindow();
			}
			if (e.getSource() == cancelButton) {
				destroyWindow();
			}

		}

	}





}
