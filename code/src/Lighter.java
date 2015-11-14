import java.awt.Color;
import java.util.Iterator;


public class Lighter extends Boid {

	private static final int SIZE = 40;
	private static final int MAX_SPEED = 10;
	private int alpha;
	private int diff;

	public Lighter(double x, double y, double vx, double vy, double ax, double ay) {
		super(x, y, vx, vy, ax, ay, Group.Lighter, Color.WHITE, SIZE);
		maxspeed = MAX_SPEED;

		alpha = 255;
		diff = -50;
	}


	public void update() {
		super.update();

		alpha += diff;

		if (alpha <= 77) {
			alpha = 77;
			diff *= -1;
		}

		else if (alpha >= 255) {
			alpha = 255;
			diff *= -1;
		}

		color = new Color(255, 255, 255, alpha);
	}
}

