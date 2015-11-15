package test;

import element.Prey;
import element.Lighter;
import group.Boids;
import simulator.BoidsSimulator;

import gui.GUISimulator;
import java.awt.Color;

/**
 * Classe de démarrage du test des classes BoidsSimulator, 
 * Boid, Boids, Prey et Lighter. (Question 8, 10, 11 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Prey
 * @see Lighter
 * @see Boids
 * @see BoidsSimulator
 *
 */
public class TestBoidsSimulator {

	/**
	 * Méthode main de démarrage du test des classes
	 * Boid, Boids, Prey, Lighter et BoidsSimulator.
	 * @param args les arguments (non utilisés)
	 */
	public static void main(String[] args) {

		Boids boids = new Boids();
		
		boids.add(new Prey(7, 8, 5, 5, 1, 1));
		boids.add(new Prey(8, 9, 5, 5, -7, 1));
		boids.add(new Prey(20, 20, 5, 5, -8, -9));
		boids.add(new Prey(200, 200, 5, 5, -1, -1));
		boids.add(new Prey(400, 400, 7, 9, 3, -2));
		boids.add(new Prey(450, 470, 7, 9, -3, -2));
		boids.add(new Prey(350, 470, 1, 9, 3, 2));
		boids.add(new Prey(350, 320, 7, 9, -3, 2));
		boids.add(new Prey(450, 50, 7, 9, -3, 2));
		boids.add(new Prey(450, 50, 7, 9, -3, 2));
		boids.add(new Prey(470, 150, 7, 9, -3, 2));
		boids.add(new Prey(480, 100, 7, 9, 3, -2));
		boids.add(new Prey(100, 480, 7, 9, -3, -2));
		boids.add(new Prey(100, 450, 7, 9, 3, -2));
		boids.add(new Prey(110, 430, 7, 9, -3, 2));
		boids.add(new Prey(250, 250, 0, -5, 0, 0));
		boids.add(new Lighter(55, 77, 0, -5, 0, 0));
		boids.add(new Lighter(255, 277, 11, -27, 12, 20));
		boids.add(new Lighter(255, 277, 11, -27, 12, 20));
		boids.add(new Lighter(250, 277, 11, -27, 12, 20));
		boids.add(new Lighter(255, 250, 11, -27, 12, 20));

		System.out.println(boids);


		GUISimulator gui = new GUISimulator(850, 500, Color.BLACK);

		gui.setSimulable(new BoidsSimulator(gui, boids));
	}
}
