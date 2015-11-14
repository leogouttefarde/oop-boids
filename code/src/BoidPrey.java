import java.awt.Color;
import java.util.LinkedList;
import java.util.Iterator;

public class BoidPrey extends Boid{
	
	private static Color color = Color.decode("#3366FF");
	private static int preySize = 7;
	private static final int PREY_MAX_SPEED = 10;
	
	public BoidPrey(PVector p, PVector v, PVector a, float ms, float mf, LinkedList<Boid> boids) {
		super(p, v, a, ms, mf, boids, Behaviour.Prey, color, preySize);
		maxspeed = PREY_MAX_SPEED;
	}

	public BoidPrey(float x, float y, float vx, float vy, float ax, float ay, LinkedList<Boid> boids) {
		super(x, y, vx, vy, ax, ay, boids, Behaviour.Prey, color, preySize);
		maxspeed = PREY_MAX_SPEED;
	}
	
	protected PVector ruleEscapeFromPredator(){
		PVector predatorPosition = new PVector(0, 0);
		
		Iterator<Boid> it= boids.iterator();
		Boid b = it.next();
		while(it.hasNext() && !(isNeighbor(b) && b.behaviour == Behaviour.Predator)) {
			b = it.next();
		}

		if(isNeighbor(b) && b.behaviour == Behaviour.Predator){
			predatorPosition.add(b.position);
			predatorPosition.sub(position);
			predatorPosition.mult(-PREY_MAX_SPEED);
		} 

		return predatorPosition.div(deplacementFactor);
	}

	@Override
	public void move(){
		PVector predatorPosition = ruleEscapeFromPredator();
		if(predatorPosition.x != 0 && predatorPosition.y != 0){
			//System.out.println("PREDATOR near !!!!");
			applyForce(predatorPosition);
			update();
		} else {
			super.move();
		}
	}
}
