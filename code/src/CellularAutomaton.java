import java.util.ArrayList;


public abstract class CellularAutomaton extends Conway{
	
	protected int defaultState;
	protected int states;
	protected int cells[][];
	protected ArrayList<Cell> initialCells;
	protected int nextCells[][];
	
	public CellularAutomaton(int n, int m, int states, int defaultState) {
		super(n, m);
		this.states = states;
		this.defaultState = defaultState;
		cells = new int[n][m];
		nextCells = new int[n][m];
		initialCells = new ArrayList<Cell>();
		initCellsAndNextCells();
		//reset();
	}
	public CellularAutomaton(int size, int states, int defaultState) {
		super(size);
		this.states = states;
		this.defaultState = defaultState;
		cells = new int[n][m];
		nextCells = new int[n][m];
		initialCells = new ArrayList<Cell>();
		initCellsAndNextCells();
		//reset();
	}
	
	protected void initCellsAndNextCells(){
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				cells[x][y] = defaultState;
				nextCells[x][y] = defaultState;
			}
		}
	}
	
	public int[][] getCells() {
		return cells;
	}
	
	public void addCell(int x, int y, int state) {
		initialCells.add(new Cell(x, y, state));
		cells[x][y] = state;
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
		String str = new String("CellularAutomaton("+n+", "+m+")\n");
		
		for (Cell c : initialCells) {
			str += c.x+ ", " + c.y+ ", " + c.state + "\n";
		}

		return str;
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
