import gui.GUISimulator;
import java.awt.Color;
import java.awt.Point;

public class TestImmigrationSimulator {

	public static void main(String[] args) {

		Immigration imm = new Immigration(70, 40);

		// imm.addCell(7, 7);
		System.out.println(imm);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new ImmigrationSimulator(gui, imm));
	}
}
