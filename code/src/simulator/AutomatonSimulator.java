package simulator;

import group.Automaton;
import event.AutomatonEvent;
import event.EventManager;
import java.awt.Color;
import gui.GUISimulator;
import gui.Simulable;
import gui.Rectangle;

/**
 * Simulateur d'automate générique
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public abstract class AutomatonSimulator implements Simulable {

	private final int BORDER = 10;
	private final int DIM = 12;
	private final int SIZE = DIM - 2;

	protected GUISimulator gui;
	protected Automaton automaton;
	protected int cells[][];

	/**
	 * Crée un simulateur d'automate
	 * 
	 * @param gui		Simulateur de l'interface graphique liée
	 * @param automaton	Automate à simuler
	 */
	public AutomatonSimulator(GUISimulator gui, Automaton automaton) {
		this.gui = gui;
		this.automaton = automaton;

		EventManager.Get().addEvent(new AutomatonEvent(1, automaton));
	}

	/**
	 * Construit le rectangle affichable d'une cellule
	 * 
	 * @param x		Position en x
	 * @param y		Position en y
	 * @param col	Couleur
	 * 
	 * @return	Cellule affichable
	 */
	protected Rectangle viewCell(int x, int y, Color col) {
		return new Rectangle(BORDER + x * DIM, BORDER + y * DIM, col, col, SIZE);
	}

	/**
	 * Indique la couleur d'une cellule
	 * 
	 * @param x	Position en x
	 * @param y	Position en y
	 * @return	Couleur
	 */
	protected abstract Color getCellColor(int x, int y);

	/**
	 * Met à jour l'affichage
	 */
	protected void updateFrame() {
		cells = automaton.getCells();

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				gui.addGraphicalElement(viewCell(x, y, getCellColor(x, y)));
			}
		}
	}

	/**
	 * Avance la simulation d'un pas
	 */
	@Override
	public void next() {
		EventManager.Get().next();
		gui.reset();
		updateFrame();
	}

	/**
	 * Relance la simulation
	 */
	@Override
	public void restart() {
		automaton.reset();
		gui.reset();
		updateFrame();
	}
}
