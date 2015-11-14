import java.awt.Color;
import gui.GUISimulator;

public class LifeSimulator extends AutomatonSimulator {

	public LifeSimulator(GUISimulator g, Life life) {
		super(g, life);
	}

	protected Color getCellColor(int x, int y) {
		Color color;

		switch (cells[x][y]) {
			case Life.ALIVE:
				color = Color.BLUE;
				break;

			case Life.DEAD:
			default:
				color = Color.GRAY;
				break;
		}

		return color;
	}
}
