import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public abstract class Boid implements Cloneable {

	protected static final int NEIGHBORHOOD = 55;
	protected static final int BASE_MAX_SPEED = 10;
	protected static final int BASE_MAX_FORCE = 77;
	protected static final int BASE_SIZE = 14;

	protected static final int MOVE_FACTOR = 100;
	protected static final int SECURITY_DIST = 20;
	protected static final int SPEED_FACTOR = 8;
	protected static final double VISION = 2*Math.PI/3;

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


	public Boid(double x, double y, double vx, double vy, double ax, double ay,
				Group type, Color color, int size) {
		position = new PVector(x, y);
		speed = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);

		computeDirection();

		maxspeed = BASE_MAX_SPEED;
		maxforce = BASE_MAX_FORCE;

		triXPts = new int[3];
		triYPts = new int[3];

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
		final double downSize = curSize / 4;

		double[] pts = turn(cx + 2*curSize/3, cy);
		triXPts[0] = (int)pts[0];
		triYPts[0] = (int)pts[1];

		pts = turn(cx - curSize/3, cy + downSize);
		triXPts[1] = (int)pts[0];
		triYPts[1] = (int)pts[1];

		pts = turn(cx - curSize/3, cy - downSize);
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
	
	public boolean isNear(Boid b, Group type) {
		return (b.type == type) && isNear(b);
	}
	
	public boolean isNear(Boid b) {
		return this != b && position.distance(b.position) < NEIGHBORHOOD;
	}

	public boolean isNeighbor(Boid b) {
		//final double neighborDir = b.getDirection();
		
		double tmp1 = b.position.x - position.x;
		double tmp2 = b.position.y - position.y;
		
		double neighborDir = aMod(Math.atan2(tmp2, tmp1));

		double minAngle = aMod(direction + VISION);
		double maxAngle = aMod(direction - VISION);
		
		if (maxAngle < minAngle) {
			final double tmp = maxAngle;
			maxAngle = minAngle;
			minAngle = tmp;
		}
			
		if (this != b && position.distance(b.position) < NEIGHBORHOOD
			 && !(minAngle <= neighborDir && neighborDir <= maxAngle))
			return true;
		
		
		return false;
	}

	// First rule : Boids try to fly towards the center of mass of neighboring boids
	protected PVector ruleFlyToCenter() {
		PVector force = new PVector();
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
		PVector forces = new PVector();

		for(Boid b : boids) {
			if(isNear(b, type)) {
				if(position.distance(b.position) < SECURITY_DIST) {
					PVector force = new PVector();

					force.add(position);
					force.sub(b.position);

					forces.add(force);
				}
			}
		}

		return forces;
	}

	// Third rule : Boids try to match speed with near boids
	protected PVector ruleMatchSpeed() {
		PVector v = new PVector();
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
		
		return v;//.div(SPEED_FACTOR);
	}

	public void update() {
		speed.add(acceleration);
		speed.limit(maxspeed);
		computeDirection();

		setPos(position.clone().add(speed));
		acceleration.reset();
	}

	public void applyForce(PVector force) {
		force.limit(maxforce);
		acceleration.add(force.div(getMass()));
	}

	public void move() {
		PVector force;

		force = ruleFlyToCenter();
		force.add( ruleKeepDistance() );
		force.add( ruleMatchSpeed() );

		applyForce(force);
		update();
	}

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + speed.x + ", vy : " 
				+ speed.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y + ")";
	}
}

