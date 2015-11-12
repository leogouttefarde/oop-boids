public class Conway extends CellularAutomaton {
	
	// ALIVE : 0, BIRTH : 1, DEAD : 2, DYING : 3
	public static final int alive = 0;
	public static final int birth = 1;
	public static final int dead = 2;
	public static final int dying = 3;
	private static final int states = 4;

	public Conway(int n, int m) {
		super(n, m, states, alive);
		reset();
	}

	public void nextGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				int nbNeighbors = 0;

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {

						if (i != 1 || j != 1) {
							int nx = getNeighbor(x, i, n);
							int ny = getNeighbor(y, j, m);

							// System.out.println(x+", "+y + ", "+i + ", "+j + " :  "+ nx+", "+ny);
							switch (cells[nx][ny]) {
								case alive:
								case dying:
									nbNeighbors++;
									break;
								default:
									break;
							}
						}
					}
				}

				if (cells[x][y] == dead && nbNeighbors == 3)
					cells[x][y] = birth;

				else if (cells[x][y] == alive && nbNeighbors != 2
							&& nbNeighbors != 3)
						cells[x][y] = dying;
			}
		}
	}

	public void finishGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				switch (cells[x][y]) {
					case birth:
						cells[x][y] = alive;
						break;

					case dying:
						cells[x][y] = dead;
						break;
					default:
						break;
				}
			}
		}
	}

	public void reset() {
		// Arrays.fill(cells, new State[m]);
		for (int i = 0; i < n; i++) {
			// cells[i] = new State[m];
			for (int j = 0; j < m; j++) {
				cells[i][j] = dead;
			}
		}

		for (Cell c : initialCells) {
			// System.out.println(c.x+", "+c.y);
			cells[c.x][c.y] = alive;
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
