/*
 * Authored by Daniel Sanchez and Trent Douglas
 */

package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

class DetectiveNotes extends JDialog {
	//ArrayLists of cards to be used throughout class
	private ArrayList<String> peopleList = new ArrayList<String>();
	private ArrayList<String> roomList = new ArrayList<String>();
	private ArrayList<String> weaponList = new ArrayList<String>();

	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(new Dimension (500, 750));
		setLayout(new GridLayout(3,2));
		makeLists();

		//Adds people check to JDialog
		JPanel panel = peopleCheck();
		add(panel);

		//Adds people guess to JDialog
		panel = peopleGuess();
		add(panel);

		//Adds room check to JDialog
		panel = roomCheck();
		add(panel);

		//Adds room guess to JDialog
		panel = roomGuess();
		add(panel);

		//Adds weapon check to JDialog
		panel = weaponCheck();
		add(panel);

		//Adds weapon guess to JDialog
		panel = weaponGuess();
		add(panel);
		
	}

	//Creates the card lists to be used throughout class
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

	//Creates the "People Check" JPanel
	private JPanel peopleCheck() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		for (int i = 0; i < peopleList.size(); i++) {
			JCheckBox checkbox = new JCheckBox(peopleList.get(i));
			panel.add(checkbox);
		}
		
		//panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		return panel;
	}

	//Creates the "People Guess" JPanel
	private JPanel peopleGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JComboBox<String> personCombo = new JComboBox<String>();
		
		for (int i = 0; i < peopleList.size(); i++) {
			personCombo.addItem(peopleList.get(i));
		}
		
		personCombo.addItem("Unsure");
		
		panel.add(personCombo);
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		
		return panel;
	}

	//Creates the "Room Check" JPanel
	private JPanel roomCheck() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,2));

		for (int i = 0; i < roomList.size(); i++) {
			JCheckBox checkbox = new JCheckBox(roomList.get(i));
			panel.add(checkbox);
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		return panel;
	}

	//Creates the "Room Guess" JPanel
	private JPanel roomGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JComboBox<String> roomCombo = new JComboBox<String>();
		
		for (int i = 0; i < roomList.size(); i++) {
			roomCombo.addItem(roomList.get(i));
		}
		
		roomCombo.addItem("Unsure");
		
		panel.add(roomCombo);
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		
		return panel;
	}

	//Creates the "Weapon Check" JPanel
	private JPanel weaponCheck() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));

		for (int i = 0; i < weaponList.size(); i++) {
			JCheckBox checkbox = new JCheckBox(weaponList.get(i));
			panel.add(checkbox);
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		return panel;
	}
	
	//Creates the "Weapon Guess" JPanel
	private JPanel weaponGuess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JComboBox<String> weaponCombo = new JComboBox<String>();
		
		for (int i = 0; i < weaponList.size(); i++) {
			weaponCombo.addItem(weaponList.get(i));
		}
		
		weaponCombo.addItem("Unsure");
		
		panel.add(weaponCombo);
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		
		return panel;
	}
	
}