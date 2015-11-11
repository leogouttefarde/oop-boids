
public class Boid implements Cloneable {

	public static int NEIGHBORHOOD = 55;
	public static int DEFAULT_MAX_SPEED = 10;
	public static int DEFAULT_MAX_FORCE = 77;

	public PVector position;
	public PVector velocity;
	public PVector acceleration;

	public float maxforce;
	public float maxspeed;


	public Boid(float x, float y, float vx, float vy, float ax, float ay) {
		position = new PVector(x, y);
		velocity = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);

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

	public float mod(float a, float b) {
		float r = a % b;

		return (r < 0) ? r+b : r;
	}

	public void setPos(PVector pos){
		pos.x = mod(pos.x, Boids.MAX_WIDTH);
		pos.y = mod(pos.y, Boids.MAX_HEIGHT);

		this.position.setLocation(pos);
	}

	public boolean isNeighbor(Boid b) {
		if (this != b && position.distance(b.position) < NEIGHBORHOOD)
			return true;

		return false;
	}

	public void update() {
		velocity.add(acceleration);
		velocity.limit(maxspeed);
		setPos(position.clone().add(velocity));
		acceleration.mult(0);
	}

	public void applyForce(PVector force) {
		float m = 1; // m = masse du Boid
		force.limit(maxforce);
		acceleration.add(force.div(m));
	}

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + velocity.x + ", vy : " + velocity.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y +")";
	}
}

