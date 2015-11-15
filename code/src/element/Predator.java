package element;

import utility.PVector;
import utility.Type;

import java.awt.Color;
import java.util.Iterator;

/**
 * Classe qui hérite de boid pour implémenter un nouveau type de boid : le type Predator.
 * Le boid Predator est un boid qui se déplace en essaim de même type, qui attaque, mange les boids de type Prey et Lighter.
 * @see Boid
 * @see Prey
 * @see Lighter
 * @author ilyes
 *
 */
public class Predator extends Boid {
	
	private static final Color PRED_COLOR = Color.decode("#990000");
	private static final int PRED_SIZE = 25;
	private static final int PRED_MAX_SPEED = 12;
	private static final int DEATH_RADIUS = 10;

	/**
	 * Crée un boid de type Predator
	 * @param x la coordonnée x de position
	 * @param y la coordonnée y de position
	 * @param sx la coordonnée x de speed
	 * @param sy la coordonnée y de speed
	 * @param ax la coordonnée x de acceleration
	 * @param ay la coordonnée y de acceleration
	 * @see Type
	 * @see Boid#Boid(double, double, double, double, double, double, Type, Color, int)
	 */
	public Predator(double x, double y, double sx, double sy, double ax, double ay) {
		super(x, y, sx, sy, ax, ay, Type.Predator, PRED_COLOR, PRED_SIZE);
		maxspeed = PRED_MAX_SPEED;
	}

	/**
	 * Première règle d'attaque pour le Predator. 
	 * Le boid Predator attaque à vitesse maximal la première proie dans son voisinage visible.
	 * @return Retourne le vecteur de déplacement pour chasser sa proie.
	 * @see Boid#isNeighbor(Boid, Type)
	 * @see PVector
	 */
	protected PVector ruleCatchPrey() {
		Iterator<Boid> it = boids.iterator();
		PVector force = new PVector();
		Boid b;

		do {
			b = it.next();
		} while (it.hasNext() && !isNeighbor(b, Type.Prey));
		
		if(isNeighbor(b, Type.Prey)) {
			force.add(b.position);
			force.sub(position);
			force.mult(PRED_MAX_SPEED);
			force.div(MOVE_FACTOR);
		}

		return force;
	}

	/**
	 * Deuxième règle d'attaque pour le Predator. 
	 * Le boid Predator mange la première proie dans son voisinage visible à une distance DEATH_RADIUS.
	 * @return Retourne le vecteur de déplacement pour chasser sa proie.
	 * @see Boid#isNeighbor(Boid, Type)
	 * @see Predator#DEATH_RADIUS
	 * @see PVector
	 */
	protected boolean kill() {
		Iterator<Boid> it = boids.iterator();
		boolean killed = false;

		while(it.hasNext() && !killed)  {
			Boid b = it.next();

			if(isNeighbor(b, Type.Prey) && position.distance(b.position) <= DEATH_RADIUS) {
				b.die();
				killed = true;
				System.out.println("Prey eaten");
			}
		}

		return killed;
	}
	
	/**
	 * Applique les trois règle de déplacement de l'essaim de même type et les deux règles d'attaque du Predator. 
	 * Les règles sont appliquées par priorité : Manger, Chasser, Se déplacer en essaim de même type.
	 * Met à jour l'état du boid.
	 * @see Boid#ruleFlyToCenter()
	 * @see Boid#ruleKeepDistance()
	 * @see Boid#ruleMatchSpeed()
	 * @see Boid#applyForce(PVector)
	 * @see Boid#update()
	 * @see Predator#ruleCatchPrey()
	 * @see Predator#kill()
	 */
	@Override
	public void move() {
		boolean killed = kill();

		if(!killed) {
			PVector force = ruleCatchPrey();

			if(!force.isNull()) {
				applyForce(force);
				update();
			}
			else {
				super.move();
			}
		}
		else {
			super.move();
		}
	}
}
