

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

	public void generate() {
		nextGeneration();
		finishGeneration();
	}

	public String toString() {
		return "Conway("+n+", "+m+")";
	}
}
