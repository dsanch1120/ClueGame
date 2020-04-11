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
	private ArrayList<String> peopleList = new ArrayList<String>();
	private ArrayList<String> roomList = new ArrayList<String>();
	private ArrayList<String> weaponList = new ArrayList<String>();

	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(new Dimension (500, 750));
		setLayout(new GridLayout(3,2));
		makeLists();

		JPanel panel = peopleCheck();
		add(panel);

		
		panel = peopleGuess();
		add(panel);

		panel = roomCheck();
		add(panel);

		panel = roomGuess();
		add(panel);

		panel = weaponCheck();
		add(panel);

		panel = weaponGuess();
		add(panel);
		
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