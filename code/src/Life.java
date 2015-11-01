import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Life {

	public enum State { ALIVE, DEAD, DYING, BIRTH };
	private int n;
	private int m;
	private State cells[][];
	private Vector<Point> initialCells;


	public Life(int n, int m) {
		this.n = n;
		this.m = m;

		cells = new State[n][m];
		initialCells = new Vector<Point>();
		reset();
	}

	public State[][] getCells() {
		return cells;
	}

	public void addCell(int x, int y) {
		initialCells.add(new Point(x, y));
		cells[x][y] = State.ALIVE;
	}

	public int getNeighbor(int cell, int n, int max) {

		int pos = cell + n;

		if (pos == 0)
			pos = max-1;

		else
			pos = (pos - 1) % max;

		return pos;
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
							}
						}
					}
				}

				if (cells[x][y] == State.DEAD && nbNeighbors == 3)
					cells[x][y] = State.BIRTH;

				else if (cells[x][y] == State.ALIVE &&
					(nbNeighbors < 2 || 3 < nbNeighbors))
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
				}
			}
		}
	}

	public void generate() {
		nextGeneration();
		finishGeneration();
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
