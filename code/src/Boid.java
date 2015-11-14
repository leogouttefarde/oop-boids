import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public abstract class Boid implements Cloneable {

	protected static final int NEIGHBORHOOD = 55;
	protected static final int BASE_MAX_SPEED = 10;
	protected static final int BASE_MAX_FORCE = 77;
	protected static final int BASE_SIZE = 7;

	protected static final int MOVE_FACTOR = 100;
	protected static final int SECURITY_DIST = 14;
	protected static final int SPEED_FACTOR = 8;
	protected static final double VISION = Math.PI/3;

	protected PVector position;
	protected PVector speed;
	protected PVector acceleration;
	protected LinkedList<Boid> boids;

	protected enum Group { Prey, Predator };
	protected Group type;

	protected Color color;
	protected int size;

	private double direction;

	protected double maxforce;
	protected double maxspeed;

	protected int triXPts[];
	protected int triYPts[];


	public Boid(double x, double y, double vx, double vy, double ax, double ay) {
		position = new PVector(x, y);
		speed = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);
		type = Group.Prey;
		color = Color.decode("#1f77b4");
		size = BASE_SIZE;

		computeDirection();

		maxspeed = BASE_MAX_SPEED;
		maxforce = BASE_MAX_FORCE;

		triXPts = new int[3];
		triYPts = new int[3];
	}

	public Boid(double x, double y, double vx, double vy, double ax, double ay,
				Group type, Color color, int size) {
		this(x, y, vx, vy, ax, ay);
		this.type = type;
		this.color = color;
		this.size = size;
	}

	public Boid clone() {
		Boid b = null;

		try {
			b = (Boid)super.clone();
			b.position = position.clone();
			b.speed = speed.clone();
			b.acceleration = acceleration.clone();
		}
		catch (CloneNotSupportedException e) {
			System.out.println("CloneError");
		}

		return b;
	}

	public void setGroup(LinkedList<Boid> group) {
		this.boids = group;
	}

	private void computeDirection() {
		direction = aMod(Math.atan2(speed.getY(), speed.getX()));
	}

	public void reset(PVector position, PVector speed, PVector acceleration) {
		this.position.setLocation(position);
		this.speed.setLocation(speed);
		this.acceleration.setLocation(acceleration);

		computeDirection();
	}

	public int getMass() {
		return 1;
	}

	public double getDirection() {
		return direction;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getSize() {
		return size;
	}

	public double[] turn(double x, double y) {
		double[] pts = { x , y };

		AffineTransform.getRotateInstance(getDirection(), position.getX(),
							position.getY()).transform(pts, 0, pts, 0, 1);

		return pts;
	}

	public void computeTriangle() {
		final double cx = position.getX();
		final double cy = position.getY();
		final double curSize = (double)size;
		final double halfSize = curSize / 2;

		double[] pts = turn(cx + curSize, cy);
		triXPts[0] = (int)pts[0];
		triYPts[0] = (int)pts[1];

		pts = turn(cx - curSize, cy + halfSize);
		triXPts[1] = (int)pts[0];
		triYPts[1] = (int)pts[1];

		pts = turn(cx - curSize, cy - halfSize);
		triXPts[2] = (int)pts[0];
		triYPts[2] = (int)pts[1];
	}

	public int[] getTriangleX() {
		return triXPts;
	}

	public int[] getTriangleY() {
		return triYPts;
	}

	public static double mod(double a, double b) {
		double r = a % b;

		return (r < 0) ? (r + b) : r;
	}

	public static double aMod(double a) {
		return mod(a, 2 * Math.PI);
	}

	public void setPos(PVector pos) {
		pos.x = mod(pos.x, Boids.getWidth());
		pos.y = mod(pos.y, Boids.getHeight());

		this.position.setLocation(pos);
	}

	public boolean isNeighbor(Boid b, Group type) {
		return (b.type == type) && isNeighbor(b);
	}

	public boolean isNeighbor(Boid b) {
		final double neighborDir = b.getDirection();

		double minAngle = aMod(direction + VISION);
		double maxAngle = aMod(direction - VISION);
		
		if (maxAngle < minAngle) {
			final double tmp = maxAngle;
			maxAngle = minAngle;
			minAngle = tmp;
		}

		// Ancienne condition toujours vraie, preuve live
		if (!(neighborDir <= minAngle && neighborDir >= maxAngle) != true) {
			System.out.println("Ce texte ne s'affichera jamais."); 
			System.exit(0);
		}


		if (this != b && position.distance(b.position) < NEIGHBORHOOD)
			// && (minAngle <= neighborDir && neighborDir <= maxAngle))
			return true;
		
		
		return false;
	}

	// First rule : Boids try to fly towards the center of mass of neighboring boids
	protected PVector ruleFlyToCenter() {
		PVector force = new PVector(0, 0);
		int count = 0;

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				force.add(b.position);
				count++;
			}
		}

		if (count > 0) {
			force.div(count);
			force.sub(position);
			force.div(MOVE_FACTOR);
		}

		return force;
	}

	// Second rule : Boids try to keep a small distance away from other objects (including other Boids)
	protected PVector ruleKeepDistance() {
		PVector forces = new PVector(0, 0);

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				if(position.distance(b.position) < SECURITY_DIST) {
					PVector force = new PVector(0, 0);

					force.add(b.position);
					force.sub(position);
					force.mult(-1);

					forces.add(force);
				}
			}
		}

		return forces;
	}

	// Third rule : Boids try to match speed with near boids
	protected PVector ruleMatchSpeed() {
		PVector v = new PVector(0, 0);
		int count = 0;

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				v.add(b.speed);
				count++;
			}
		}

		if (count > 0) {
			v.div(count);
			v.sub(speed);
		}
		
		return v.div(SPEED_FACTOR);
	}

	public void update() {
		speed.add(acceleration);
		speed.limit(maxspeed);
		computeDirection();

		setPos(position.clone().add(speed));
		acceleration.mult(0);
	}

	public void applyForce(PVector force) {
		force.limit(maxforce);
		acceleration.add(force.div(getMass()));
	}

	public void move() {
		PVector f;

		f = ruleFlyToCenter();
		f.add( ruleKeepDistance() );
		f.add( ruleMatchSpeed() );

		applyForce(f);
		update();
	}

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + speed.x + ", vy : " 
				+ speed.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y + ")";
	}
}

