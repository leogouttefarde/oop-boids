import java.awt.Color;
import java.util.Iterator;

public class Prey extends Boid {
	
	private static final Color PREY_COLOR = Color.decode("#3366FF");
	private static final int PREY_SIZE = 7;
	private static final int PREY_MAX_SPEED = 10;

	public Prey(double x, double y, double vx, double vy, double ax, double ay) {
		super(x, y, vx, vy, ax, ay, Group.Prey, PREY_COLOR, PREY_SIZE);
		maxspeed = PREY_MAX_SPEED;
	}

	protected PVector ruleEscapeFromPredator() {
		PVector f = new PVector(0, 0);
		
		Iterator<Boid> it= boids.iterator();
		Boid b = it.next();
		while(it.hasNext() && !isNeighbor(b, Group.Predator)) {
			b = it.next();
		}

		if(isNeighbor(b, Group.Predator)) {
			f.add(b.position);
			f.sub(position);
			f.mult(-PREY_MAX_SPEED);
			f.div(MOVE_FACTOR);
		} 

		return f;
	}

	@Override
	public void move() {
		PVector f = ruleEscapeFromPredator();

		if(!f.isNull()) {
			applyForce(f);
			update();
		} else {
			super.move();
		}
	}
}

