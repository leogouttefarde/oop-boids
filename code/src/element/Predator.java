package element;

import utility.PVector;
import utility.Type;

import java.awt.Color;
import java.util.Iterator;

/**
 * Classe qui hérite de Boid pour implémenter un nouveau type de Boid : le type Predator.
 * Le boid Predator est un Boid qui se déplace en essaim de même type, qui attaque
 * et tue les Boids de type Prey.
 * 
 * @see Boid
 * @see Prey
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class Predator extends Boid {
	
	private static final Color PRED_COLOR = Color.decode("#990000");
	private static final int PRED_SIZE = 25;
	private static final int PRED_MAX_SPEED = 12;
	private static final int DEATH_RADIUS = 10;

	/**
	 * Crée un Boid de type Predator
	 * 
	 * @param x 	Coordonnée en x
	 * @param y 	Coordonnée en y
	 * @param sx 	Vitesse en x
	 * @param sy 	Vitesse en y
	 * @param ax 	Accélération en x
	 * @param ay 	Accélération en y
	 * @see Type
	 * @see Boid#Boid(double, double, double, double, double, double, Type, Color, int)
	 */
	public Predator(double x, double y, double sx, double sy, double ax, double ay) {
		super(x, y, sx, sy, ax, ay, Type.Predator, PRED_COLOR, PRED_SIZE);
		maxspeed = PRED_MAX_SPEED;
	}

	/**
	 * Première règle d'attaque pour le Predator. 
	 * Le boid Predator attaque à vitesse maximale la première proie dans son voisinage visible.
	 * 
	 * @return Vecteur de déplacement de chasse
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
	 * Seconde règle d'attaque pour le Predator. 
	 * Le boid Predator mange la première proie dans son voisinage visible
	 * à une distance DEATH_RADIUS.
	 * 
	 * @return Vecteur de déplacement de chasse
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
	 * Applique les trois règles de déplacement de l'essaim de même type
	 * et les deux règles d'attaque du Predator. 
	 * Les règles sont appliquées par priorité :
	 * Manger, Chasser, Se déplacer en essaim de même type.
	 * Met à jour l'état du Boid.
	 *
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
