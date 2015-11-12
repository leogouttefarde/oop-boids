public abstract class ExtendedCellularAutomaton extends CellularAutomaton{
	
	protected int nextCells[][];
	
	public ExtendedCellularAutomaton(int n, int m, int states, int defaultState) {
		super(n, m, states, defaultState);
		nextCells = new int[n][m];
		initCellsAndNextCells();
	}
	public ExtendedCellularAutomaton(int size, int states, int defaultState) {
		super(size, states, defaultState);
		nextCells = new int[n][m];
		initCellsAndNextCells();
	}
	
	protected void initCellsAndNextCells(){
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				cells[x][y] = defaultState;
				nextCells[x][y] = defaultState;
			}
		}
	}
	
	protected abstract void nextGeneration();

	public void finishGeneration() {
		int tmp[][] = cells;
		cells = nextCells;
		nextCells = tmp;
	}
	
	public void reset() {
		initCellsAndNextCells();
		
		for (Cell c : initialCells) {
			// System.out.println(c.x+", "+c.y);
			cells[c.x][c.y] = c.state;
		}
	}

	public String toString() {
		String str = new String("ExtendedCellularAutomaton("+n+", "+m+")\n");
		
		for (Cell c : initialCells) {
			str += c.x+ ", " + c.y+ ", " + c.state + "\n";
		}

		return str;
	}


}
