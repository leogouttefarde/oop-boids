package element;

import utility.PVector;
import utility.Type;
import group.Boids;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 * Classe abstraite qui regroupe les parties commune entre les différents type de Boid (Prey, Predator, Lighter)
 * @author ilyes
 *
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
	 * Crée un boid avec des valeurs prédéfinis
	 * @param x la coordonnée x de position
	 * @param y la coordonnée y de position
	 * @param sx la coordonnée x de speed
	 * @param sy la coordonnée y de speed
	 * @param ax la coordonnée x de acceleration
	 * @param ay la coordonnée y de acceleration
	 * @param type le type de boid 
	 * @see Type
	 * @see Predator
	 * @see Prey
	 * @see Lighter
	 * @param color la couleur du boid
	 * @param size le paramètre de taille pour calculer les points du Triangle
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
	 * Retourne une copie du boid
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
			System.out.println("CloneError");
		}

		return b;
	}
	
	/**
	 * 
	 * @return Retourne le type du boid
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * @return Retourne la position du boid
	 * @see PVector
	 */
	public PVector getPosition(){
		return position;
	}
	
	/**
	 * @return Retourne la vitesse du boid
	 * @see PVector
	 */
	public PVector getSpeed(){
		return speed;
	}
	
	/**
	 * @return Retourne l'accélération du boid
	 * @see PVector
	 */
	public PVector getAcceleration(){
		return acceleration;
	}

	/**
	 * @return Retourne la distance minimale entre deux boids voisin
	 * @see Boid#ruleKeepDistance()
	 */
	public int GetMinDist() {
		return Boid.MIN_DIST;
	}

	
	public void setGroup(LinkedList<Boid> group) {
		this.boids = group;
	}
	
	/**
	 * Calcul la direction du boid
	 */
	private void computeDirection() {
		direction = aMod(Math.atan2(speed.getY(), speed.getX()));
	}

	/**
	 * Initialise la position, la vitesse, l'accélération et la direction du boid
	 * @param position la nouvelle position
	 * @param speed la nouvelle vitesse
	 * @param acceleration la nouvelle accélération
	 * @see Boid#computeDirection()
	 */
	public void reset(PVector position, PVector speed, PVector acceleration) {
		this.position.setLocation(position);
		this.speed.setLocation(speed);
		this.acceleration.setLocation(acceleration);

		computeDirection();
	}

	/**
	 * 
	 * @return Retourne la masse d'un boid
	 */
	public int getMass() {
		return 1;
	}

	/**
	 * 
	 * @return Retourne la direction du boid
	 */
	public double getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @return Retourne la couleur du boid
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * 
	 * @return Retourne la taille du boid
	 * @see Boid#computeTriangle()
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 
	 * @return Retourne vrai si le boid est mort, faux sinon
	 * @see Boid#dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Met à vrai l'attribut dead du boid
	 * @see Boid#dead
	 */
	public void die() {
		dead = true;
	}

	/**
	 * Applique une rotation de centre position et d'angle direction pour le point (x, y).
	 * Cette méthode est utilisé pour orienter le Triangle dans la bonne direction.
	 * @param x la coordonnée x 
	 * @param y la coordonnée y
	 * @return Retourne un tableau à 2 éléments qui contient le resultat de la rotation
	 * @see Boid#computeTriangle()
	 */
	public double[] turn(double x, double y) {
		double[] pts = { x , y };

		AffineTransform.getRotateInstance(getDirection(), position.getX(),
							position.getY()).transform(pts, 0, pts, 0, 1);

		return pts;
	}

	/**
	 * Calcul les trois points du Triangle correspondant au boid
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
	 * @return Retourne les coordonnées x des trois points du Triangle
	 * @see Boid#triXPts
	 */
	public int[] getTriangleX() {
		return triXPts;
	}

	/**
	 * @return Retourne les coordonnées y des trois points du Triangle
	 * @see Boid#triYPts
	 */
	public int[] getTriangleY() {
		return triYPts;
	}

	/**
	 * @return Retourne a % b
	 */
	public static double mod(double a, double b) {
		double r = a % b;

		return (r < 0) ? (r + b) : r;
	}

	/**
	 * @return a % 2 * Math.PI
	 */
	public static double aMod(double a) {
		return mod(a, 2 * Math.PI);
	}

	/**
	 * Met à jour la position du boid de manière ignorer les bords de la fenêtre graphique.
	 * @param pos la position du boid
	 */
	public void setPos(PVector pos) {
		pos.x = mod(pos.x, Boids.getWidth());
		pos.y = mod(pos.y, Boids.getHeight());

		this.position.setLocation(pos);
	}
	
	/**
	 * 
	 * @param b un boid
	 * @param type un type de boid
	 * @return Retourne vrai si b est un voisin visible de même type que le boid
	 * @see Boid#isNeighbor(Boid)
	 */
	public boolean isNeighbor(Boid b, Type type) {
		return (b.type == type) && isNeighbor(b);
	}
	
	/**
	 * 
	 * @param b un boid
	 * @return Retourne vrai si b est dans le voisinage du boid
	 */
	public boolean isNear(Boid b) {
		return this != b && position.distance(b.position) < NEIGHBORHOOD;
	}

	/**
	 * 
	 * @param b un boid
	 * @return Retourne vrai si b est un voisin visible du boid
	 */
	public boolean isNeighbor(Boid b) {
		
		// calcul de l'orientation de b par rapport à this
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
		
		// vérification qu'il s'agit d'un voisin dans le champs de vision
		if (this != b && position.distance(b.position) < NEIGHBORHOOD
			 && !(minAngle <= neighborDir && neighborDir <= maxAngle))
			return true;
		
		
		return false;
	}
	
	/**
	 * Première règle de déplacement en essaim. 
	 * Le boid se déplace vers le centre de masse de ces voisins.
	 * @return Retourne le vecteur de déplacement vers le centre de masse
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
	 * Deuxième règle de déplacement en essaim. 
	 * Le boid se replace pour être à une distance minimale de tous les boids de sont voisinage
	 * @return Retourne le vecteur de déplacement pour s'éloigner des bois trop proche
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
	 * Met à jour l'état du boid.
	 */
	public void update() {
		speed.add(acceleration);
		speed.limit(maxspeed);
		computeDirection();

		setPos(position.clone().add(speed));
		acceleration.reset();
	}

	/**
	 * Applique une force sur l'accélération du boid
	 * @param force la force à appliquer
	 * @see PVector
	 */
	public void applyForce(PVector force) {
		force.limit(maxforce);
		acceleration.add(force.div(getMass()));
	}

	/**
	 * Applique les trois règle de déplacement de l'essaim et met à jour l'état du boid.
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
	 * @return Retourne une String qui contient l'état du boid à afficher
	 */
	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + speed.x + ", vy : " 
				+ speed.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y + ")";
	}
}

