import java.util.ArrayList;

public abstract class CellularAutomaton {

	protected int n;
	protected int m;
	protected int defaultState;
	protected int states;
	protected int cells[][];
	protected ArrayList<Cell> initialCells;

	public CellularAutomaton(int n, int m, int states, int defaultState) {
		this.n = n;
		this.m = m;
		this.states = states;
		this.defaultState = defaultState;
		
		cells = new int[n][m];
		initialCells = new ArrayList<Cell>();
	}

	public CellularAutomaton(int size, int states, int defaultState) {
		this(size, size, states, defaultState);
	}
	
	public int[][] getCells() {
		return cells;
	}
	
	public void addCell(int x, int y, int state) {
		initialCells.add(new Cell(x, y, state));
		cells[x][y] = state;
	}

	public abstract void reset();
	protected abstract void nextGeneration();
	protected abstract void finishGeneration();

	protected int getNeighbor(int cell, int n, int max) {

		int pos = cell + n;

		if (pos == 0)
			pos = max-1;

		else
			pos = (pos - 1) % max;

		return pos;
	}

	public void generate() {
		nextGeneration();
		finishGeneration();
	}

	public String toString() {
		return "Conway("+n+", "+m+")";
	}
	
	public class Cell {
		public int x;
		public int y;
		public int state;

		public Cell(int x, int y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}

	}
}
