package test;

import group.Immigration;
import simulator.ImmigrationSimulator;
import gui.GUISimulator;
import java.awt.Color;

/**
 * Classe de démarrage du test des classes Immigration et ImmigrationSimulator. 
 * (Question 6 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Immigration
 * @see ImmigrationSimulator
 */
public class TestImmigrationSimulator {

	/**
	 * Méthode main de démarrage du test des classes Immigration et ImmigrationSimulator.
	 * @param args Arguments (non utilisés)
	 */
	public static void main(String[] args) {

		Immigration imm = new Immigration(70, 40, 4);
		
		imm.add(1, 1, 0);
		imm.add(1, 2, 0);
		imm.add(1, 3, 0);
		imm.add(7, 7, 1);
		imm.add(8, 7, 1);
		imm.add(9, 7, 1);
		imm.add(7, 10, 2);
		imm.add(8, 10, 2);
		imm.add(9, 10, 2);
		imm.add(7, 11, 2);
		imm.add(8, 11, 2);
		imm.add(9, 11, 2);
		imm.add(7, 12, 2);
		imm.add(7, 8, 3);
		imm.add(7, 9, 3);
		imm.add(8, 9, 3);
		imm.add(9, 9, 3);

		imm.add(19, 30, 3);
		imm.add(20, 30, 3);
		imm.add(20, 31, 3);
		imm.add(20, 30, 3);
		imm.add(31, 22, 3);
		imm.add(21, 31, 3);
		
		System.out.println(imm);

		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new ImmigrationSimulator(gui, imm));
	}
}
