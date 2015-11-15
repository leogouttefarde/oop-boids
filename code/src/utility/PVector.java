package utility;

import java.awt.geom.Point2D;

/**
 * Classe de vecteur avec des méthodes pour leurs manipulation
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 *
 */
public class PVector extends Point2D.Double {

	/**
	 * Crée un PVector = (0, 0)
	 */
	public PVector() {
		super();
	}

	/**
	 * Crée un PVector = (x, y)
	 * @param x la coordonnée des abscisses du vecteur
	 * @param y la coordonnée des ordonnées du vecteur
	 */
	public PVector(double x, double y) {
		super(x, y);
	}


	/**
	 * @return Retourne un copie du vecteur
	 */
	public PVector clone() {
		return (PVector)super.clone();
	}

	/**
	 * @return Retourne vrai si le vecteur vaut (0, 0)
	 */
	public boolean isNull() {
		return (x == 0 && y == 0);
	}

	/**
	 * Calcul la somme du vecteur this et du vecteur p.
	 * @param p un vecteur
	 * @return Retourne la somme du vecteur this et du vecteur p
	 */
	public PVector add(PVector p) {
		x += p.x;
		y += p.y;

		return this;
	}

	/**
	 * Calcul la difference du vecteur this et du vecteur p.
	 * @param p un vecteur
	 * @return Retourne la difference du vecteur this et du vecteur p
	 */
	public PVector sub(PVector p) {
		x -= p.x;
		y -= p.y;

		return this;
	}

	/**
	 * 
	 * @return Retourne la norme du vecteur
	 */
	public double norm() {
		double n = Math.sqrt(x*x + y*y);

		return n;
	}

	/**
	 * 
	 * @param value une valeur
	 * @param lim une limite
	 * @return Retourne une valeur appartenant à l'intervalle [-lim, lim], 
	 * si value est hors de cette intervalle la valeur de retour est lim ou -lim selon les cas.
	 */
	public double getLimit(double value, double lim) {

		if (value > lim)
			value = lim;

		else if (value < -lim)
			value = -lim;

		return value;
	}

	/**
	 * @param lim un limite
	 * @return Retourne un vecteur appartenant à l'intervalle [-lim, lim]x[-lim, lim].
	 * @see PVector#getLimit(double, double)
	 */
	public PVector limit(double lim) {
		x = getLimit(x, lim);
		y = getLimit(y, lim);

		return this;
	}

	/**
	 * Calcul le produit du vecteur this par v.
	 * @param v une valeur
	 * @return Retourne le vecteur multiplié par v
	 */
	public PVector mult(double v) {
		x *= v;
		y *= v;

		return this;
	}

	/**
	 * Calcul le produit du vecteur this et du vecteur p.
	 * @param p un vecteur
	 * @return Retourne la difference du vecteur this et du vecteur p
	 */
	public PVector mult(PVector p) {
		x *= p.x;
		y *= p.y;

		return this;
	}

	/**
	 * Calcul le quotient du vecteur this par v.
	 * @param v une valeur
	 * @return Retourne le vecteur divisé par v
	 */
	public PVector div(double v) {
		x /= v;
		y /= v;

		return this;
	}
	
	/**
	 * Met le vecteur à (0, 0)
	 */
	public void reset() {
		x = y = 0;
	}
}
