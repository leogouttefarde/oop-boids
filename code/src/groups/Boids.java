package groups;

import elements.Boid;
import java.util.LinkedList;
import java.util.Iterator;

public class Boids {

	private static int width;
	private static int height;

	private LinkedList<Boid> boids;
	private LinkedList<Boid> beginning;

	public Boids() {
		boids = new LinkedList<Boid>();
		beginning = new LinkedList<Boid>();
	}

	public void add(Boid b) {
		boids.add(b);
		b.setGroup(boids);

		beginning.add(b.clone());
	}

	public static void setWidth(int width) {
		Boids.width = width;
	}

	public static void setHeight(int height) {
		Boids.height = height;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public void moveAllBoids(){
		for(Boid b : boids){
			b.move();
		}
	}

	public void update() {
		moveAllBoids();
	}

	public void reset() {
		Boid currentBoid;
		Iterator<Boid> it= beginning.iterator();
		System.out.println("\n\nreset !");
		System.out.println(beginning);
		System.out.println(this);
		for(Boid b : boids) {
			currentBoid = it.next();
			b.reset(currentBoid.getPosition(), currentBoid.getSpeed(), currentBoid.getAcceleration());
		}
		System.out.println(this);
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

