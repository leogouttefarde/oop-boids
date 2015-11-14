import java.util.ArrayList;

public abstract class Automaton {

	protected int n;
	protected int m;
	protected int defaultState;
	protected int states;
	protected int cells[][];
	protected ArrayList<Cell> beginning;

	public Automaton(int n, int m, int states, int defaultState) {
		this.n = n;
		this.m = m;
		this.states = states;
		this.defaultState = defaultState;
		
		cells = new int[n][m];
		beginning = new ArrayList<Cell>();
	}

	public Automaton(int size, int states, int defaultState) {
		this(size, size, states, defaultState);
	}

	public int[][] getCells() {
		return cells;
	}

	public void add(int x, int y, int state) {
		beginning.add(new Cell(x, y, state));
		cells[x][y] = state;
	}

	public abstract void reset();
	protected abstract void nextGeneration();
	protected abstract void finishGeneration();

	protected int getNeighbor(int cell, int n, int max) {

		int pos = cell + n;

		if (pos == 0)
			pos = max - 1;

		else
			pos = (pos - 1) % max;

		return pos;
	}

	public void generate() {
		nextGeneration();
		finishGeneration();
	}

	public String toString() {
		return "Automaton("+n+", "+m+")";
	}


	public class Cell {
		private int x;
		private int y;
		private int state;

		public Cell(int x, int y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public int getState(){
			return state;
		}

		public String toString() {
			return "Cell(" + x + ", " + y + ") : " + state;
		}
	}
}
