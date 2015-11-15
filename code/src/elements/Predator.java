package elements;

import utilities.PVector;
import utilities.Type;
import java.awt.Color;
import java.util.Iterator;

public class Predator extends Boid {
	
	private static final Color PRED_COLOR = Color.decode("#990000");
	private static final int PRED_SIZE = 25;
	private static final int PRED_MAX_SPEED = 12;
	private static final int DEATH_RADIUS = 10;

	public Predator(double x, double y, double vx, double vy, double ax, double ay) {
		super(x, y, vx, vy, ax, ay, Type.Predator, PRED_COLOR, PRED_SIZE);
		maxspeed = PRED_MAX_SPEED;
	}


	protected PVector ruleCatchPrey() {
		Iterator<Boid> it = boids.iterator();
		PVector force = new PVector();
		Boid b;

		do {
			b = it.next();
		} while (it.hasNext() && !isNeighbor(b, Type.Prey));
		
		if(isNeighbor(b, Type.Prey)) {
			force.add(b.position);
			force.sub(position);
			force.mult(PRED_MAX_SPEED);
			force.div(MOVE_FACTOR);
		}

		return force;
	}

	protected boolean kill() {
		Iterator<Boid> it = boids.iterator();
		boolean killed = false;

		while(it.hasNext() && !killed)  {
			Boid b = it.next();

			if(isNeighbor(b, Type.Prey) && position.distance(b.position) <= DEATH_RADIUS) {
				it.remove();
				killed = true;
				System.out.println("Prey eaten");
			}
		}

		return killed;
	}

	@Override
	public void move() {
		boolean killed = kill();

		if(!killed) {
			PVector force = ruleCatchPrey();

			if(!force.isNull()) {
				applyForce(force);
				update();
			}
			else {
				super.move();
			}
		}
		else {
			super.move();
		}
	}
}
