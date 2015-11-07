import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

public class BallsSimulator implements Simulable {

	private Balls balls;
	private GUISimulator gui;
	private static final int ballSize = 7; // Ball radius
	private static final int GUI_BORDERS = 3; // GUI panel borders

	public BallsSimulator(Balls b, GUISimulator g){
		this.balls = b;
		this.gui = g;
	}

	private void updateFrame(){
		Iterator<Point> it = this.balls.iterator();
		while(it.hasNext()){
			Point p = it.next();

			// Subtract 1 from ballSize because Oval adds 1 pixel to radius
			gui.addGraphicalElement(new Oval((int)p.getX(), (int)p.getY(),
					Color.decode("#1f77b4"), Color.decode("#1f77b4"), 2*(ballSize-1)));
		}
	}

	@Override 
	public void next(){
		// Add 1 to ballSize because Oval adds 1 pixel in the middle
		balls.translateBoundarie(this.gui.getPanelWidth() - GUI_BORDERS - ballSize-1,
				this.gui.getPanelHeight() - GUI_BORDERS - ballSize-1, ballSize);
		System.out.println(balls.toString());
		this.gui.reset();
		updateFrame();
	}

	@Override
	public void restart(){
		balls.reInit();
		System.out.println(balls.toString());
		this.gui.reset();
		updateFrame();
	}
}
