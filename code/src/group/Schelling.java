package group;

import java.util.LinkedList;

/**
 * Modèle de Schelling
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class Schelling extends ExtendedAutomaton {
	
	private int K;
	private LinkedList<Cell> vacantHouses;
	private boolean init;


	/**
	 * Crée un modèle de Shelling de taille n x m, avec {@code states} états et de seuil K
	 * 
	 * @param n				Nombre de cellules en largeur
	 * @param m 			Nombre de cellules en hauteur
	 * @param states		Nombre d'états
	 * @param K				Seuil
	 */
	public Schelling(int n, int m, int states, int K) {
		this(n, m, states, K, 0);
		init = true;
	}

	/**
	 * Crée un modèle de Shelling de taille n x m, avec {@code states} états, de seuil K
	 * et en spécifiant l'état par défaut.
	 * 
	 * @param n				Nombre de cellules en largeur
	 * @param m 			Nombre de cellules en hauteur
	 * @param states		Nombre d'états
	 * @param K				Seuil
	 * @param defaultState	Etat par défaut
	 */
	public Schelling(int n, int m, int states, int K, int defaultState) {
		super(n, m, states, defaultState);
		this.K = K;

		vacantHouses = new LinkedList<Cell>();
	}


	/**
	 * Initialise les maisons vacantes
	 */
	private void initVacantHouses() {
		vacantHouses.clear();

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				if(cells[x][y] == defaultState)
					vacantHouses.add(new Cell(x, y, defaultState));
			}
		}
	}

	/* (non-Javadoc)
	 * @see group.Automaton#preGeneration()
	 */
	protected void preGeneration() {

		if (init) {
			initVacantHouses();
			init = false;
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				nextCells[x][y] = cells[x][y];
			}
		}
	}

	/* (non-Javadoc)
	 * @see group.Automaton#skipCellGen(int)
	 */
	protected boolean skipCellGen(int cell) {
		boolean skip = false;

		if (cell == defaultState)
			skip = true;

		return skip;
	}

	/* (non-Javadoc)
	 * @see group.Automaton#isNeighborMatch(int, int)
	 */
	protected boolean isNeighborMatch(int cell, int neighbor) {
		boolean success = false;

		if (neighbor != cell && neighbor != defaultState) {
			success = true;
		}

		return success;
	}

	/* (non-Javadoc)
	 * @see group.Automaton#endCellGen(int, int, int)
	 */
	protected void endCellGen(int x, int y, int nbNeighbors) {
		if(nbNeighbors > K) {
			nextCells[x][y] = defaultState;
			vacantHouses.add(new Cell(x, y, defaultState));

			Cell cell = vacantHouses.remove();
			nextCells[ cell.getX() ][ cell.getY() ] = cells[x][y];
		}
	}


	/* (non-Javadoc)
	 * @see group.ExtendedAutomaton#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		initVacantHouses();
	}

	/* (non-Javadoc)
	 * @see group.ExtendedAutomaton#toString()
	 */
	public String toString() {
		String str = new String("Schelling("+n+", "+m+")\n");

		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}
}
