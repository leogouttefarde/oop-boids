package utility;

import java.awt.geom.Point2D;

/**
 * Classe de vecteur munie de méthodes pour leur manipulation
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class PVector extends Point2D.Double {

	/**
	 * Crée un PVector (0, 0)
	 */
	public PVector() {
		super();
	}

	/**
	 * Crée un PVector (x, y)
	 * @param x 	Abscisse
	 * @param y 	Ordonnée
	 */
	public PVector(double x, double y) {
		super(x, y);
	}


	/**
	 * @return Retourne une copie du vecteur
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
	 * 
	 * @param p 	Un vecteur
	 * 
	 * @return Somme du vecteur this et du vecteur p
	 */
	public PVector add(PVector p) {
		x += p.x;
		y += p.y;

		return this;
	}

	/**
	 * Calcul la difference du vecteur this et du vecteur p.
	 * 
	 * @param p		Un vecteur
	 * 
	 * @return Difference du vecteur this et du vecteur p
	 */
	public PVector sub(PVector p) {
		x -= p.x;
		y -= p.y;

		return this;
	}

	/**
	 * Calcule la norme du vecteur
	 * 
	 * @return Norme
	 */
	public double norm() {
		double n = Math.sqrt(x*x + y*y);

		return n;
	}

	/**
	 * Calcule une valeur limite
	 * 
	 * @param value Valeur
	 * @param lim	Limite
	 * 
	 * @return Valeur tronquée
	 */
	public static double getLimit(double value, double lim) {

		if (value > lim)
			value = lim;

		else if (value < -lim)
			value = -lim;

		return value;
	}

	/**
	 * Limite le vecteur
	 * 
	 * @param lim	Limite
	 * 
	 * @return	Vecteur limité
	 * @see PVector#getLimit(double, double)
	 */
	public PVector limit(double lim) {
		x = getLimit(x, lim);
		y = getLimit(y, lim);

		return this;
	}

	/**
	 * Calcule le produit du vecteur this par v.
	 * 
	 * @param v Valeur
	 * 
	 * @return Vecteur produit
	 */
	public PVector mult(double v) {
		x *= v;
		y *= v;

		return this;
	}

	/**
	 * Calcule le produit du vecteur this et du vecteur p.
	 * @param p Un vecteur
	 * @return Vecteur produit
	 */
	public PVector mult(PVector p) {
		x *= p.x;
		y *= p.y;

		return this;
	}

	/**
	 * Divise le vecteur this par v.
	 * @param v Une valeur
	 * @return Résultat
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
