import java.awt.Color;
import java.awt.Point;

import gui.GUISimulator;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

	private GUISimulator gui;
	private Boids boids;

	public BoidsSimulator(GUISimulator gui, Boids boids) {
		this.gui = gui;
		this.boids = boids;
	}

	private void updateFrame() {
		
	}

	@Override 
	public void next() {
		boids.update();
		System.out.println(boids.toString());
		this.gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		boids.reset();
		System.out.println(boids.toString());
		this.gui.reset();
		updateFrame();
	}
}
