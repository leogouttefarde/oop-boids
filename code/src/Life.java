import java.awt.Point;
import java.util.ArrayList;

public class Life extends Conway {

	public enum State { ALIVE, DEAD, DYING, BIRTH };
	private State cells[][];
	private ArrayList<Point> initialCells;


	public Life(int n, int m) {
		super(n, m);

		cells = new State[n][m];
		initialCells = new ArrayList<Point>();
		reset();
	}

	public State[][] getCells() {
		return cells;
	}

	public void addCell(int x, int y) {
		initialCells.add(new Point(x, y));
		cells[x][y] = State.ALIVE;
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
								case ALIVE:
								case DYING:
									nbNeighbors++;
									break;
								default:
									break;
							}
						}
					}
				}

				if (cells[x][y] == State.DEAD && nbNeighbors == 3)
					cells[x][y] = State.BIRTH;

				else if (cells[x][y] == State.ALIVE && nbNeighbors != 2
							&& nbNeighbors != 3)
						cells[x][y] = State.DYING;
			}
		}
	}

	public void finishGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				switch (cells[x][y]) {
					case BIRTH:
						cells[x][y] = State.ALIVE;
						break;

					case DYING:
						cells[x][y] = State.DEAD;
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
				cells[i][j] = State.DEAD;
			}
		}

		for (Point c : initialCells) {
			// System.out.println(c.x+", "+c.y);
			cells[c.x][c.y] = State.ALIVE;
		}
	}

	public String toString() {
		String str = new String("Life("+n+", "+m+")\n");

		for (Point c : initialCells) {
			str += c + "\n";
		}

		return str;
	}

}
