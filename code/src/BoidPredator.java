import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;


public class BoidPredator extends Boid{
	
	private static Color color = Color.decode("#990000");
	private static int predatorSize = 15;
	private static final int PREDATOR_MAX_SPEED = 12;
	private static final int DISTANCE_TO_EAT = 5;

	public BoidPredator(PVector p, PVector v, PVector a, float ms, float mf, ArrayList<Boid> boids) {
		super(p, v, a, ms, mf, boids, Behaviour.Predator, color, predatorSize);
		maxspeed = PREDATOR_MAX_SPEED;
	}

	public BoidPredator(float x, float y, float vx, float vy, float ax, float ay, ArrayList<Boid> boids) {
		super(x, y, vx, vy, ax, ay, boids, Behaviour.Predator, color, predatorSize);
		maxspeed = PREDATOR_MAX_SPEED;
	}
	
	protected PVector ruleCatchPrey(){
		PVector preyPosition = new PVector(0, 0);

		Iterator<Boid> it= boids.iterator();
		Boid b = it.next();
		while(it.hasNext() && !(isNeighbor(b) && b.behaviour == Behaviour.Prey)) {
			b = it.next();
		}
		
		if(isNeighbor(b) && b.behaviour == Behaviour.Prey){
			preyPosition.add(b.position);
			preyPosition.sub(position);
			preyPosition.mult(PREDATOR_MAX_SPEED);
		} 
		return preyPosition.div(deplacementFactor);
	}
	
	protected boolean eatPrey(){
		
		Iterator<Boid> it= boids.iterator();
		Boid b = it.next();
		while(it.hasNext() && !(isNeighbor(b) && b.behaviour == Behaviour.Prey) && position.distance(b.position) > DISTANCE_TO_EAT) {
			b = it.next();
		}
		if(isNeighbor(b) && b.behaviour == Behaviour.Prey && position.distance(b.position) <= DISTANCE_TO_EAT){
			it.remove();
			return true;
		}
		return false;
	}
	/*//eatPrey bug : leve une exception lors de la suppression
	@Override
	public void move(){
		if(!eatPrey()){
			PVector preyPosition = ruleCatchPrey();
			if(preyPosition.x != 0 && preyPosition.y != 0){
				applyForce(preyPosition);
				update();
			} else {
				super.move();
			}
		}
		else {
			super.move();
		}
	}
	*/
	
	@Override
	public void move(){
		PVector preyPosition = ruleCatchPrey();
		if(preyPosition.x != 0 && preyPosition.y != 0){
			//System.out.println("There is a prey near");
			applyForce(preyPosition);
			update();
		} else {
			super.move();
		}
	}
	
	
}
