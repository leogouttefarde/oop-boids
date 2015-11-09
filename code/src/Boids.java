import java.util.ArrayList;
import java.util.Iterator;

public class Boids {

	private ArrayList<Boid> boids;
	private ArrayList<Boid> initialState;
	private static final int deplacementFactor = 100;
	private static final int smallDistance = 5;
	private static final int velocityFactor = 8;

	public Boids() {
		this.boids = new ArrayList<Boid>();
		this.initialState = new ArrayList<Boid>();
	}

	public void add(float x, float y, float vx, float vy, float ax, float ay) {
		this.add(new Boid(x, y, vx, vy, ax, ay));
	}

	public void add(Boid b) {
		this.boids.add(b);
		this.initialState.add(b);
	}

	// first rule : Boids try to fly towards the centre of mass of neighbouring boids
	private PVector ruleFlyTowardCentreMass(Boid currentBoid){
		PVector centerMassPosition = new PVector(0, 0);
		
		for(Boid b : boids)
			if(b != currentBoid)
				centerMassPosition.add(b.position);

		centerMassPosition.div(boids.size() - 1);
		centerMassPosition.add(currentBoid.position.mult(-1));
		
		return centerMassPosition.div(deplacementFactor);
	}
	
	// second rule : Boids try to keep a small distance away from other objects (including other boids)
	private PVector ruleKeepDistance(Boid currentBoid){
		PVector d = new PVector(0, 0);
		PVector tmp = new PVector(0, 0);
		
		for(Boid b : boids){
			if(b != currentBoid){
				if(PVector.distance(b.position.getX(), b.position.getY(), currentBoid.position.getX(), currentBoid.position.getY()) < smallDistance){
					tmp.add(b.position);
					tmp.add(currentBoid.position.mult(-1));
					d.add(tmp.mult(-1));
				}	
			}
		}
		
		return d;
	}
	
	// third rule : Boids try to match velocity with near boids
	private PVector ruleMatchVelocity(Boid currentBoid){
		PVector v = new PVector(0, 0);
		
		for(Boid b : boids)
			if(b != currentBoid)
				v.add(b.velocity);
		
		v.div(boids.size() - 1);
		v.add(currentBoid.velocity.mult(-1));
		
		return v.div(velocityFactor);
	}
	
	public void moveAllBoids(){
		PVector v1, v2, v3;
		
		for(Boid b : boids){
			v1 = ruleFlyTowardCentreMass(b);
			v2 = ruleKeepDistance(b);
			v3 = ruleMatchVelocity(b);
			
			b.velocity.add(v1);
			b.velocity.add(v2);
			b.velocity.add(v3);
			
			b.position.add(b.velocity);
		}
	}
	
	public void update() {
		this.moveAllBoids();
	}

	public void reset() {
		Boid currentBoid;
		Iterator<Boid> it= initialState.iterator();
		for(Boid b : boids){
			currentBoid = it.next();
			b.reset(currentBoid.position, currentBoid.velocity, currentBoid.acceleration);
		}
	}
	
	public Iterator<Boid> iterator(){
		return this.boids.iterator();
	}

	public String toString() {
		String str = "Boids\n";

		for (Boid b : boids) {
			str += b + "\n";
		}

		return str;
	}
}

