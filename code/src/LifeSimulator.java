import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class LifeSimulator implements Simulable {

	private GUISimulator gui;
	private Life life;

	public LifeSimulator(GUISimulator g, Life life) {
		this.gui = g;
		this.life = life;
	}

	private void updateFrame() {
		Life.State cells[][] = life.getCells();

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				switch (cells[x][y]) {
					case ALIVE:
						gui.addGraphicalElement(
							new Rectangle(10+x * 12,10+ y*12,
								Color.BLUE,
								Color.BLUE, 10));
						break;

					case DEAD:
						gui.addGraphicalElement(
							new Rectangle(10+x * 12,10+ y*12,
								Color.GRAY,
								Color.GRAY, 10));
						break;

					default:
						System.out.println("wtf");
				}
			}
		}
	}

	@Override 
	public void next() {
		life.generate();
		System.out.println(life);
		gui.reset();
		updateFrame();
	}

	@Override
	public void restart() {
		life.reset();
		gui.reset();
		updateFrame();
	}
}
