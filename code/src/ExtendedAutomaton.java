public abstract class ExtendedAutomaton extends Automaton {
	
	protected int nextCells[][];
	
	public ExtendedAutomaton(int n, int m, int states, int defaultState) {
		super(n, m, states, defaultState);
		nextCells = new int[n][m];
	}

	public ExtendedAutomaton(int size, int states, int defaultState) {
		this(size, size, states, defaultState);
	}

	protected abstract void nextGeneration();

	public void finishGeneration() {
		int tmp[][] = cells;
		cells = nextCells;
		nextCells = tmp;
	}

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

	public String toString() {
		String str = new String("ExtendedAutomaton("+n+", "+m+")\n");
		
		for (Cell c : beginning) {
			str += c.getX()+ ", " + c.getY() + ", " + c.getState() + "\n";
		}

		return str;
	}
}
