package elements;

import utilities.PVector;
import utilities.Type;
import java.awt.Color;
import java.util.Iterator;

public class Prey extends Boid {
	
	private static final Color PREY_COLOR = Color.decode("#3366FF");
	private static final int PREY_SIZE = BASE_SIZE;
	private static final int PREY_MAX_SPEED = 10;

	public Prey(double x, double y, double vx, double vy, double ax, double ay) {
		super(x, y, vx, vy, ax, ay, Type.Prey, PREY_COLOR, PREY_SIZE);
		maxspeed = PREY_MAX_SPEED;
	}


	protected PVector ruleEscapeFromPredator() {
		Iterator<Boid> it = boids.iterator();
		PVector force = new PVector();
		Boid b;

		do {
			b = it.next();
		} while (it.hasNext() && !isNeighbor(b, Type.Predator));

		if(isNeighbor(b, Type.Predator)) {
			force.add(position);
			force.sub(b.position);
			force.mult(PREY_MAX_SPEED);
			force.div(MOVE_FACTOR);
		} 

		return force;
	}

	@Override
	public void move() {
		PVector force = ruleEscapeFromPredator();

		if(!force.isNull()) {
			applyForce(force);
			update();
		}
		else {
			super.move();
		}
	}
}

