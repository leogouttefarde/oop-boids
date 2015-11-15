package simulator;

import group.Life;
import java.awt.Color;
import gui.GUISimulator;

/**
 * Simulateur du jeu de la vie de Conway
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class LifeSimulator extends AutomatonSimulator {

	/**
	 * Crée un simulateur du jeu de la vie
	 * 
	 * @param gui		Simulateur de l'interface graphique liée
	 * @param life		Jeu de la vie à simuler
	 */
	public LifeSimulator(GUISimulator g, Life life) {
		super(g, life);
	}

	/* (non-Javadoc)
	 * @see simulator.AutomatonSimulator#getCellColor(int, int)
	 */
	protected Color getCellColor(int x, int y) {
		Color color;

		switch (cells[x][y]) {
			case Life.ALIVE:
				color = Color.BLUE;
				break;

			case Life.DEAD:
			default:
				color = Color.GRAY;
				break;
		}

		return color;
	}
}
