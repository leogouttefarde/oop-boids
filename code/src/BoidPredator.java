import java.util.ArrayList;


public class BoidPredator extends Boid{

	public BoidPredator(PVector p, PVector v, PVector a, float ms, float mf, ArrayList<Boid> boids) {
		super(p, v, a, ms, mf, boids, Behaviour.Predator);
	}

	public BoidPredator(float x, float y, float vx, float vy, float ax, float ay, ArrayList<Boid> boids) {
		super(x, y, vx, vy, ax, ay, boids, Behaviour.Predator);
	}
	
	// à vérifier
	protected PVector ruleCatchPrey(){
		PVector preyPosition = new PVector(0, 0);

		// System.out.println("ruleFlyTowardCentreMass");
		
		for(Boid b : boids) {
			if(isNeighbor(b) && b.behaviour == behaviour) {
				preyPosition.add(b.position);
				preyPosition.sub(position);
			}
		}

		// System.out.println(centerMassPosition);
		return preyPosition.div(deplacementFactor);
	}
	
	//à faire 
	@Override
	public void move(){
		// à faire 
		// deplacement classique ou attaque
	}
	
	
	
}
