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

	protected int triXPoints[];
	protected int triYPoints[];


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

		triXPoints = new int[3];
		triYPoints = new int[3];
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

	public double[] rotateAroundCenter(double cx, double cy, double x, double y) {
		double[] res = {x , y};
		AffineTransform.getRotateInstance(getDirection(), cx, cy).transform(res, 0, res, 0, 1);
		return res;
	}

	public void computeTrianglePoints() {
		double cx = position.getX();
		double cy = position.getY();

		double[] res = rotateAroundCenter(cx, cy, cx + (double)size, cy);
		triXPoints[0] = (int)res[0];
		triYPoints[0] = (int)res[1];

		res = rotateAroundCenter(cx, cy, cx - (double)size, cy + ((double)size)/2);
		triXPoints[1] = (int)res[0];
		triYPoints[1] = (int)res[1];

		res = rotateAroundCenter(cx, cy, cx - (double)size, cy - ((double)size)/2);
		triXPoints[2] = (int)res[0];
		triYPoints[2] = (int)res[1];
	}

	public int[] getTriangleXPoints() {
		return triXPoints;
	}

	public int[] getTriangleYPoints() {
		return triYPoints;
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
		double angleDirection = b.getDirection();
		double currentAngleDirection = getDirection();

		double firstVisionLimit = aMod(currentAngleDirection + VISION);
		double secondVisionLimit = aMod(currentAngleDirection - VISION);
		
		double minAngleVisionLimit = Math.min(firstVisionLimit, secondVisionLimit);
		double maxAngleVisionLimit = Math.max(firstVisionLimit, secondVisionLimit);

		// Ancienne condition toujours vraie, preuve live
		if (!(angleDirection <= minAngleVisionLimit && angleDirection >= maxAngleVisionLimit) != true) {
			System.out.println("Ce texte ne s'affichera jamais."); 
			System.exit(0);
		}


		if (this != b && position.distance(b.position) < NEIGHBORHOOD)
			// && (minAngleVisionLimit <= angleDirection && angleDirection <= maxAngleVisionLimit))
			return true;
		
		
		return false;
	}

	// First rule : Boids try to fly towards the center of mass of neighboring boids
	protected PVector ruleFlyTowardCentreMass() {
		PVector centerMassPosition = new PVector(0, 0);
		int nb = 0;

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				centerMassPosition.add(b.position);
				nb++;
			}
		}

		if (nb > 0) {
			centerMassPosition.div(nb);
			centerMassPosition.sub(position);
		}

		return centerMassPosition.div(MOVE_FACTOR);
	}

	// Second rule : Boids try to keep a small distance away from other objects (including other Boids)
	protected PVector ruleKeepDistance() {
		PVector d = new PVector(0, 0);

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				if(position.distance(b.position) < SECURITY_DIST) {
					PVector tmp = new PVector(0, 0);

					tmp.add(b.position);
					tmp.sub(position);
					tmp.mult(-1);

					d.add(tmp);
				}
			}
		}

		return d;
	}

	// Third rule : Boids try to match speed with near boids
	protected PVector ruleMatchVelocity() {
		PVector v = new PVector(0, 0);
		int nb = 0;

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				v.add(b.speed);
				nb++;
			}
		}

		if (nb > 0) {
			v.div(nb);
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

		f = ruleFlyTowardCentreMass();
		f.add( ruleKeepDistance() );
		f.add( ruleMatchVelocity() );

		applyForce(f);
		update();
	}

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + speed.x + ", vy : " 
				+ speed.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y + ")";
	}
}

