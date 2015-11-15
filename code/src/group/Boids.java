package group;

import element.Boid;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class Boids {

	private static int width;
	private static int height;

	private LinkedList<Boid> boids;
	private LinkedList<Boid> beginning;

	/**
	 * Crée un groupe de Boids
	 */
	public Boids() {
		boids = new LinkedList<Boid>();
		beginning = new LinkedList<Boid>();
	}

	/**
	 * Ajoute un Boid au groupe
	 * @param b	Boid à ajouter
	 */
	public void add(Boid b) {
		boids.add(b);
		b.setGroup(boids);

		beginning.add(b.clone());
	}

	/**
	 * Setter de la largeur
	 * @param width		Largeur
	 */
	public static void setWidth(int width) {
		Boids.width = width;
	}

	/**
	 * Setter de la hauteur
	 * @param height	Hauteur
	 */
	public static void setHeight(int height) {
		Boids.height = height;
	}

	/**
	 * Getter de la largeur
	 * return	Largeur
	 */
	public static int getWidth() {
		return width;
	}

	/**
	 * Getter de la hauteur
	 * return	Hauteur
	 */
	public static int getHeight() {
		return height;
	}

	/**
	 * Déplace tous les Boid
	 */
	public void moveAllBoids(){
		Iterator<Boid> it = boids.iterator();
		Boid b;

		while (it.hasNext()) {
			b = it.next();

			b.move();

			if (b.isDead()) {
				it.remove();
			}
		}
	}

	/**
	 * Met à jour les différents Boid
	 */
	public void update() {
		moveAllBoids();
	}

	/**
	 * Reinitialise le groupe
	 */
	public void reset() {
		Boid curBoid;
		Iterator<Boid> it= beginning.iterator();
		
		for(Boid b : boids) {
			curBoid = it.next();
			b.reset(curBoid.getPosition(), curBoid.getSpeed(),
					curBoid.getAcceleration());
		}
	}

	/**
	 * Fournit un itérateur sur les Boid
	 * @return Itérateur
	 */
	public Iterator<Boid> iterator(){
		return boids.iterator();
	}

	/**
	 * Affiche les Boid
	 * @return	Représentation textuelle
	 */
	public String toString() {
		String str = "Boids\n";

		for (Boid b : boids) {
			str += b + "\n";
		}

		return str;
	}
}

