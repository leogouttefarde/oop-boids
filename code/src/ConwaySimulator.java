import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public abstract class ConwaySimulator implements Simulable {

	protected GUISimulator gui;
	protected Conway conway;

	public ConwaySimulator(GUISimulator gui, Conway conway) {
		this.gui = gui;
		this.conway = conway;
	}

	protected abstract void updateFrame();

	@Override 
	public void next() {
		conway.generate();
		System.out.println(conway);
		gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		conway.reset();
		gui.reset();
		updateFrame();
	}
}
