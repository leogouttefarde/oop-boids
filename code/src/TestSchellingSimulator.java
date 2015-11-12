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
		// color[1] = new Color(255, 0, 0);
		// color[2] = new Color(0, 255, 0);
		// color[3] = new Color(0, 0, 255);
		// color[4] = new Color(0, 255, 255);
		// color[5] = new Color(255, 0, 255);
		// color[6] = new Color(255, 255, 0);

		int threshold = 5;
		int n = 50;
		int m = 50;

		int rndMin = 1;
		int rndMax = numberOfColor + 1;
		Schelling sch = new Schelling(n, m, numberOfColor + 1, threshold);

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
		// sch.add(7, 9, 2);
		// sch.add(8, 9, 2);
		// sch.add(9, 9, 2);
		// sch.add(10, 9, 2);
		// sch.add(7, 8, 4);
		// sch.add(8, 8, 4);
		// sch.add(9, 8, 4);
		// sch.add(10, 8, 4);
		// sch.add(1, 1, 2);
		// sch.add(1, 2, 1);
		// sch.add(1, 3, 1);
		// sch.add(2, 2, 3);
		// sch.add(2, 3, 3);
		// sch.add(3, 2, 2);
		// sch.add(3, 3, 2);
		// sch.add(3, 4, 2);
		// sch.add(3, 5, 2);
		// sch.add(4, 2, 3);
		// sch.add(4, 3, 3);
		// sch.add(4, 4, 3);
		// sch.add(4, 5, 3);
		// sch.add(4, 6, 3);
		// sch.add(4, 7, 3);

		sch.initVacantHousing();
		
		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new SchellingSimulator(gui, sch, color));
	}

}
