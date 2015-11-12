import java.awt.Color;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

	private GUISimulator gui;
	private Boids boids;
	private static int size = 7;

	public BoidsSimulator(GUISimulator gui, Boids boids) {
		this.gui = gui;
		this.boids = boids;

		Boids.setWidth(gui.getPanelWidth());
		Boids.setHeight(gui.getPanelHeight());

		EventManager.Get().addEvent(new BoidsEvent(1, boids));
	}

	private void updateFrame() {
		Iterator<Boid> it = this.boids.iterator();
		while(it.hasNext()){
			Boid b = it.next();
			b.computeTrianglePoints(size);
			gui.addGraphicalElement(new Triangle(b.getTriangleXPoints(), b.getTriangleYPoints(), Color.decode("#1f77b4"), Color.decode("#1f77b4")));
		}
	}

	@Override 
	public void next() {
		EventManager.Get().next();
		//System.out.println(boids.toString());
		gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		boids.reset();
		// System.out.println(boids.toString());
		gui.reset();
		updateFrame();
	}
}
