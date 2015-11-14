import java.util.ArrayList;
import java.util.Iterator;

public class Boids {

	private static int maxWidth;
	private static int maxHeight;

	private ArrayList<Boid> boids;
	private ArrayList<Boid> initialState;

	public Boids() {
		boids = new ArrayList<Boid>();
		initialState = new ArrayList<Boid>();
	}


	public void add(float x, float y, float vx, float vy, float ax, float ay) {
		add(new Boid(x, y, vx, vy, ax, ay, this.boids));
	}

	public void add(Boid b) {
		boids.add(b);
		initialState.add(b.clone());
	}
	
	public ArrayList<Boid> getBoids(){
		return boids;
	}

	public static void setWidth(int width) {
		maxWidth = width;
	}

	public static void setHeight(int height) {
		maxHeight = height;
	}

	public static int getWidth() {
		return maxWidth;
	}

	public static int getHeight() {
		return maxHeight;
	}

	public void moveAllBoids(){
		for(Boid b : boids){
			if(b.behaviour != Behaviour.Dead)
				b.move();
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

