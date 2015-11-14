import java.util.Iterator;

import gui.GUISimulator;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

	private GUISimulator gui;
	private Boids boids;

	public BoidsSimulator(GUISimulator gui, Boids boids) {
		this.gui = gui;
		this.boids = boids;

		Boids.setWidth(gui.getPanelWidth());
		Boids.setHeight(gui.getPanelHeight());

		EventManager.Get().addEvent(new BoidsEvent(1, boids));
	}

	private void updateFrame() {
		Iterator<Boid> it = boids.iterator();
		Boid b;

		while(it.hasNext()) {
			b = it.next();
			b.computeTriangle();
			gui.addGraphicalElement(new Triangle(b.getTriangleX(), b.getTriangleY(), b.getColor(), b.getColor()));
		}
	}

	@Override 
	public void next() {
		EventManager.Get().next();
		gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		boids.reset();
		gui.reset();
		updateFrame();
	}
}
