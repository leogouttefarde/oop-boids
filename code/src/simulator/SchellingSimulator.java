package simulator;

import group.Schelling;
import java.awt.Color;
import gui.GUISimulator;

/**
 * Simulateur du modèle de Schelling
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class SchellingSimulator extends AutomatonSimulator {

	private Color colors[];

	/**
	 * Crée un simulateur du modèle de Schelling
	 * 
	 * @param gui		Simulateur de l'interface graphique liée
	 * @param sch		Modèle de Schelling à simuler
	 * @param colors	Couleurs des états
	 */
	public SchellingSimulator(GUISimulator gui, Schelling sch, Color colors[]) {
		super(gui, sch);
		this.colors = colors;
	}

	/* (non-Javadoc)
	 * @see simulator.AutomatonSimulator#getCellColor(int, int)
	 */
	protected Color getCellColor(int x, int y) {
		return colors[ cells[x][y] ];
	}
}

