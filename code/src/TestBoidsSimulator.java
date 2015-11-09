import gui.GUISimulator;
import java.awt.Color;

public class TestBoidsSimulator {

	public static void main(String[] args) {

		Boids boids = new Boids();

		boids.add(7, 8, 2, 2, 1, 1);
		boids.add(8, 9, 2, 2, 1, 1);
		boids.add(20, 20, 2, 2, 1, 1);
		boids.add(200, 200, 2, 2, 1, 1);

		System.out.println(boids);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new BoidsSimulator(gui, boids));
	}
}
