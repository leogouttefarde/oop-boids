package group;

import java.util.ArrayList;

/**
 * Classe abstraite d'automate regroupant les parties communes de chacun.
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public abstract class Automaton {

	protected int n;
	protected int m;
	protected int defaultState;
	protected int states;
	protected int cells[][];
	protected ArrayList<Cell> beginning;

	/**
	 * Crée un automate cellulaire de taille n x m avec {@code states} états,
	 * et d'état par défaut {@code defaultState}.
	 * 
	 * @param n				Nombre de cellules en largeur
	 * @param m 			Nombre de cellules en hauteur
	 * @param states		Nombre d'états
	 * @param defaultState	Etat par défaut
	 */
	public Automaton(int n, int m, int states, int defaultState) {
		this.n = n;
		this.m = m;
		this.states = states;
		this.defaultState = defaultState;
		
		cells = new int[n][m];
		beginning = new ArrayList<Cell>();
	}

	/**
	 * Crée un automate cellulaire de taille size x size avec {@code states} états,
	 * et d'état par défaut {@code defaultState}.
	 * 
	 * @param size			Nombre de cellules en hauteur / largeur
	 * @param states		Nombre d'états
	 * @param defaultState	Etat par défaut
	 */
	public Automaton(int size, int states, int defaultState) {
		this(size, size, states, defaultState);
	}


	/**
	 * Retourne le tableau de cellules courant de l'automate.
	 * 
	 * @return Tableau de cellules n x m
	 */
	public int[][] getCells() {
		return cells;
	}

	/**
	 * Définit une cellule dans l'état {@code state} à la position (x, y)
	 * 
	 * @param x		Position en x
	 * @param y		Position en y
	 * @param state	Etat (compris entre 0 et states-1)
	 */
	public void add(int x, int y, int state) {
		beginning.add(new Cell(x, y, state));
		cells[x][y] = state;
	}

	/**
	 * Méthode abstraite de réinitialisation de l'automate.
	 */
	public abstract void reset();

	/**
	 * Méthode abstraite appelée après la génération de l'état suivant pour
	 * finaliser les cellules.
	 */
	protected abstract void finishGeneration();


	/**
	 * Méthode indiquant quel voisin de cellule est à prendre en compte pour
	 * quelle cellule.
	 * 
	 * @param cell		Etat de la cellule
	 * @param neighbor	Etat du voisin
	 * 
	 * @return	Indique s'il faut prendre ce voisin en compte ou non
	 */
	protected abstract boolean isNeighborMatch(int cell, int neighbor);
	
	/**
	 * Méthode effectuant les traitement requis après avoir calculé le nombre
	 * de voisins d'une cellule.
	 * 
	 * @param x				Position en x de la cellule
	 * @param y				Position en y de la cellule
	 * 
	 * @param nbNeighbors	Nombre de voisins détectés
	 */
	protected abstract void endCellGen(int x, int y, int nbNeighbors);

	/**
	 * Fonction permettant d'effectuer des opérations avant la génération
	 * de l'état suivant (vide sauf pour Schelling).
	 */
	protected void preGeneration() {}

	/**
	 * Fonction permettant d'ignorer une cellule qu'il n'y a pas besoin
	 * de générer (pour Schelling).
	 * 
	 * @param cell	Etat de la cellule concernée
	 * 
	 * @return	Indique s'il faut l'ignorer
	 */
	protected boolean skipCellGen(int cell) {
		return false;
	}

	/**
	 * Fonction générique de génération de l'état suivant.
	 */
	protected void nextGeneration() {
		int nbNeighbors;

		preGeneration();

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				final int cell = cells[x][y];

				// S'il faut générer la cellule courante
				if (!skipCellGen(cell)) {
					nbNeighbors = 0;

					// Observer tous les voisins
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {

							// Eviter la cellule courante
							if (i != 1 || j != 1) {
								final int nx = getNeighbor(x, i, n);
								final int ny = getNeighbor(y, j, m);

								// Si un voisin est concerné, on le compte
								if (isNeighborMatch(cell, cells[nx][ny]))
									nbNeighbors++;
							}
						}
					}

					endCellGen(x, y, nbNeighbors);
				}
			}
		}
	}

	/**
	 * Retourne la position du voisin numéro {@code n} d'une cellule
	 * en position cell, pour une longueur maximale donnée.
	 * 
	 * @param cell	Position de la cellule
	 * @param n		Numéro du voisin
	 * @param max	Longueur maximale
	 * 
	 * @return	Position du voisin
	 */
	protected int getNeighbor(int cell, int n, int max) {

		int pos = cell + n;

		if (pos == 0)
			pos = max - 1;

		else
			pos = (pos - 1) % max;

		return pos;
	}

	/**
	 * Passe à la prochaine génération de cellules.
	 */
	public void generate() {
		nextGeneration();
		finishGeneration();
	}

	public String toString() {
		return "Automaton("+n+", "+m+")";
	}


	/**
	 * Type de cellule permettant leur mémorisation dans un état précis.
	 * 
	 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
	 */
	public class Cell {
		private int x;
		private int y;
		private int state;

		/**
		 * Crée une cellule
		 * 
		 * @param x		Position en x
		 * @param y		Position en y
		 * @param state	Etat
		 */
		public Cell(int x, int y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}
		
		/**
		 * Getter x
		 * 
		 * @return Position en x
		 */
		public int getX(){
			return x;
		}

		/**
		 * Getter y
		 * 
		 * @return Position en y
		 */
		public int getY(){
			return y;
		}

		/**
		 * Getter d'état
		 * 
		 * @return Etat
		 */
		public int getState(){
			return state;
		}

		public String toString() {
			return "Cell(" + x + ", " + y + ") : " + state;
		}
	}
}
