
public class Immigration extends ExtendedAutomaton {

	public Immigration(int n, int m, int states) {
		this(n, m, states, 0);
	}

	public Immigration(int n, int m, int states, int defaultState) {
		super(n, m, states, defaultState);	
	}

	public void nextGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {

				int nbNeighbors = 0;
				final int k = cells[x][y];
				final int kp = (k + 1) % states;

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {

						if (i != 1 || j != 1) {
							final int nx = getNeighbor(x, i, n);
							final int ny = getNeighbor(y, j, m);

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

	public String toString() {
		String str = new String("Immigration("+n+", "+m+")\n");
		
		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}

}

