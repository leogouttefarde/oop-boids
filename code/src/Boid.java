import java.awt.geom.AffineTransform;

public class Boid implements Cloneable {
	
	public static int NEIGHBORHOOD = 55;
	public static int DEFAULT_MAX_SPEED = 10;
	public static int DEFAULT_MAX_FORCE = 77;
	private static final int nbTrianglePoint = 3;

	public PVector position;
	public PVector velocity;
	public PVector acceleration;
	
	private static double visionAngle = Math.PI/3;
	private double orientation;

	public float maxforce;
	public float maxspeed;

	public int triXPoints[];
	public int triYPoints[];


	public Boid(float x, float y, float vx, float vy, float ax, float ay) {
		position = new PVector(x, y);
		velocity = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);
		
		orientation = mod(Math.atan2((double)velocity.getY(), (double)velocity.getX()), 2*Math.PI);
		
		maxspeed = DEFAULT_MAX_SPEED;
		maxforce = DEFAULT_MAX_FORCE;

		triXPoints = new int[nbTrianglePoint];
		triYPoints = new int[nbTrianglePoint];
	}
	
	public Boid(PVector p, PVector v, PVector a, float ms, float mf) {
		position = p;
		velocity = v;
		acceleration = a;

		maxspeed = ms;
		maxforce = mf;

		triXPoints = new int[nbTrianglePoint];
		triYPoints = new int[nbTrianglePoint];
	}

	public Boid clone() {
		Boid b = new Boid(position.clone(), velocity.clone(), acceleration.clone(), maxspeed, maxforce);

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

	public void computeTrianglePoints(int size) {
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
		if (!(angleDirection <= minAngleVisionLimit && angleDirection >= maxAngleVisionLimit) == false) {
			System.out.println("Ce texte ne s'affichera jamais."); 
			System.exit(0);
		}


		if (this != b && position.distance(b.position) < NEIGHBORHOOD
			&& (minAngleVisionLimit <= angleDirection && angleDirection <= maxAngleVisionLimit))
			return true;
		
		
		return false;
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

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + velocity.x + ", vy : " 
				+ velocity.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y +")";
	}
}

