import java.util.ArrayList;

public class BoidPrey extends Boid{
	
	public BoidPrey(PVector p, PVector v, PVector a, float ms, float mf, ArrayList<Boid> boids) {
		super(p, v, a, ms, mf, boids, Behaviour.Prey);
	}

	public BoidPrey(float x, float y, float vx, float vy, float ax, float ay, ArrayList<Boid> boids) {
		super(x, y, vx, vy, ax, ay, boids, Behaviour.Prey);
	}
	
	// à faire
	protected PVector ruleEscapeFromPredator(){
		PVector predatorPosition = new PVector(0, 0);
		
		return predatorPosition.div(deplacementFactor);
	}

	//à faire
	@Override
	public void move(){
		
	}
}
