package element;

import utility.PVector;
import utility.Type;

import java.awt.Color;
import java.util.Iterator;

/**
 * Classe qui hérite de Boid pour implémenter un nouveau type de Boid : le type Prey.
 * Le Boid Prey est un Boid qui se déplace en essaim de même type et qui fuit le Predator.
 * 
 * @see Boid
 * @see Predator
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class Prey extends Boid {
	
	private static final Color PREY_COLOR = Color.decode("#3366FF");
	private static final int PREY_SIZE = BASE_SIZE;
	private static final int PREY_MAX_SPEED = 10;
	
	/**
	 * Crée un boid de type Prey
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
	public Prey(double x, double y, double sx, double sy, double ax, double ay) {
		super(x, y, sx, sy, ax, ay, Type.Prey, PREY_COLOR, PREY_SIZE);
		maxspeed = PREY_MAX_SPEED;
	}

	/**
	 * Nouvelle règle de fuite pour le Boid Prey.
	 * Il fuit à vitesse maximale le premier prédateur dans son voisinage visible.
	 * 
	 * @return Vecteur de déplacement de fuite
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
	 * Applique les trois règles de déplacement de l'essaim de même type
	 * et la règle de fuite.
	 * Les règles sont appliquées par priorité :
	 * Fuir, Se déplacer en essaim de même type.
	 * Met à jour l'état du boid.
	 * 
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

