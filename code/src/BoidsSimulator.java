import java.awt.Color;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

	private GUISimulator gui;
	private Boids boids;

	public BoidsSimulator(GUISimulator gui, Boids boids) {
		this.gui = gui;
		this.boids = boids;
	}

	private void updateFrame() {
		Iterator<Boid> it = this.boids.iterator();
		while(it.hasNext()){
			Boid b = it.next();

			// Subtract 1 from ballSize because Oval adds 1 pixel to radius
			gui.addGraphicalElement(new Oval((int)b.position.getX(), (int)b.position.getY(),
					Color.decode("#1f77b4"), Color.decode("#1f77b4"), 10));
		}
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
