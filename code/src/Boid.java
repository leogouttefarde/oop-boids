import java.awt.geom.AffineTransform;

public class Boid implements Cloneable {
	
	public static int NEIGHBORHOOD = 55;
	public static int DEFAULT_MAX_SPEED = 10;
	public static int DEFAULT_MAX_FORCE = 77;
	private static final int nbTrianglePoint = 3;

	public PVector position;
	public PVector velocity;
	public PVector acceleration;
	
	private static double visionAngle = 3*Math.PI/4;
	private double orientation;

	public float maxforce;
	public float maxspeed;


	public Boid(float x, float y, float vx, float vy, float ax, float ay) {
		position = new PVector(x, y);
		velocity = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);
		
		orientation = mod(Math.atan2((double)velocity.getY(), (double)velocity.getX()), 2*Math.PI);
		
		maxspeed = DEFAULT_MAX_SPEED;
		maxforce = DEFAULT_MAX_FORCE;

	}
	
	public Boid(PVector p, PVector v, PVector a, float ms, float mf) {
		position = p;
		velocity = v;
		acceleration = a;

		maxspeed = ms;
		maxforce = mf;
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
	
	public double[] rotateAroundCenter(double cx, double cy, double x, double y){
		double[] res = {x , y};
		AffineTransform.getRotateInstance(orientation, cx, cy).transform(res, 0, res, 0, 1);
		return res;
	}
	
	public int[] computeTriangleXPoints(int size){
		int xPoints[] = new int[nbTrianglePoint];
		double cx = position.getX();
		double cy = position.getY();
		
		double[] res = rotateAroundCenter(cx, cy, cx + (double)size, cy + (double)size);
		xPoints[0] = (int)res[0];
		res = rotateAroundCenter(cx, cy, cx, cy - (double)size);
	    xPoints[1] = (int)res[0];
	    res = rotateAroundCenter(cx, cy, cx - (double)size, cy);
	    xPoints[2] = (int)res[0];
		
		return xPoints;
	}
	
	public int[] computeTriangleYPoints(int size){
		int yPoints[] = new int[nbTrianglePoint];
		double cx = position.getX();
		double cy = position.getY();
		
		double[] res = rotateAroundCenter(cx, cy, cx + (double)size, cy + (double)size);
		yPoints[0] = (int)res[1];
		res = rotateAroundCenter(cx, cy, cx, cy - (double)size);
	    yPoints[1] = (int)res[1];
	    res = rotateAroundCenter(cx, cy, cx - (double)size, cy);
	    yPoints[2] = (int)res[1];
		
		return yPoints;
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
		pos.x = mod(pos.x, Boids.MAX_WIDTH);
		pos.y = mod(pos.y, Boids.MAX_HEIGHT);

		this.position.setLocation(pos);
	}

	public boolean isNeighbor(Boid b) {
		double angleDirection = Math.atan2((double)b.velocity.getY(), (double)b.velocity.getX());
		double currentAngleDirection = Math.atan2((double)velocity.getY(), (double)velocity.getX());
		
		double firstVisionLimit = mod(currentAngleDirection + visionAngle, 2*Math.PI);
		double secondVisionLimit = mod(currentAngleDirection - visionAngle, 2*Math.PI);
		
		double minAngleVisionLimit = Math.min(firstVisionLimit, secondVisionLimit);
		double maxAngleVisionLimit = Math.max(firstVisionLimit, secondVisionLimit);
		
		
		if (this != b && position.distance(b.position) < NEIGHBORHOOD
			&& !(angleDirection >= minAngleVisionLimit && angleDirection <= maxAngleVisionLimit))
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

