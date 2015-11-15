package group;

/**
 * Jeu de l'Immigration
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class Immigration extends ExtendedAutomaton {

	/**
	 * Crée un jeu de l'Immigration
	 * 
	 * @param n			Nombre de cases en largeur
	 * @param m			Nombre de cases en hauteur
	 * @param states	Nombre d'états
	 */
	public Immigration(int n, int m, int states) {
		this(n, m, states, 0);
	}

	/**
	 * Crée un jeu de l'Immigration en spécifiant l'état par défaut
	 * 
	 * @param n				Nombre de cases en largeur
	 * @param m				Nombre de cases en hauteur
	 * @param states		Nombre d'états
	 * @param defaultState	Etat par défaut
	 */
	public Immigration(int n, int m, int states, int defaultState) {
		super(n, m, states, defaultState);	
	}


	/* (non-Javadoc)
	 * @see group.Automaton#isNeighborMatch(int, int)
	 */
	protected boolean isNeighborMatch(int cell, int neighbor) {
		final int next = (cell + 1) % states;
		boolean success = false;

		if (neighbor == next)
			success = true;

		return success;
	}

	/* (non-Javadoc)
	 * @see group.Automaton#endCellGen(int, int, int)
	 */
	protected void endCellGen(int x, int y, int nbNeighbors) {
		final int state = cells[x][y];
		final int next = (state + 1) % states;

		if (nbNeighbors >= 3)
			nextCells[x][y] = next;

		else
			nextCells[x][y] = state;
	}

	/* (non-Javadoc)
	 * @see group.ExtendedAutomaton#toString()
	 */
	public String toString() {
		String str = new String("Immigration("+n+", "+m+")\n");
		
		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}
}

