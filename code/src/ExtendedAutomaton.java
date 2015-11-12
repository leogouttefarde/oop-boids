

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

		for (Cell c : initialCells) {
			// System.out.println(c.x+", "+c.y);
			cells[c.x][c.y] = c.state;
		}
	}

	public String toString() {
		String str = new String("ExtendedAutomaton("+n+", "+m+")\n");
		
		for (Cell c : initialCells) {
			str += c.x+ ", " + c.y+ ", " + c.state + "\n";
		}

		return str;
	}
}
