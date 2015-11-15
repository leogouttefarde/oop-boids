package element;

import utility.PVector;
import utility.Type;
import group.Boids;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 * Classe abstraite qui regroupe les parties communes entre les différents
 * types de Boid.
 *
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public abstract class Boid implements Cloneable {

	protected static final int NEIGHBORHOOD = 55;
	protected static final int BASE_MAX_SPEED = 10;
	protected static final int BASE_MAX_FORCE = 77;
	protected static final int BASE_SIZE = 14;

	protected static final int MOVE_FACTOR = 100;
	protected static final int MIN_DIST = 20;
	protected static final double VISION = 2*Math.PI/3;

	protected PVector position;
	protected PVector speed;
	protected PVector acceleration;
	protected LinkedList<Boid> boids;

	protected Type type;

	protected Color color;
	protected int size;
	private boolean dead;

	private double direction;

	protected double maxforce;
	protected double maxspeed;

	protected int triXPts[];
	protected int triYPts[];

	/**
	 * Crée un boid avec des valeurs prédéfinies
	 * 
	 * @param x 	Coordonnée en x
	 * @param y 	Coordonnée en y
	 * @param sx 	Vitesse en x
	 * @param sy 	Vitesse en y
	 * @param ax 	Accélération en x
	 * @param ay 	Accélération en y
	 * @param type 	Type de boid 
	 * @see Type
	 * @see Predator
	 * @see Prey
	 * @see Lighter
	 * @param color Couleur du boid
	 * @param size 	Taille du triangle en longueur
	 * @see Boid#computeTriangle()
	 */
	public Boid(double x, double y, double sx, double sy, double ax, double ay,
				Type type, Color color, int size) {
		position = new PVector(x, y);
		speed = new PVector(sx, sy);
		acceleration = new PVector(ax, ay);

		computeDirection();

		maxspeed = BASE_MAX_SPEED;
		maxforce = BASE_MAX_FORCE;

		triXPts = new int[3];
		triYPts = new int[3];

		this.type = type;
		this.color = color;
		this.size = size;
		dead = false;
	}

	/**
	 * Clone le Boid
	 * 
	 * @return Clone
	 */
	public Boid clone() {
		Boid b = null;

		try {
			b = (Boid)super.clone();
			b.position = position.clone();
			b.speed = speed.clone();
			b.acceleration = acceleration.clone();
		}
		catch (CloneNotSupportedException e) {
			// Erreur de clone (n'arrive pas)
		}

		return b;
	}
	
	/**
	 * Retourne le type du Boid
	 * 
	 * @return Type du Boid
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Retourne la position du Boid
	 * 
	 * @return Position du Boid
	 * @see PVector
	 */
	public PVector getPosition(){
		return position;
	}

	/**
	 * Retourne la vitesse du boid
	 * 
	 * @return Vitesse du boid
	 * @see PVector
	 */
	public PVector getSpeed(){
		return speed;
	}

	/**
	 * Retourne l'accélération du Boid
	 * 
	 * @return Accélération du Boid
	 * @see PVector
	 */
	public PVector getAcceleration(){
		return acceleration;
	}

	/**
	 * Retourne la distance minimale entre deux Boids voisins
	 * 
	 * @return Distance minimale
	 * @see Boid#ruleKeepDistance()
	 */
	public int GetMinDist() {
		return Boid.MIN_DIST;
	}

	
	/**
	 * Définit le groupe auquel ce Boid appartient
	 * 
	 * @param group	Groupe de Boids
	 */
	public void setGroup(LinkedList<Boid> group) {
		this.boids = group;
	}
	
	/**
	 * Calcule la direction du Boid
	 */
	private void computeDirection() {
		direction = aMod(Math.atan2(speed.getY(), speed.getX()));
	}

	/**
	 * Initialise la position, la vitesse, l'accélération et la direction du Boid
	 * 
	 * @param position 		Nouvelle position
	 * @param speed 		Nouvelle vitesse
	 * @param acceleration 	Nouvelle accélération
	 * @see Boid#computeDirection()
	 */
	public void reset(PVector position, PVector speed, PVector acceleration) {
		this.position.setLocation(position);
		this.speed.setLocation(speed);
		this.acceleration.setLocation(acceleration);

		computeDirection();
	}

	/**
	 * Retourne la masse du Boid
	 * 
	 * @return Masse
	 */
	public int getMass() {
		return 1;
	}

	/**
	 * Retourne la direction du Boid
	 * 
	 * @return Direction
	 */
	public double getDirection() {
		return direction;
	}

	/**
	 * Retourne la couleur du Boid
	 * 
	 * @return Couleur
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Retourne la taille du Boid
	 * 
	 * @return Taille
	 * @see Boid#computeTriangle()
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Retourne vrai si le Boid est mort, faux sinon
	 * 
	 * @return Vrai ou faux
	 * @see Boid#dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Prépare le Boid à mourir
	 * @see Boid#dead
	 */
	public void die() {
		dead = true;
	}

	/**
	 * Applique une rotation de centre {@link #position} et
	 * d'angle {@link #direction} pour le point (x, y).
	 * 
	 * @param x 	Coordonnée en x
	 * @param y 	Coordonnée en y
	 * 
	 * @return Tableau à 2 éléments contenant le résultat de la rotation
	 * @see Boid#computeTriangle()
	 */
	public double[] turn(double x, double y) {
		double[] pts = { x , y };

		AffineTransform.getRotateInstance(getDirection(), position.getX(),
							position.getY()).transform(pts, 0, pts, 0, 1);

		return pts;
	}

	/**
	 * Calcule les trois points du Triangle correspondant au Boid
	 * 
	 * @see Boid#turn(double, double)
	 * @see Boid#triXPts
	 * @see Boid#triYPts
	 */
	public void computeTriangle() {
		final double cx = position.getX();
		final double cy = position.getY();
		final double curSize = (double)size;
		final double downSize = curSize / 4;

		double[] pts = turn(cx + 2*curSize/3, cy);
		triXPts[0] = (int)pts[0];
		triYPts[0] = (int)pts[1];

		pts = turn(cx - curSize/3, cy + downSize);
		triXPts[1] = (int)pts[0];
		triYPts[1] = (int)pts[1];

		pts = turn(cx - curSize/3, cy - downSize);
		triXPts[2] = (int)pts[0];
		triYPts[2] = (int)pts[1];
	}

	/**
	 * Getter des coordonnées en X du triangle
	 * 
	 * @return Coordonnées en X
	 * @see Boid#triXPts
	 */
	public int[] getTriangleX() {
		return triXPts;
	}

	/**
	 * Getter des coordonnées en Y du triangle
	 * 
	 * @return Coordonnées en Y
	 * @see Boid#triYPts
	 */
	public int[] getTriangleY() {
		return triYPts;
	}

	/**
	 * Calcule un modulo positif.
	 * 
	 * @param a		Premier paramètre
	 * @param b		Second paramètre
	 * 
	 * @return	Modulo positif
	 */
	public static double mod(double a, double b) {
		double r = a % b;

		return (r < 0) ? (r + b) : r;
	}

	/**
	 * Calcule le modulo 2*PI positif d'un angle
	 * 
	 * @param a	Angle donné
	 * 
	 * @return	Modulo positif
	 */
	public static double aMod(double a) {
		return mod(a, 2 * Math.PI);
	}

	/**
	 * Met à jour la position du Boid de manière à ignorer les bords de la fenêtre graphique.
	 * 
	 * @param pos	Position du Boid
	 */
	public void setPos(PVector pos) {
		pos.x = mod(pos.x, Boids.getWidth());
		pos.y = mod(pos.y, Boids.getHeight());

		this.position.setLocation(pos);
	}
	
	/**
	 * Indique si b est un voisin visible de même type que le Boid
	 * 
	 * @param b		Un boid
	 * @param type	Type de Boid
	 * 
	 * @return Vrai si b est un voisin visible de même type
	 * @see Boid#isNeighbor(Boid)
	 */
	public boolean isNeighbor(Boid b, Type type) {
		return (b.type == type) && isNeighbor(b);
	}
	
	/**
	 * Indique si b est dans le voisinage du Boid
	 * 
	 * @param b		Un Boid
	 * 
	 * @return Vrai si b est dans le voisinage
	 */
	public boolean isNear(Boid b) {
		return this != b && position.distance(b.position) < NEIGHBORHOOD;
	}

	/**
	 * Indique si b est un voisin visible du Boid
	 * 
	 * @param b		Un boid
	 * 
	 * @return Vrai si b est un voisin visible
	 */
	public boolean isNeighbor(Boid b) {
		
		// Calcul de l'orientation de b par rapport à this
		double dirRelativX = b.position.x - position.x;
		double dirRelativY = b.position.y - position.y;
		
		double neighborDir = aMod(Math.atan2(dirRelativY, dirRelativX));

		double minAngle = aMod(direction + VISION);
		double maxAngle = aMod(direction - VISION);
		
		if (maxAngle < minAngle) {
			final double tmp = maxAngle;
			maxAngle = minAngle;
			minAngle = tmp;
		}
		
		// Vérification qu'il s'agit d'un voisin dans le champs de vision
		if (this != b && position.distance(b.position) < NEIGHBORHOOD
			 && !(minAngle <= neighborDir && neighborDir <= maxAngle))
			return true;
		
		
		return false;
	}
	
	/**
	 * Première règle de déplacement en essaim. 
	 * Le boid se déplace vers le centre de gravité de ses voisins.
	 * 
	 * @return Retourne un vecteur de déplacement vers le centre de gravité
	 * @see Boid#isNeighbor(Boid, Type)
	 * @see PVector
	 */
	protected PVector ruleFlyToCenter() {
		PVector force = new PVector();
		int count = 0;

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				force.add(b.position);
				count++;
			}
		}

		if (count > 0) {
			force.div(count);
			force.sub(position);
			force.div(MOVE_FACTOR);
		}

		return force;
	}
	
	/**
	 * Seconde règle de déplacement en essaim.
	 * Le boid se replace pour être à une distance minimale de tous les boids de son voisinage.
	 * 
	 * @return Retourne le vecteur de déplacement pour s'éloigner des bois trop proches
	 * @see Boid#isNear(Boid)
	 * @see PVector
	 */
	protected PVector ruleKeepDistance() {
		PVector forces = new PVector();

		for(Boid b : boids) {
			if(isNear(b)) {
				if(position.distance(b.position) < GetMinDist()) {
					PVector force = new PVector();

					force.add(position);
					force.sub(b.position);

					forces.add(force);
				}
			}
		}

		return forces;
	}
	
	/**
	 * Troisième règle de déplacement en essaim. 
	 * Le boid adapte sa vitesse pour rejoindre un essaim qui est dans son voisinage visible.
	 * 
	 * @return Retourne le vecteur de déplacement pour adapter sa vitesse
	 * @see Boid#isNeighbor(Boid, Type)
	 * @see PVector
	 */
	protected PVector ruleMatchSpeed() {
		PVector v = new PVector();
		int count = 0;

		for(Boid b : boids) {
			if(isNeighbor(b, type)) {
				v.add(b.speed);
				count++;
			}
		}

		if (count > 0) {
			v.div(count);
			v.sub(speed);
		}
		
		return v;
	}
	
	/**
	 * Met à jour l'état du Boid.
	 */
	public void update() {
		speed.add(acceleration);
		speed.limit(maxspeed);
		computeDirection();

		setPos(position.clone().add(speed));
		acceleration.reset();
	}

	/**
	 * Applique une force sur le Boid
	 * 
	 * @param force 	Force à appliquer
	 * @see PVector
	 */
	public void applyForce(PVector force) {
		force.limit(maxforce);
		acceleration.add(force.div(getMass()));
	}

	/**
	 * Applique les trois règle de déplacement de l'essaim et met à jour l'état du Boid.
	 * 
	 * @see Boid#ruleFlyToCenter()
	 * @see Boid#ruleKeepDistance()
	 * @see Boid#ruleMatchSpeed()
	 * @see Boid#applyForce(PVector)
	 * @see Boid#update()
	 */
	public void move() {
		PVector force;

		force = ruleFlyToCenter();
		force.add( ruleKeepDistance() );
		force.add( ruleMatchSpeed() );

		applyForce(force);
		update();
	}
	
	/**
	 * @return Retourne une String qui contient l'état du Boid à afficher
	 */
	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + speed.x + ", vy : " 
				+ speed.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y + ")";
	}
}

