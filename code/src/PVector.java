import java.awt.geom.Point2D;

public class PVector extends Point2D.Double {

	public PVector() {
		super();
	}

	public PVector(double x, double y) {
		super(x, y);
	}


	public PVector clone() {
		return (PVector)super.clone();
	}

	public boolean isNull() {
		return (x == 0 && y == 0);
	}

	public PVector add(PVector p) {
		x += p.x;
		y += p.y;

		return this;
	}

	public PVector sub(PVector p) {
		x -= p.x;
		y -= p.y;

		return this;
	}

	public double norm() {
		double n = Math.sqrt(x*x + y*y);

		return n;
	}

	public double getLimit(double value, double lim) {

		if (value > lim)
			value = lim;

		else if (value < -lim)
			value = -lim;

		return value;
	}

	public PVector limit(double lim) {
		x = getLimit(x, lim);
		y = getLimit(y, lim);

		return this;
	}

	public PVector mult(double v) {
		x *= v;
		y *= v;

		return this;
	}

	public PVector mult(PVector p) {
		x *= p.x;
		y *= p.y;

		return this;
	}

	public PVector div(double v) {
		x /= v;
		y /= v;

		return this;
	}

	public void reset() {
		x = y = 0;
	}
}
