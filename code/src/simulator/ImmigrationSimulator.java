package simulator;

import group.Immigration;

import java.awt.Color;
import gui.GUISimulator;

public class ImmigrationSimulator extends AutomatonSimulator {

	public ImmigrationSimulator(GUISimulator g, Immigration imm) {
		super(g, imm);
	}

	protected Color getCellColor(int x, int y) {
		final int k = 255 - 70 * cells[x][y];

		return new Color(k, k, k);
	}
}
