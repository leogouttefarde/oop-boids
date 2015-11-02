

public abstract class Conway {

	protected int n;
	protected int m;

	public Conway(int n, int m) {
		this.n = n;
		this.m = m;
	}

	public Conway(int size) {
		this(size, size);
	}

	public abstract void reset();
	protected abstract void nextGeneration();
	protected abstract void finishGeneration();

	protected int getNeighbor(int cell, int n, int max) {

		int pos = cell + n;

		if (pos == 0)
			pos = max-1;

		else
			pos = (pos - 1) % max;

		return pos;
	}

	public void generate() {
		nextGeneration();
		finishGeneration();
	}

	public String toString() {
		return "Conway("+n+", "+m+")";
	}
}
