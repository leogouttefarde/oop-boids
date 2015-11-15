package group;

/**
 * Classe abstraite d'automate cellulaire étendu.
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public abstract class ExtendedAutomaton extends Automaton {
	
	protected int nextCells[][];


	/**
	 * Crée un automate cellulaire de taille n x m avec {@code states} états,
	 * et d'état par défaut {@code defaultState}.
	 * 
	 * @param n				Nombre de cellules en largeur
	 * @param m 			Nombre de cellules en hauteur
	 * @param states		Nombre d'états
	 * @param defaultState	Etat par défaut
	 */
	public ExtendedAutomaton(int n, int m, int states, int defaultState) {
		super(n, m, states, defaultState);
		nextCells = new int[n][m];
	}

	/**
	 * Crée un automate cellulaire de taille size x size avec {@code states} états,
	 * et d'état par défaut {@code defaultState}.
	 * 
	 * @param size			Nombre de cellules en hauteur / largeur
	 * @param states		Nombre d'états
	 * @param defaultState	Etat par défaut
	 */
	public ExtendedAutomaton(int size, int states, int defaultState) {
		this(size, size, states, defaultState);
	}

	/**
	 * Echange les tableaux cells et nextCells après chaque génération.
	 * 
	 * @see group.Automaton#finishGeneration()
	 */
	public void finishGeneration() {
		int tmp[][] = cells;
		cells = nextCells;
		nextCells = tmp;
	}

	/*
	 * @see group.Automaton#reset()
	 */
	@Override
	public void reset() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				cells[x][y] = defaultState;
			}
		}

		for (Cell c : beginning) {
			cells[c.getX()][c.getY()] = c.getState();
		}
	}

	/* (non-Javadoc)
	 * @see group.Automaton#toString()
	 */
	public String toString() {
		String str = new String("ExtendedAutomaton("+n+", "+m+")\n");
		
		for (Cell c : beginning) {
			str += c.getX()+ ", " + c.getY() + ", " + c.getState() + "\n";
		}

		return str;
	}
}
