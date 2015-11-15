package simulator;

import group.Immigration;

import java.awt.Color;
import gui.GUISimulator;

/**
 * Simulateur du jeu de l'Immigration
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class ImmigrationSimulator extends AutomatonSimulator {

	/**
	 * Crée un simulateur du jeu de l'Immigration
	 * 
	 * @param gui		Simulateur de l'interface graphique liée
	 * @param imm		Jeu de l'Immigration à simuler
	 */
	public ImmigrationSimulator(GUISimulator g, Immigration imm) {
		super(g, imm);
	}

	/* (non-Javadoc)
	 * @see simulator.AutomatonSimulator#getCellColor(int, int)
	 */
	protected Color getCellColor(int x, int y) {
		final int k = 255 - 70 * cells[x][y];

		return new Color(k, k, k);
	}
}
