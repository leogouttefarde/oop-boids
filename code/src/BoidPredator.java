import java.util.ArrayList;
import java.util.Iterator;


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
		Iterator<Boid> it= boids.iterator();
		Boid b = it.next();
		while(it.hasNext() && !(isNeighbor(b) && b.behaviour == Behaviour.Prey)) {
			b = it.next();
		}
		
		if(isNeighbor(b) && b.behaviour == Behaviour.Prey){
			preyPosition.add(b.position);
			preyPosition.sub(position);
		} 

		// System.out.println(centerMassPosition);
		return preyPosition.div(deplacementFactor);
	}
	
	//à vérifier soit on attaque soit on suit ces semblable 
	// on attaque des qu'il y a un proie dans son voisinage
	@Override
	public void move(){
		PVector preyPosition = ruleCatchPrey();
		if(preyPosition.x != 0 && preyPosition.y != 0){
			applyForce(preyPosition);
			update();
		} else {
			super.move();
		}
	}
	
	
	
}
