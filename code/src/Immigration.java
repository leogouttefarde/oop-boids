import java.util.ArrayList;

public class Immigration extends Conway {

	private int defaultState;
	private int states;
	private int cells[][];
	private int nextCells[][];
	private ArrayList<Cell> initialCells;


	public Immigration(int n, int m, int states) {
		this(n, m, states, 0);
	}

	public Immigration(int n, int m, int states, int defaultState) {
		super(n, m);

		this.states = states;
		this.defaultState = defaultState;
		cells = new int[n][m];
		nextCells = new int[n][m];
		initialCells = new ArrayList<Cell>();
		reset();
	}


	public int[][] getCells() {
		return cells;
	}

	public void addCell(int x, int y, int state) {
		initialCells.add(new Cell(x, y, state));
		cells[x][y] = state;
	}

	public void nextGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				int nbNeighbors = 0;
				final int k = cells[x][y];
				final int kp = (k + 1) % states;
				// System.out.println(k+", "+kp);

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {

						if (i != 1 || j != 1) {
							int nx = getNeighbor(x, i, n);
							int ny = getNeighbor(y, j, m);

							//System.out.println(x+", "+y + ", "+k + ", "+i + ", "+j + " :  "+ nx+", "+ny+", "+cells[nx][ny]);
							if (cells[nx][ny] == kp)
								nbNeighbors++;
						}
					}
				}

				if (nbNeighbors >= 3)
					nextCells[x][y] = kp;

				else
					nextCells[x][y] = k;
			}
		}

	}

	public void finishGeneration() {
		int tmp[][] = cells;
		cells = nextCells;
		nextCells = tmp;
	}

	public void reset() {
		// Arrays.fill(cells, new State[m]);
		for (int i = 0; i < n; i++) {
			// cells[i] = new State[m];
			for (int j = 0; j < m; j++) {
				cells[i][j] = defaultState;
			}
		}

		for (Cell c : initialCells) {
			// System.out.println(c.x+", "+c.y);
			cells[c.x][c.y] = c.state;
		}
	}

	public String toString() {
		String str = new String("Immigration("+n+", "+m+")\n");

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
