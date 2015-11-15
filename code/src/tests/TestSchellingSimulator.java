package tests;

import groups.Schelling;
import simulators.SchellingSimulator;
import gui.GUISimulator;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class TestSchellingSimulator {

	public static void main(String[] args) {

		int numberOfColor = 7;
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