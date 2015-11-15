package group;

public class Immigration extends ExtendedAutomaton {

	public Immigration(int n, int m, int states) {
		this(n, m, states, 0);
	}

	public Immigration(int n, int m, int states, int defaultState) {
		super(n, m, states, defaultState);	
	}


	protected boolean isNeighborMatch(int cell, int neighbor) {
		final int next = (cell + 1) % states;
		boolean success = false;

		if (neighbor == next)
			success = true;

		return success;
	}

	protected void endCellGen(int x, int y, int nbNeighbors) {
		final int state = cells[x][y];
		final int next = (state + 1) % states;

		if (nbNeighbors >= 3)
			nextCells[x][y] = next;

		else
			nextCells[x][y] = state;
	}

	public String toString() {
		String str = new String("Immigration("+n+", "+m+")\n");
		
		for (Cell c : beginning) {
			str += c + "\n";
		}

		return str;
	}
}

