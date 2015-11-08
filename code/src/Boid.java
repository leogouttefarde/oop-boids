import java.awt.geom.Point2D;

public class Boid {

	public PVector location;
	public PVector velocity;
	public PVector acceleration;

	// float maxforce;
	public float maxspeed;


	public Boid(float x, float y) {
		location = new PVector(x, y);
		velocity = new PVector(0, 0);
		acceleration = new PVector(0, 0);

		maxspeed = 10;
		// maxforce = 0.1;
	}

	void update() {
		velocity.add(acceleration);
		// velocity.limit(maxspeed);
		location.add(velocity);
		acceleration.mult(0);
	}

	public void applyForce(PVector force) {
		float m = 1; // C'est quoi m ?
		acceleration.add(force.div(m));
	}

	public String toString() {
		return "Boid(" + location.x + "," + location.y + ")";
	}
}

