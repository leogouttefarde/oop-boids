import java.util.LinkedList;

public class Schelling extends ExtendedAutomaton {
	
	private int threshold;
	private LinkedList<Cell> vacantHousing;


	public Schelling(int n, int m, int states, int threshold) {
		this(n, m, states, threshold, 0);
	}

	public Schelling(int n, int m, int states, int threshold, int defaultState) {
		super(n, m, states, defaultState);

		this.threshold = threshold;
		vacantHousing = new LinkedList<Cell>();
	}
	
	public void initVacantHousing(){
		vacantHousing.clear();
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				if(cells[x][y] == defaultState)
					vacantHousing.add(new Cell(x, y, defaultState));
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

				if(nbNeighbors > threshold) {
					System.out.println(x + " " + y);
					nextCells[x][y] = defaultState;
					vacantHousing.add(new Cell(x, y, defaultState));

					Cell c = vacantHousing.remove();
					nextCells[c.x][c.y] = cellState;
				}
			}
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		initVacantHousing();
	}

	public String toString() {
		String str = new String("Schelling("+n+", "+m+")\n");

		for (Cell c : initialCells) {
			str += c + "\n";
		}

		return str;
	}
}
