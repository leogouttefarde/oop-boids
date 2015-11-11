import java.awt.geom.Point2D;

public class PVector extends Point2D.Float implements Cloneable {

	public PVector(float x, float y) {
		super(x, y);
	}

	public PVector clone() {
		PVector p = new PVector(x, y);

		return p;
	}

	PVector add(PVector p) {
		x += p.x;
		y += p.y;

		return this;
	}

	PVector sub(PVector p) {
		x -= p.x;
		y -= p.y;

		return this;
	}

	float getLimit(float value, float lim) {

		if (value > lim)
			value = lim;

		else if (value < -lim)
			value = -lim;

		return value;
	}

	PVector limit(float lim) {
		x = getLimit(x, lim);
		y = getLimit(y, lim);

		return this;
	}

	PVector mult(float v) {
		x *= v;
		y *= v;

		return this;
	}

	PVector mult(PVector p) {
		x *= p.x;
		y *= p.y;

		return this;
	}

	PVector div(float v) {
		x /= v;
		y /= v;

		return this;
	}
}
