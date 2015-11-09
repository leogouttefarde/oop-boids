import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Boids {

	private ArrayList<Boid> boids;


	public Boids() {
		boids = new ArrayList<Boid>();
	}

	public void add(float x, float y) {
		add(new Boid(x, y));
	}

	public void add(Boid b) {
		boids.add(b);
	}

	public void update() {
		
	}

	public void reset() {
		
	}

	public String toString() {
		String str = "Boids\n";

		for (Boid b : boids) {
			str += b + "\n";
		}

		return str;
	}
}

