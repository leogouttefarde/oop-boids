import java.util.Iterator;
import java.awt.Color;

import gui.GUISimulator;
import gui.Simulable;
import gui.Oval;

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
		Color transp = new Color(0,0,0,0);

		while(it.hasNext()) {
			b = it.next();
			b.computeTriangle();

			switch (b.getType()) {
				case Prey:
				case Predator:
					gui.addGraphicalElement(new Triangle(b.getTriangleX(),
						b.getTriangleY(), b.getColor(), b.getColor()));
					break;

				case Lighter:
					gui.addGraphicalElement(new Oval((int)b.position.x, (int)b.position.y,
						transp, b.getColor(), b.size));

				default:
			}
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
