import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;

public class LifeSimulator extends AutomatonSimulator {

	public LifeSimulator(GUISimulator g, Life life) {
		super(g, life);
	}

	protected void updateFrame() {
		int cells[][] = ((Life)game).getCells();

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				switch (cells[x][y]) {
					case Life.ALIVE:
						gui.addGraphicalElement(
							new Rectangle(10+x * 12,10+ y*12,
								Color.BLUE,
								Color.BLUE, 10));
						break;

					case Life.DEAD:
						gui.addGraphicalElement(
							new Rectangle(10+x * 12,10+ y*12,
								Color.GRAY,
								Color.GRAY, 10));
						break;
					default:
						break;
				}
			}
		}
	}
}
