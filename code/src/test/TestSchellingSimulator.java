package test;

import group.Schelling;
import simulator.SchellingSimulator;
import gui.GUISimulator;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe de démarrage du test des classes Schelling et SchellingSimulator.
 * (Question 7 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Schelling
 * @see SchellingSimulator
 */
public class TestSchellingSimulator {

	/**
	 * Méthode main de démarrage du test des classes Schelling et 
	 * SchellingSimulator.
	 * @param args les arguments (non utilisés)
	 */
	public static void main(String[] args) {

		int numberOfColor = 7;
		
		/* 
		 * tirage aléatoire de numberOfColor couleurs pour l'affichage
		 * des familles
		 */
		Color color[] = new Color[numberOfColor + 1];
		color[0] = new Color(255, 255, 255);
		for(int i = 1; i < color.length; i++){
			int r = ThreadLocalRandom.current().nextInt(0, 255);
			int g = ThreadLocalRandom.current().nextInt(0, 255);
			int b = ThreadLocalRandom.current().nextInt(0, 255);
			color[i] = new Color(r, g, b);
		}

		int k = 5;
		int n = 50;
		int m = 50;

		int rndMin = 1;
		int rndMax = numberOfColor + 1;
		Schelling sch = new Schelling(n, m, numberOfColor + 1, k);

		int nbMaxHabitant = (4 * n * m)/5;
		/* 
		* tirage aléatoire d'un état (correspondant à une couleur dans Color[])
		* pour nbMaxHabitant
		*/
		int nbHabitant = 0;
		while(nbHabitant < nbMaxHabitant){
			int x = ThreadLocalRandom.current().nextInt(0, n);
			int y = ThreadLocalRandom.current().nextInt(0, m);
			while(sch.getCells()[x][y] != 0){
				x = ThreadLocalRandom.current().nextInt(0, n);
				y = ThreadLocalRandom.current().nextInt(0, m);
			}
			sch.add(x, y, ThreadLocalRandom.current().nextInt(rndMin, rndMax));
			nbHabitant++;
		}

		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new SchellingSimulator(gui, sch, color));
	}

}
