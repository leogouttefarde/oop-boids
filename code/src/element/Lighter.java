package element;

import utility.Type;
import utility.PVector;

import java.awt.Color;
import java.util.Random;

/**
 * Classe qui hérite de Boid pour implémenter un nouveau type de Boid : le type Lighter.
 * Le Lighter est un Boid sphérique qui brille.
 *
 * @see Boid
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class Lighter extends Boid {

	private static final int SIZE = 40;
	private static final int MAX_SPEED = 10;
	protected static final int MIN_DIST = 44;

	protected static final int MIN_LIGHT = 0;
	protected static final int MAX_LIGHT = 170;

	
	/**
	 * Crée un boid de type Lighter
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
	public Lighter(double x, double y, double sx, double sy, double ax, double ay) {
		super(x, y, sx, sy, ax, ay, Type.Lighter, Color.WHITE, SIZE);
		maxspeed = MAX_SPEED;
		randLight();
	}

	@Override
	public void reset(PVector position, PVector speed, PVector acceleration) {
		super.reset(position, speed, acceleration);
		randLight();
	}

	/**
	 * Calcule un taux de brillance aléatoire.
	 *
	 * @see Lighter#setLight(int)
	 */
	protected void randLight() {
		Random rand = new Random();
		setLight(rand.nextInt(MAX_LIGHT - MIN_LIGHT) + MIN_LIGHT);
	}

	/**
	 * Calcule la couleur d'affichage
	 * 
	 * @param light Taux de brillance
	 */
	protected void setLight(int light) {
		color = new Color(light, 255, 255, 255);
	}

	@Override
	public int GetMinDist() {
		return Lighter.MIN_DIST;
	}

	@Override
	public void update() {
		super.update();
		randLight();
	}
}

