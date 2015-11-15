package test;

import group.Life;
import simulator.LifeSimulator;
import gui.GUISimulator;
import java.awt.Color;

/**
 * Classe de démarrage du test des classes Life et LifeSimulator. 
 * (Question 5 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Life
 * @see LifeSimulator
 */
public class TestLifeSimulator {
	
	/**
	 * Méthode pour initialiser une zone du plateau de jeu 
	 * pour le test de la figure 5 du sujet
	 *
	 * @param life Plateau de jeu
	 * @param x Abscisse
	 * @param y Ordonnée
	 */
	private static void basicTest(Life life, int x, int y) {
		life.add(x, y);
		life.add(x + 1, y);
		life.add(x, y + 1);
		life.add(x + 2, y + 1);
		life.add(x + 4, y + 4);
	}
	
	/**
	 * Méthode pour initialiser une zone du plateau de jeu 
	 * pour le test blinker de la page wikipédia :
	 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
	 *
	 * @param life Plateau de jeu
	 * @param x Abscisse
	 * @param y Ordonnée
	 */
	private static void blinker(Life life, int x, int y) {
		life.add(x, y);
		life.add(x, y + 1);
		life.add(x, y - 1);
	}
	
	/**
	 * Méthode pour initialiser une zone du plateau de jeu 
	 * pour le test pulsar de la page wikipédia :
	 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
	 *
	 * @param life Plateau de jeu
	 * @param x Abscisse
	 * @param y Ordonnée
	 */
	private static void pulsar(Life life, int x, int y) {
		life.add(x, y - 1);
		life.add(x, y - 2);
		life.add(x, y - 3);
		
		life.add(x - 1, y);
		life.add(x - 2, y);
		life.add(x - 3, y);
		
		life.add(x - 1, y - 5);
		life.add(x - 2, y - 5);
		life.add(x - 3, y - 5);
		
		life.add(x - 5, y - 1);
		life.add(x - 5, y - 2);
		life.add(x - 5, y - 3);
		
		life.add(x + 2, y - 1);
		life.add(x + 2, y - 2);
		life.add(x + 2, y - 3);
		
		life.add(x + 3, y);
		life.add(x + 4, y);
		life.add(x + 5, y);
		
		life.add(x + 3, y - 5);
		life.add(x + 4, y - 5);
		life.add(x + 5, y - 5);
		
		life.add(x + 7, y - 1);
		life.add(x + 7, y - 2);
		life.add(x + 7, y - 3);
		
		life.add(x, y + 3);
		life.add(x, y + 4);
		life.add(x, y + 5);
		
		life.add(x - 1, y + 2);
		life.add(x - 2, y + 2);
		life.add(x - 3, y + 2);
		
		life.add(x - 1, y + 7);
		life.add(x - 2, y + 7);
		life.add(x - 3, y + 7);
		
		life.add(x - 5, y + 3);
		life.add(x - 5, y + 4);
		life.add(x - 5, y + 5);
		
		life.add(x + 2, y + 3);
		life.add(x + 2, y + 4);
		life.add(x + 2, y + 5);
		
		life.add(x + 3, y + 2);
		life.add(x + 4, y + 2);
		life.add(x + 5, y + 2);
		
		life.add(x + 3, y + 7);
		life.add(x + 4, y + 7);
		life.add(x + 5, y + 7);
		
		life.add(x + 7, y + 3);
		life.add(x + 7, y + 4);
		life.add(x + 7, y + 5);
		
		
	}
	
	/**
	 * Méthode pour initialiser une zone du plateau de jeu 
	 * pour le test toad de la page wikipédia :
	 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
	 *
	 * @param life Plateau de jeu
	 * @param x Abscisse
	 * @param y Ordonnée
	 */
	private static void toad(Life life, int x, int y) {
		life.add(x - 1, y);
		life.add(x - 2, y);
		life.add(x - 3, y);
		
		life.add(x, y - 1);
		life.add(x - 1, y - 1);
		life.add(x - 2, y - 1);
		
	}

	/**
	 * Méthode main de démarrage du test des classes Life et LifeSimulator.
	 * @param args Arguments (non utilisés)
	 */
	public static void main(String[] args) {

		Life life = new Life(70, 40);
		basicTest(life, 7, 7);
		blinker(life, 5, 20);
		toad(life, 5, 30);
		pulsar(life, 35, 20);
		System.out.println(life);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new LifeSimulator(gui, life));
	}
}
