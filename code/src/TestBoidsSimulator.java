import gui.GUISimulator;
import java.awt.Color;

public class TestBoidsSimulator {

	public static void main(String[] args) {

		Boids boids = new Boids();
		
		boids.add(7, 8, 2, 2, 1, 1);
		boids.add(8, 9, 2, 2, -7, 1);
		boids.add(20, 20, 2, 2, -8, -9);
		boids.add(200, 200, 2, 2, -1, -1);
		boids.add(400, 400, 7, 9, 3, -2);
		boids.add(450, 470, 7, 9, -3, -2);
		boids.add(350, 470, 1, 9, 3, 2);
		boids.add(350, 320, 7, 9, -3, 2);
		boids.add(450, 50, 7, 9, -3, 2);
		boids.add(450, 50, 7, 9, -3, 2);
		boids.add(470, 150, 7, 9, -3, 2);
		boids.add(480, 100, 7, 9, 3, -2);
		boids.add(100, 480, 7, 9, -3, -2);
		boids.add(100, 450, 7, 9, 3, -2);
		boids.add(110, 430, 7, 9, -3, 2);
		boids.add(250, 250, 0, -3, 0, 0);

		System.out.println(boids);


		GUISimulator gui = new GUISimulator(850, 500, Color.BLACK);

		gui.setSimulable(new BoidsSimulator(gui, boids));
	}
}
