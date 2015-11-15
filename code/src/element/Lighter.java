package element;

import utility.Type;
import utility.PVector;
import java.awt.Color;
import java.util.Random;


public class Lighter extends Boid {

	private static final int SIZE = 40;
	private static final int MAX_SPEED = 10;
	protected static final int MIN_DIST = 44;

	protected static final int STEP = 40;
	protected static final int MIN_LIGHT = 0;
	protected static final int MAX_LIGHT = 170;

	private int light;
	private int diff;


	public Lighter(double x, double y, double vx, double vy, double ax, double ay) {
		super(x, y, vx, vy, ax, ay, Type.Lighter, Color.WHITE, SIZE);
		maxspeed = MAX_SPEED;

		diff = STEP;
		randLight();
	}


	public void reset(PVector position, PVector speed, PVector acceleration) {
		super.reset(position, speed, acceleration);
		randLight();
	}

	protected void randLight() {
		Random rand = new Random();
		setLight(rand.nextInt(MAX_LIGHT - MIN_LIGHT) + MIN_LIGHT);
	}

	protected void setLight(int light) {
		this.light = light;
		color = new Color(light, 255, 255, 255);
	}

	public int GetMinDist() {
		return Lighter.MIN_DIST;
	}

	public void update() {
		super.update();

		light -= diff;

		if (light <= MIN_LIGHT) {
			light = MIN_LIGHT;
			diff *= -1;
		}
		else if (light >= MAX_LIGHT) {
			light = MAX_LIGHT;
			diff *= -1;
		}

		setLight(light);
	}
}

