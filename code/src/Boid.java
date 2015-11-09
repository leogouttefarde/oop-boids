
public class Boid {

	public PVector position;
	public PVector velocity;
	public PVector acceleration;

	// float maxforce;
	public float maxspeed;


	public Boid(float x, float y, float vx, float vy, float ax, float ay) {
		position = new PVector(x, y);
		velocity = new PVector(vx, vy);
		acceleration = new PVector(ax, ay);

		maxspeed = 10;
		// maxforce = 0.1;
	}
	
	public void reset(PVector position, PVector velocity, PVector acceleration){
		this.position.setLocation(position);
		this.position.setLocation(velocity);
		this.position.setLocation(acceleration);
	}

	public void update() {
		velocity.add(acceleration);
		// velocity.limit(maxspeed);
		position.add(velocity);
		acceleration.mult(0);
	}

	public void applyForce(PVector force) {
		float m = 1; // C'est quoi m ?
		acceleration.add(force.div(m));
	}

	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + velocity.x + ", vy : " + velocity.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y +")";
	}
}

