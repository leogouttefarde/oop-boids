import gui.GUISimulator;
import java.awt.Color;
import java.awt.Point;

public class TestBallsSimulator {

	public static void main(String[] args) {
		Balls b = new Balls();
		
		b.addBall(new Point(20, 100));
		b.addBall(new Point(100, 250));
		b.addBall(new Point(200, 100));
		
		GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
		gui.setSimulable(new BallsSimulator(b, gui));
	}
}
