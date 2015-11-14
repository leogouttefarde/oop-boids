import java.awt.Color;
import gui.GUISimulator;

public class SchellingSimulator extends AutomatonSimulator {

	private Color colors[];

	public SchellingSimulator(GUISimulator gui, Schelling sch, Color colors[]) {
		super(gui, sch);
		this.colors = colors;
	}

	protected Color getCellColor(int x, int y) {
		return colors[ cells[x][y] ];
	}
}

