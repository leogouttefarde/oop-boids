import gui.GUISimulator;
import java.awt.Color;
import java.awt.Point;

public class TestImmigrationSimulator {

	public static void main(String[] args) {

		Immigration imm = new Immigration(70, 40, 4);

		imm.addCell(1, 1, 0);
		imm.addCell(1, 2, 0);
		imm.addCell(1, 3, 0);
		imm.addCell(7, 7, 1);
		imm.addCell(8, 7, 1);
		imm.addCell(9, 7, 1);
		imm.addCell(7, 10, 2);
		imm.addCell(7, 11, 2);
		imm.addCell(8, 11, 2);
		imm.addCell(7, 12, 2);
		imm.addCell(7, 8, 3);
		imm.addCell(7, 9, 3);

		imm.addCell(19, 30, 3);
		imm.addCell(20, 30, 3);
		imm.addCell(20, 31, 3);
		imm.addCell(20, 30, 3);
		imm.addCell(31, 22, 3);
		imm.addCell(21, 31, 3);
		System.out.println(imm);


		GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

		gui.setSimulable(new ImmigrationSimulator(gui, imm));
	}
}
