import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Boid implements Cloneable {
	
	public static int NEIGHBORHOOD = 55;
	public static int DEFAULT_MAX_SPEED = 10;
	public static int DEFAULT_MAX_FORCE = 77;
	private static final int nbTrianglePoint = 3;
	private static final int basicSize = 7;
	
	protected static final int deplacementFactor = 100;
	protected static final int smallDistance = 14;
	protected static final int velocityFactor = 8;
	
	protected PVector position;
	protected PVector velocity;
	protected PVector acceleration;
	protected ArrayList<Boid> boids;
	
	protected enum Behaviour {Prey, Predator};
	protected Behaviour behaviour;
	
	protected Color color;
	protected int size;
		
	protected static double visionAngle = Math.PI/3;
	protected double orientation;

	protected float maxforce;
	protected float maxspeed;

	public int triXPoints[];
	public int triYPoints[];

	public Boid(float x, float y, float vx, float vy, float ax, float ay, ArrayList<Boid> boids) {
		position = new PVector(x, y);
		velocity = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);
		this.boids = boids;
		behaviour = Behaviour.Prey;
		color = Color.decode("#1f77b4");
		size = basicSize;
		orientation = mod(Math.atan2((double)velocity.getY(), (double)velocity.getX()), 2*Math.PI);
		
		maxspeed = DEFAULT_MAX_SPEED;
		maxforce = DEFAULT_MAX_FORCE;

		triXPoints = new int[nbTrianglePoint];
		triYPoints = new int[nbTrianglePoint];
		
	}

	public Boid(PVector p, PVector v, PVector a, float ms, float mf, ArrayList<Boid> boids) {
		position = p;
		velocity = v;
		acceleration = a;
		
		maxspeed = ms;
		maxforce = mf;
		
		this.boids = boids;
		behaviour = Behaviour.Prey;
		color = Color.decode("#1f77b4");
		orientation = mod(Math.atan2((double)velocity.getY(), (double)velocity.getX()), 2*Math.PI);

		triXPoints = new int[nbTrianglePoint];
		triYPoints = new int[nbTrianglePoint];
	}
	
	public Boid(float x, float y, float vx, float vy, float ax, float ay, ArrayList<Boid> boids, Behaviour behav, Color c, int size) {
		this(x, y, vx, vy, ax, ay, boids);
		behaviour = behav;
		color = c;
		this.size = size;
	}

	public Boid(PVector p, PVector v, PVector a, float ms, float mf, ArrayList<Boid> boids, Behaviour behav, Color c, int size) {
		this(p, v, a, ms, mf, boids);
		behaviour = behav;
		color = c;
		this.size = size;
	}

	public Boid clone() {
		Boid b = new Boid(position.clone(), velocity.clone(), acceleration.clone(), maxspeed, maxforce, this.boids);

		return b;
	}
	
	public void reset(PVector position, PVector velocity, PVector acceleration){
		this.position.setLocation(position);
		this.velocity.setLocation(velocity);
		this.acceleration.setLocation(acceleration);
	}
	
	public double getOrientation(){
		return orientation;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getSize(){
		return size;
	}

	// public double[] rotateAroundCenter(double cx, double cy, double[] x, double[] y){
	// 	double[] res = new int[x.length + y.length];
	// 	for (int i = 0; i < res.length; i++) {
	// 		if (i % 2)
	// 			value = y[(i - 1) / 2];
	// 		else
	// 			value = x[i/2];
	// 		res[i] = value;
	// 	}
	// 	AffineTransform.getRotateInstance(orientation, cx, cy).transform(res, 0, res, 0, res.length);
	// 	return res;
	// }

	public double[] rotateAroundCenter(double cx, double cy, double x, double y){
		double[] res = {x , y};
		AffineTransform.getRotateInstance(orientation, cx, cy).transform(res, 0, res, 0, 1);
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

	public float mod(float a, float b) {
		float r = a % b;

		return (r < 0) ? r+b : r;
	}
	
	public double mod(double a, double b) {
		double r = a % b;

		return (r < 0) ? r+b : r;
	}

	public void setPos(PVector pos){
		pos.x = mod(pos.x, Boids.getWidth());
		pos.y = mod(pos.y, Boids.getHeight());

		this.position.setLocation(pos);
	}

	public boolean isNeighbor(Boid b) {
		double angleDirection = mod(Math.atan2((double)b.velocity.getY(), (double)b.velocity.getX()), 2*Math.PI);
		double currentAngleDirection = mod(Math.atan2((double)velocity.getY(), (double)velocity.getX()), 2*Math.PI);

		double firstVisionLimit = mod(currentAngleDirection + visionAngle, 2*Math.PI);
		double secondVisionLimit = mod(currentAngleDirection - visionAngle, 2*Math.PI);
		
		double minAngleVisionLimit = Math.min(firstVisionLimit, secondVisionLimit);
		double maxAngleVisionLimit = Math.max(firstVisionLimit, secondVisionLimit);

		// Ancienne condition toujours vraie, preuve live
		if (!(angleDirection <= minAngleVisionLimit && angleDirection >= maxAngleVisionLimit) != true) {
			System.out.println("Ce texte ne s'affichera jamais."); 
			System.exit(0);
		}


		if (this != b && position.distance(b.position) < NEIGHBORHOOD)
			//&& (minAngleVisionLimit <= angleDirection && angleDirection <= maxAngleVisionLimit))
			return true;
		
		
		return false;
	}
	
	// First rule : Boids try to fly towards the center of mass of neighboring boids
	protected PVector ruleFlyTowardCentreMass(){
		PVector centerMassPosition = new PVector(0, 0);
		int nb = 0;

		// System.out.println("ruleFlyTowardCentreMass");

		for(Boid b : boids) {
			if(isNeighbor(b) && b.behaviour == behaviour) {
				centerMassPosition.add(b.position);
				nb++;
			}
		}

		if (nb > 0) {
			centerMassPosition.div(nb);
			centerMassPosition.sub(position);
		}

		// System.out.println(centerMassPosition);
		return centerMassPosition.div(deplacementFactor);
	}

	// Second rule : Boids try to keep a small distance away from other objects (including other Boids)
	protected PVector ruleKeepDistance() {
		// System.out.println("ruleKeepDistance");
		PVector d = new PVector(0, 0);
		// System.out.println("d");
		// System.out.println(d);

		for(Boid b : boids){
			if(isNeighbor(b) && b.behaviour == behaviour) {
				if(position.distance(b.position) < smallDistance){
					PVector tmp = new PVector(0, 0);

					tmp.add(b.position);
					tmp.sub(position);
					tmp.mult(-1);

					d.add(tmp);
				}
			}
		}

		// System.out.println("d");
		// System.out.println(d);

		return d;
	}

	// Third rule : Boids try to match velocity with near boids
	protected PVector ruleMatchVelocity() {
		PVector v = new PVector(0, 0);
		int nb = 0;

		// System.out.println("ruleMatchVelocity");

		for(Boid b : boids) {
			if(isNeighbor(b) && b.behaviour == behaviour) {
				v.add(b.velocity);
				nb++;
			}
		}

		if (nb > 0) {
			v.div(nb);
			v.sub(velocity);
		}
		
		// System.out.println(v);
		return v.div(velocityFactor);
	}

	public void update() {
		velocity.add(acceleration);
		velocity.limit(maxspeed);
		setPos(position.clone().add(velocity));
		acceleration.mult(0);
		orientation = mod(Math.atan2((double)velocity.getY(), (double)velocity.getX()), 2*Math.PI);
	}

	public void applyForce(PVector force) {
		float m = 1; // m = masse du Boid
		force.limit(maxforce);
		acceleration.add(force.div(m));
	}
	
	public void move(){
		PVector v1, v2, v3;
		v1 = ruleFlyTowardCentreMass();
		v2 = ruleKeepDistance();
		v3 = ruleMatchVelocity();
		
		applyForce(v1);
		applyForce(v2);
		applyForce(v3);
		update();
	}

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + velocity.x + ", vy : " 
				+ velocity.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y +")";
	}
}

