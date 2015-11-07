import gui.GUISimulator;
import java.awt.Color;

public class TestLifeSimulator {

	public static void main(String[] args) {

		Life life = new Life(70, 40);

		life.addCell(7, 7);
		life.addCell(8, 7);
		life.addCell(7, 8);
		life.addCell(9, 8);
		life.addCell(11, 11);
		System.out.println(life);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new LifeSimulator(gui, life));
	}
}
