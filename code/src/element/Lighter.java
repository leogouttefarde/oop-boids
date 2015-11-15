package element;

import utility.Type;
import utility.PVector;

import java.awt.Color;
import java.util.Random;

/**
 * Classe qui hérite de boid pour implémenter un nouveau type de boid : le type Lighter.
 * Le boid Lighter est un boid sphèrique qui clignote. 
 * Le boid Lighter est considéré comme une proie pour Predator
 * @see Boid
 * @see Predator
 * @author ilyes
 *
 */
public class Lighter extends Boid {

	private static final int SIZE = 40;
	private static final int MAX_SPEED = 10;
	protected static final int MIN_DIST = 44;

	protected static final int STEP = 40;
	protected static final int MIN_LIGHT = 0;
	protected static final int MAX_LIGHT = 170;

	private int light;
	private int diff;

	/**
	 * Crée un boid de type Lighter
	 * @param x la coordonnée x de position
	 * @param y la coordonnée y de position
	 * @param sx la coordonnée x de speed
	 * @param sy la coordonnée y de speed
	 * @param ax la coordonnée x de acceleration
	 * @param ay la coordonnée y de acceleration
	 * @see Type
	 * @see Boid#Boid(double, double, double, double, double, double, Type, Color, int)
	 */
	public Lighter(double x, double y, double sx, double sy, double ax, double ay) {
		super(x, y, sx, sy, ax, ay, Type.Lighter, Color.WHITE, SIZE);
		maxspeed = MAX_SPEED;

		diff = STEP;
		randLight();
	}

	/**
	 * Initialise la position, la vitesse, l'accélération et la direction et le scintillement du boid Lighter
	 * @param position la nouvelle position
	 * @param speed la nouvelle vitesse
	 * @param acceleration la nouvelle accélération
	 * @see Lighter#randLight()
	 */
	@Override
	public void reset(PVector position, PVector speed, PVector acceleration) {
		super.reset(position, speed, acceleration);
		randLight();
	}

	/**
	 * Calcul le taux d'opacité alpha pour le codage ARGB de la couleur du boid.
	 * Permet d'obtenir l'effet de scintillement.
	 * @see Lighter#setLight(int)
	 */
	protected void randLight() {
		Random rand = new Random();
		setLight(rand.nextInt(MAX_LIGHT - MIN_LIGHT) + MIN_LIGHT);
	}

	/**
	 * Calcul de la couleur d'affichage du boid
	 * @param light le taux d'opacite alpha du codage ARGB
	 */
	protected void setLight(int light) {
		this.light = light;
		color = new Color(light, 255, 255, 255);
	}

	/**
	 * @return Retourne la distance minimale entre deux boids voisin
	 * @see Boid#ruleKeepDistance()
	 */
	@Override
	public int GetMinDist() {
		return Lighter.MIN_DIST;
	}
	
	/**
	 * Met à jour l'état du boid Lighter.
	 */
	@Override
	public void update() {
		super.update();

		light -= diff;

		if (light <= MIN_LIGHT) {
			light = MIN_LIGHT;
			diff *= -1;
		}
		else if (light >= MAX_LIGHT) {
			light = MAX_LIGHT;
			diff *= -1;
		}

		setLight(light);
	}
}

