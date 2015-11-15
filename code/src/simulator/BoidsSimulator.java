package simulator;

import element.Boid;
import group.Boids;
import event.EventManager;
import event.BoidsEvent;
import event.LighterEvent;
import utility.Triangle;

import java.util.Iterator;
import java.awt.Color;

import gui.GraphicalElement;
import gui.GUISimulator;
import gui.Simulable;
import gui.Oval;

/**
 * Simulateur de Boids
 * 
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 */
public class BoidsSimulator implements Simulable {

	private GUISimulator gui;
	private Boids boids;

	/**
	 * Crée un simulateur de Boids
	 * 
	 * @param gui		Simulateur de l'interface graphique liée
	 * @param boids		Boids à simuler
	 */
	public BoidsSimulator(GUISimulator gui, Boids boids) {
		this.gui = gui;
		this.boids = boids;

		Boids.setWidth(gui.getPanelWidth());
		Boids.setHeight(gui.getPanelHeight());

		EventManager.Get().addEvent(new BoidsEvent(1, boids));
		EventManager.Get().addEvent(new LighterEvent(7));
	}

	/**
	 * Met à jour l'affichage
	 */
	private void updateFrame() {
		final Color transp = new Color(0,0,0,0);
		Iterator<Boid> it = boids.iterator();
		GraphicalElement elem;
		Boid b;

		while(it.hasNext()) {
			b = it.next();
			b.computeTriangle();

			switch (b.getType()) {
				case Prey:
				case Predator:
				default:
					elem = new Triangle(b.getTriangleX(), b.getTriangleY(),
										b.getColor(), b.getColor());
					break;

				case Lighter:
					elem = new Oval((int)b.getPosition().getX(),
									(int)b.getPosition().getY(),
									transp, b.getColor(), b.getSize());
			}

			gui.addGraphicalElement(elem);
		}
	}

	/**
	 * Avance la simulation d'un pas
	 */
	@Override
	public void next() {
		EventManager.Get().next();
		gui.reset();
		updateFrame();
	}

	/**
	 * Relance la simulation
	 */
	@Override
	public void restart() {
		boids.reset();
		gui.reset();
		updateFrame();
	}
}
