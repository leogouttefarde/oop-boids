import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;

public class ImmigrationSimulator extends AutomatonSimulator {

	public ImmigrationSimulator(GUISimulator g, Immigration imm) {
		super(g, imm);
	}

	protected void updateFrame() {
		int cells[][] = ((Immigration)automaton).getCells();

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				int k = 255 - 70 * cells[x][y];
				Color c = new Color(k, k, k);
				gui.addGraphicalElement(new Rectangle(10+x * 12,10+ y*12, c, c, 10));
			}
		}
	}
}
