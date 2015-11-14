import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class Boid implements Cloneable {

	public static int NEIGHBORHOOD = 55;
	public static int BASE_MAX_SPEED = 10;
	public static int BASE_MAX_FORCE = 77;
	private static final int BASE_SIZE = 7;

	protected static final int MOVE_FACTOR = 100;
	protected static final int SECURITY_DIST = 14;
	protected static final int SPEED_FACTOR = 8;

	protected PVector position;
	protected PVector speed;
	protected PVector acceleration;
	protected LinkedList<Boid> boids;

	protected enum Group { Prey, Predator };
	protected Group type;

	protected Color color;
	protected int size;

	protected static double visionAngle = Math.PI/3;
	private double direction;

	protected double maxforce;
	protected double maxspeed;

	protected int triXPoints[];
	protected int triYPoints[];


	public Boid(double x, double y, double vx, double vy, double ax, double ay, LinkedList<Boid> boids) {
		position = new PVector(x, y);
		speed = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);
		this.boids = boids;
		type = Group.Prey;
		color = Color.decode("#1f77b4");
		size = BASE_SIZE;

		computeDirection();

		maxspeed = BASE_MAX_SPEED;
		maxforce = BASE_MAX_FORCE;

		triXPoints = new int[3];
		triYPoints = new int[3];
		
	}

	public Boid(double x, double y, double vx, double vy, double ax, double ay, LinkedList<Boid> boids, Group behav, Color c, int size) {
		this(x, y, vx, vy, ax, ay, boids);
		type = behav;
		color = c;
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

	private void computeDirection() {
		direction = ABS_ANGLE(Math.atan2(speed.getY(), speed.getX()));
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

	public static double ABS_MOD(double a, double b) {
		double r = a % b;

		return (r < 0) ? (r + b) : r;
	}

	public static double ABS_ANGLE(double a) {
		return ABS_MOD(a, 2 * Math.PI);
	}

	public void setPos(PVector pos) {
		pos.x = ABS_MOD(pos.x, Boids.getWidth());
		pos.y = ABS_MOD(pos.y, Boids.getHeight());

		this.position.setLocation(pos);
	}

	public boolean isNeighbor(Boid b, Group type) {
		return (this.type == type) && isNeighbor(b);
	}

	public boolean isNeighbor(Boid b) {
		double angleDirection = b.getDirection();
		double currentAngleDirection = getDirection();

		double firstVisionLimit = ABS_ANGLE(currentAngleDirection + visionAngle);
		double secondVisionLimit = ABS_ANGLE(currentAngleDirection - visionAngle);
		
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
			if(isNeighbor(b) && b.type == type) {
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
			if(isNeighbor(b) && b.type == type) {
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
			if(isNeighbor(b) && b.type == type) {
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
				+ speed.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y +", "+direction+")";
	}
}

