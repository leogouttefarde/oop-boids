package simulator;

import group.Balls;
import event.EventManager;
import event.BallsEvent;
import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

public class BallsSimulator implements Simulable {

	private Balls balls;
	private GUISimulator gui;
	public static final int BALLSIZE = 7; // Rayon des ball
	private static final int GUI_BORDERS = 3; // bordure du panel GUI

	public BallsSimulator(GUISimulator gui, Balls balls) {
		this.gui = gui;
		this.balls = balls;

		// Add 1 to BALLSIZE because Oval adds 1 pixel in the middle
		balls.setWidth(gui.getPanelWidth() - GUI_BORDERS - BALLSIZE-1);
		balls.setHeight(gui.getPanelHeight() - GUI_BORDERS - BALLSIZE-1);

		EventManager.Get().addEvent(new BallsEvent(1, balls));
	}

	private void updateFrame() {
		Iterator<Point> it = balls.iterator();

		while(it.hasNext()) {
			Point p = it.next();

			// Subtract 1 from BALLSIZE because Oval adds 1 pixel to radius
			gui.addGraphicalElement(new Oval((int)p.getX(), (int)p.getY(),
							Color.decode("#1f77b4"), Color.decode("#1f77b4"),
							2 * (BALLSIZE-1)));
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
		balls.reInit();
		gui.reset();
		updateFrame();
	}
}
