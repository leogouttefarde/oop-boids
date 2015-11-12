import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;

public class ConwaySimulator extends CellularAutomatonSimulator {

	public ConwaySimulator(GUISimulator g, Conway life) {
		super(g, life);
	}

	protected void updateFrame() {
		int cells[][] = ((Conway)game).getCells();

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				switch (cells[x][y]) {
					case Conway.alive:
						gui.addGraphicalElement(
							new Rectangle(10+x * 12,10+ y*12,
								Color.BLUE,
								Color.BLUE, 10));
						break;

					case Conway.dead:
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
