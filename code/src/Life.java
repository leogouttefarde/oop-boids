
public class Life extends Automaton {

	public static final int ALIVE = 0;
	public static final int BIRTH = 1;
	public static final int DEAD = 2;
	public static final int DYING = 3;

	public Life(int n, int m) {
		super(n, m, 4, DEAD);
		reset();
	}

	public void add(int x, int y) {
		initialCells.add(new Cell(x, y, ALIVE));
		cells[x][y] = ALIVE;
	}

	public void nextGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				int nbNeighbors = 0;

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {

						if (i != 1 || j != 1) {
							final int nx = getNeighbor(x, i, n);
							final int ny = getNeighbor(y, j, m);

							switch (cells[nx][ny]) {
								case ALIVE:
								case DYING:
									nbNeighbors++;
							}
						}
					}
				}

				if (cells[x][y] == DEAD && nbNeighbors == 3)
					cells[x][y] = BIRTH;

				else if (cells[x][y] == ALIVE && nbNeighbors != 2
							&& nbNeighbors != 3)
						cells[x][y] = DYING;
			}
		}
	}

	public void finishGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				switch (cells[x][y]) {
					case BIRTH:
						cells[x][y] = ALIVE;
						break;

					case DYING:
						cells[x][y] = DEAD;
				}
			}
		}
	}

	public void reset() {
		// Arrays.fill(cells, new State[m]);
		for (int i = 0; i < n; i++) {
			// cells[i] = new State[m];
			for (int j = 0; j < m; j++) {
				cells[i][j] = DEAD;
			}
		}

		for (Cell c : initialCells) {
			cells[c.x][c.y] = ALIVE;
		}
	}

	public String toString() {
		String str = new String("Life("+n+", "+m+")\n");

		for (Cell c : initialCells) {
			str += c + "\n";
		}

		return str;
	}
}
