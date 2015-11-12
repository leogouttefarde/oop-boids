import gui.GUISimulator;
import java.awt.Color;

public class TestConwaySimulator {

	public static void main(String[] args) {

		Conway life = new Conway(70, 40);

		life.addCell(7, 7, Conway.alive);
		life.addCell(8, 7, Conway.alive);
		life.addCell(7, 8, Conway.alive);
		life.addCell(9, 8, Conway.alive);
		life.addCell(11, 11, Conway.alive);
		System.out.println(life);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new ConwaySimulator(gui, life));
	}
}
