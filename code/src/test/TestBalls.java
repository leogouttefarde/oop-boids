package test;

import group.Balls;
import java.awt.Point;

/**
 * Classe de démarrage du test de la classe Balls. (Question 1 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Balls
 */
public class TestBalls {

	/**
	 * Méthode main de démarrage du test de la classe Balls.
	 * @param args les arguments (non utilisés)
	 */
	public static void main(String[] args) {
		Balls b = new Balls();
		
		b.addBall(new Point(10, 20));
		b.addBall(new Point(15, 25));
		b.addBall(new Point(28, 43));
		b.addBall(new Point(52, 68));
		
		System.out.println(b.toString());
		
		b.translate(5, 10);
		System.out.println(b.toString());
		
		b.reInit();
		System.out.println(b.toString());
		
	}

}
