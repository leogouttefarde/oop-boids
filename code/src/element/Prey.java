package element;

import utility.PVector;
import utility.Type;

import java.awt.Color;
import java.util.Iterator;

/**
 * Classe qui hérite de boid pour implémenter un nouveau type de boid : le type Prey.
 * Le boid Prey est un boid qui se déplace en essaim de même type et qui fuit ces prédateur.
 * @see Boid
 * @see Predator
 * @author ilyes
 *
 */
public class Prey extends Boid {
	
	private static final Color PREY_COLOR = Color.decode("#3366FF");
	private static final int PREY_SIZE = BASE_SIZE;
	private static final int PREY_MAX_SPEED = 10;
	
	/**
	 * Crée un boid de type Prey
	 * @param x la coordonnée x de position
	 * @param y la coordonnée y de position
	 * @param sx la coordonnée x de speed
	 * @param sy la coordonnée y de speed
	 * @param ax la coordonnée x de acceleration
	 * @param ay la coordonnée y de acceleration
	 * @see Type
	 * @see Boid#Boid(double, double, double, double, double, double, Type, Color, int)
	 */
	public Prey(double x, double y, double sx, double sy, double ax, double ay) {
		super(x, y, sx, sy, ax, ay, Type.Prey, PREY_COLOR, PREY_SIZE);
		maxspeed = PREY_MAX_SPEED;
	}

	/**
	 * Nouvelle règle de fuite pour le Prey. 
	 * Le boid Prey fuit à vitesse maximal le premier prédateur dans son voisinage visible.
	 * @return Retourne le vecteur de déplacement pour fuir son prédateur.
	 * @see Boid#isNeighbor(Boid, Type)
	 * @see PVector
	 */
	protected PVector ruleEscapeFromPredator() {
		Iterator<Boid> it = boids.iterator();
		PVector force = new PVector();
		Boid b;

		do {
			b = it.next();
		} while (it.hasNext() && !isNeighbor(b, Type.Predator));

		if(isNeighbor(b, Type.Predator)) {
			force.add(position);
			force.sub(b.position);
			force.mult(PREY_MAX_SPEED);
			force.div(MOVE_FACTOR);
		} 

		return force;
	}

	/**
	 * Applique les trois règle de déplacement de l'essaim de même type et la règle de fuite du Prey. 
	 * Les règles sont appliquées par priorité : Fuir, Se déplacer en essaim de même type.
	 * Met à jour l'état du boid.
	 * @see Boid#ruleFlyToCenter()
	 * @see Boid#ruleKeepDistance()
	 * @see Boid#ruleMatchSpeed()
	 * @see Boid#applyForce(PVector)
	 * @see Boid#update()
	 * @see Prey#ruleEscapeFromPredator()
	 */
	@Override
	public void move() {
		PVector force = ruleEscapeFromPredator();

		if(!force.isNull()) {
			applyForce(force);
			update();
		}
		else {
			super.move();
		}
	}
}

