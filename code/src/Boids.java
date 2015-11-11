import java.util.ArrayList;
import java.util.Iterator;

public class Boids {

	static int MAX_HEIGHT = 500;
	static int MAX_WIDTH = 500;

	private ArrayList<Boid> boids;
	private ArrayList<Boid> initialState;
	private static final int deplacementFactor = 100;
	private static final int smallDistance = 5;
	private static final int velocityFactor = 8;

	public Boids() {
		boids = new ArrayList<Boid>();
		initialState = new ArrayList<Boid>();
	}

	public void add(float x, float y, float vx, float vy, float ax, float ay) {
		add(new Boid(x, y, vx, vy, ax, ay));
	}

	public void add(Boid b) {
		boids.add(b);
		initialState.add(b.clone());
	}

	// First rule : Boids try to fly towards the center of mass of neighboring boids
	private PVector ruleFlyTowardCentreMass(Boid currentBoid){
		PVector centerMassPosition = new PVector(0, 0);
		int nb = 0;

		// System.out.println("ruleFlyTowardCentreMass");

		for(Boid b : boids) {
			if(currentBoid.isNeighbor(b)) {
				centerMassPosition.add(b.position);
				nb++;
			}
		}

		if (nb > 0) {
			centerMassPosition.div(nb);
			centerMassPosition.sub(currentBoid.position);
		}

		// System.out.println(centerMassPosition);
		return centerMassPosition.div(deplacementFactor);
	}

	// // Second rule : Boids try to keep a small distance away from other objects (including other boids)
	// private PVector ruleKeepDistance(Boid currentBoid){
	// 	PVector d = new PVector(0, 0);
	// 	PVector tmp = new PVector(0, 0);
		
	// 	for(Boid b : boids){
	// 		if(b != currentBoid){
	// 			if(b.position.distance(currentBoid.position) < smallDistance){
	// 				tmp.add(b.position);
	// 				tmp.add(currentBoid.position.mult(-1));
	// 				d.add(tmp.mult(-1));
	// 			}	
	// 		}
	// 	}
		
	// 	return d;
	// }

	// Second rule : Boids try to keep a small distance away from other objects (including other boids)
	private PVector ruleKeepDistance(Boid currentBoid){
		// System.out.println("ruleKeepDistance");
		PVector d = new PVector(0, 0);
		// System.out.println("d");
		// System.out.println(d);

		for(Boid b : boids){
			if(currentBoid.isNeighbor(b)){
				if(currentBoid.position.distance(b.position) < smallDistance){
					PVector tmp = new PVector(0, 0);

					tmp.add(b.position);
					tmp.sub(currentBoid.position);
					tmp.mult(-1);

					// Multiply by speed so it doesn't absorb this rule
					tmp.mult(currentBoid.velocity);

					d.add(tmp);
				}
			}
		}

		// System.out.println("d");
		// System.out.println(d);

		return d;
	}

	// Third rule : Boids try to match velocity with near boids
	private PVector ruleMatchVelocity(Boid currentBoid){
		PVector v = new PVector(0, 0);
		int nb = 0;

		// System.out.println("ruleMatchVelocity");

		for(Boid b : boids) {
			if(currentBoid.isNeighbor(b)) {
				v.add(b.velocity);
				nb++;
			}
		}

		if (nb > 0) {
			v.div(nb);
			v.sub(currentBoid.velocity);
		}
		
		// System.out.println(v);
		return v.div(velocityFactor);
	}

	// public void moveAllBoids(){
	// 	PVector v1, v2, v3;
		
	// 	for(Boid b : boids){
	// 		v1 = ruleFlyTowardCentreMass(b);
	// 		v2 = ruleKeepDistance(b);
	// 		v3 = ruleMatchVelocity(b);
			
	// 		b.velocity.add(v1);
	// 		b.velocity.add(v2);
	// 		b.velocity.add(v3);
			
	// 		b.position.add(b.velocity);
	// 	}
	// }

	public void moveAllBoids(){
		PVector v1, v2, v3;
		
		for(Boid b : boids){
			v1 = ruleFlyTowardCentreMass(b);
			v2 = ruleKeepDistance(b);
			v3 = ruleMatchVelocity(b);

			b.applyForce(v1);
			b.applyForce(v2);
			b.applyForce(v3);

			b.update();
		}
	}

	public void update() {
		moveAllBoids();
	}

	public void reset() {
		Boid currentBoid;
		Iterator<Boid> it= initialState.iterator();
		System.out.println("\n\nreset !");
		System.out.println(initialState);
		System.out.println(boids);
		for(Boid b : boids){
			currentBoid = it.next();
			b.reset(currentBoid.position, currentBoid.velocity, currentBoid.acceleration);
		}
		System.out.println(boids);
	}

	public Iterator<Boid> iterator(){
		return boids.iterator();
	}

	public String toString() {
		String str = "Boids\n";

		for (Boid b : boids) {
			str += b + "\n";
		}

		return str;
	}
}

