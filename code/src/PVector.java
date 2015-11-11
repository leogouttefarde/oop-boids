import java.awt.geom.Point2D;

public class PVector extends Point2D.Float implements Cloneable {

	public PVector(float x, float y) {
		super(x, y);
	}

	public PVector clone() {
		PVector p = new PVector(x, y);

		return p;
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

	public float norm() {
		float n = (float)Math.sqrt(x*x + y*y);

		return n;
	}

	public float getLimit(float value, float lim) {

		if (value > lim)
			value = lim;

		else if (value < -lim)
			value = -lim;

		return value;
	}

	public PVector limit(float lim) {
		x = getLimit(x, lim);
		y = getLimit(y, lim);

		return this;
	}

	public PVector mult(float v) {
		x *= v;
		y *= v;

		return this;
	}

	public PVector mult(PVector p) {
		x *= p.x;
		y *= p.y;

		return this;
	}

	public PVector div(float v) {
		x /= v;
		y /= v;

		return this;
	}
}
