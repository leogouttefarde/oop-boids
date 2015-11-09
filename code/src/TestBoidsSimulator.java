import gui.GUISimulator;
import java.awt.Color;

public class TestBoidsSimulator {

	public static void main(String[] args) {

		Boids boids = new Boids();

		boids.add(7, 8);
		boids.add(8, 9);
		boids.add(20, 20);
		boids.add(200, 200);

		System.out.println(boids);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new BoidsSimulator(gui, boids));
	}
}
