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

	public static final Color[] COLORS = { Color.WHITE,
		Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
		Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
		Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };

	/**
	 * Crée un simulateur du jeu de l'Immigration
	 * 
	 * @param gui		Simulateur de l'interface graphique liée
	 * @param imm		Jeu de l'Immigration à simuler
	 */
	public ImmigrationSimulator(GUISimulator gui, Immigration imm) {
		super(gui, imm);
	}

	/* (non-Javadoc)
	 * @see simulator.AutomatonSimulator#getCellColor(int, int)
	 */
	protected Color getCellColor(int x, int y) {
		final int state = cells[x][y];
		Color color;

		if (state >= COLORS.length)
			color = new Color(state, state, state);

		else
			color = COLORS[state];

		return color;
	}
}
