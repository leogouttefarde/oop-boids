import java.awt.Color;
import java.util.LinkedList;
import java.util.Iterator;


public class Predator extends Boid {
	
	private static Color color = Color.decode("#990000");
	private static int predatorSize = 15;
	private static final int PREDATOR_MAX_SPEED = 12;
	private static final int DISTANCE_TO_EAT = 7;

	public Predator(float x, float y, float vx, float vy, float ax, float ay, LinkedList<Boid> boids) {
		super(x, y, vx, vy, ax, ay, boids, Behaviour.Predator, color, predatorSize);
		maxspeed = PREDATOR_MAX_SPEED;
	}

	// public Predator clone() {
	// 	Predator b = null;

	// 	b = (Predator)super.clone();

	// 	return b;
	// }
	
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
			preyPosition.div(deplacementFactor);
		}

		return preyPosition;
	}
	
	protected boolean eatPrey(){
		Iterator<Boid> it= boids.iterator();
		boolean hasEat = false;
		while(it.hasNext() && !hasEat)  {
			Boid b = it.next();
			if(isNeighbor(b) && b.behaviour == Behaviour.Prey && position.distance(b.position) <= DISTANCE_TO_EAT){
				it.remove();
				hasEat = true;
				//System.out.println("Prey eaten");
			}
		}
		return hasEat;
	}
	
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
	
	// @Override
	// public void move(){
	// 	PVector preyPosition = ruleCatchPrey();
	// 	if(preyPosition.x != 0 && preyPosition.y != 0){
	// 		//System.out.println("There is a prey near");
	// 		applyForce(preyPosition);
	// 		update();
	// 	} else {
	// 		super.move();
	// 	}
	// }
	
	
}
