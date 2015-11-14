import java.awt.Color;
import java.util.LinkedList;
import java.util.Iterator;


public class Predator extends Boid {
	
	private static final Color PRED_COLOR = Color.decode("#990000");
	private static final int PRED_SIZE = 15;
	private static final int PRED_MAX_SPEED = 12;
	private static final int DEATH_RADIUS = 10;

	public Predator(double x, double y, double vx, double vy, double ax, double ay) {
		super(x, y, vx, vy, ax, ay, Group.Predator, PRED_COLOR, PRED_SIZE);
		maxspeed = PRED_MAX_SPEED;
	}

	protected PVector ruleCatchPrey() {
		PVector f = new PVector(0, 0);

		Iterator<Boid> it= boids.iterator();
		Boid b = it.next();
		while(it.hasNext() && !isNeighbor(b, Group.Prey)) {
			b = it.next();
		}
		
		if(isNeighbor(b, Group.Prey)) {
			f.add(b.position);
			f.sub(position);
			f.mult(PRED_MAX_SPEED);
			f.div(MOVE_FACTOR);
		}

		return f;
	}

	protected boolean kill() {
		Iterator<Boid> it = boids.iterator();
		boolean killed = false;

		while(it.hasNext() && !killed)  {
			Boid b = it.next();

			if(isNeighbor(b, Group.Prey) && position.distance(b.position) <= DEATH_RADIUS) {
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
			PVector f = ruleCatchPrey();

			if(!f.isNull()) {
				applyForce(f);
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
