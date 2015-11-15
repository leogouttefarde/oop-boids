package groups;

public class Life extends Automaton {

	public static final int DEAD = 0;
	public static final int DYING = 1;
	public static final int ALIVE = 2;
	public static final int BIRTH = 3;

	public Life(int n, int m) {
		super(n, m, 4, DEAD);
		reset();
	}


	public void add(int x, int y) {
		beginning.add(new Cell(x, y, ALIVE));
		cells[x][y] = ALIVE;
	}


	protected boolean isNeighborMatch(int cell, int neighbor) {
		boolean success = false;

		switch (neighbor) {
			case ALIVE:
			case DYING:
				success = true;
		}

		return success;
	}

	protected void endCellGen(int x, int y, int nbNeighbors) {
		if (cells[x][y] == DEAD && nbNeighbors == 3)
			cells[x][y] = BIRTH;

		else if (cells[x][y] == ALIVE && nbNeighbors != 2 && nbNeighbors != 3)
				cells[x][y] = DYING;
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
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cells[i][j] = DEAD;
			}
		}

		for (Cell c : beginning) {
			cells[c.getX()][c.getY()] = ALIVE;
		}
	}

	public String toString() {
		String str = new String("Life("+n+", "+m+")\n");

		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}
}
