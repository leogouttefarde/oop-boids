import java.util.LinkedList;

public class Schelling extends ExtendedAutomaton {
	
	private int k;
	private LinkedList<Cell> vacantHouse;


	public Schelling(int n, int m, int states, int k) {
		this(n, m, states, k, 0);
	}

	public Schelling(int n, int m, int states, int k, int defaultState) {
		super(n, m, states, defaultState);

		this.k = k;
		vacantHouse = new LinkedList<Cell>();
	}
	
	public void initVacantHouse(){
		vacantHouse.clear();
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				if(cells[x][y] == defaultState)
					vacantHouse.add(new Cell(x, y, defaultState));
			}
		}
	}

	public void nextGeneration() {

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				nextCells[x][y] = cells[x][y];
			}
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				final int cellState = cells[x][y];

				if (cellState == defaultState)
					continue;

				int nbNeighbors = 0;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {

						if (i != 1 || j != 1) {
							final int nx = getNeighbor(x, i, n);
							final int ny = getNeighbor(y, j, m);

							final int state = cells[nx][ny];

							if (state != cellState && state != defaultState) {
									nbNeighbors++;
							}
						}
					}
				}

				if(nbNeighbors > k) {
					nextCells[x][y] = defaultState;
					vacantHouse.add(new Cell(x, y, defaultState));

					Cell c = vacantHouse.remove();
					nextCells[c.getX()][c.getY()] = cellState;
				}
			}
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		initVacantHouse();
	}

	public String toString() {
		String str = new String("Schelling("+n+", "+m+")\n");

		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}
}
