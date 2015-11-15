package test;

import group.Balls;
import simulator.BallsSimulator;
import gui.GUISimulator;
import java.awt.Color;
import java.awt.Point;

/**
 * Classe de démarrage du test des classes Balls et BallsSimulator. 
 * (Question 2, 3, 4 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Balls
 * @see BallsSimulator
 *
 */
public class TestBallsSimulator {

	/**
	 * Méthode main de démarrage du test des classes Balls et BallsSimulator.
	 * @param args Arguments (non utilisés)
	 */
	public static void main(String[] args) {
		Balls b = new Balls();
		
		b.addBall(new Point(20, 100));
		b.addBall(new Point(100, 250));
		b.addBall(new Point(200, 100));
		
		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new BallsSimulator(gui, b));
	}
}
