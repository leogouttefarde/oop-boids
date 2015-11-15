package groups;

import java.util.LinkedList;

public class Schelling extends ExtendedAutomaton {
	
	private int k;
	private LinkedList<Cell> vacantHouses;


	public Schelling(int n, int m, int states, int k) {
		this(n, m, states, k, 0);
	}

	public Schelling(int n, int m, int states, int k, int defaultState) {
		super(n, m, states, defaultState);
		this.k = k;

		vacantHouses = new LinkedList<Cell>();
		initVacantHouses();
	}

	public void initVacantHouses() {
		vacantHouses.clear();

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				if(cells[x][y] == defaultState)
					vacantHouses.add(new Cell(x, y, defaultState));
			}
		}
	}


	protected void preGeneration() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				nextCells[x][y] = cells[x][y];
			}
		}
	}

	protected boolean skipCellGen(int cell) {
		boolean skip = false;

		if (cell == defaultState)
			skip = true;

		return skip;
	}

	protected boolean isNeighborMatch(int cell, int neighbor) {
		boolean success = false;

		if (neighbor != cell && neighbor != defaultState) {
			success = true;
		}

		return success;
	}

	protected void endCellGen(int x, int y, int nbNeighbors) {
		if(nbNeighbors > k) {
			nextCells[x][y] = defaultState;
			vacantHouses.add(new Cell(x, y, defaultState));

			Cell cell = vacantHouses.remove();
			nextCells[ cell.getX() ][ cell.getY() ] = cells[x][y];
		}
	}


	@Override
	public void reset() {
		super.reset();
		initVacantHouses();
	}

	public String toString() {
		String str = new String("Schelling("+n+", "+m+")\n");

		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}
}
