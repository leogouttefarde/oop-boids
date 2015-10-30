import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

public class BallsSimulator implements Simulable{

	private Balls balls;
	private GUISimulator gui;
	private static final int ballSize = 10;
	
	public BallsSimulator(Balls b, GUISimulator g){
		this.balls = b;
		this.gui = g;
		/*rndMax = Math.min(this.gui.getWidth(), this.gui.getHeight());
		this.dx = ThreadLocalRandom.current().nextInt(rndMin, rndMax + 1) % vitesseMax;
		this.dy = ThreadLocalRandom.current().nextInt(rndMin, rndMax + 1) % vitesseMax;
		System.out.println(this.dx + " " + this.dy);*/
	}
	
	private void updateFrame(){
		Iterator<Point> it = this.balls.iterator();
		while(it.hasNext()){
			Point p = it.next();
			gui.addGraphicalElement(new Oval((int)p.getX(), (int)p.getY(), Color.decode("#1f77b4"), Color.decode("#1f77b4"), ballSize));
		}
	}
	
	@Override 
	public void next(){
		//balls.translate(this.dx, this.dy);
		balls.translateBoundarie(this.gui.getPanelWidth() - ballSize, this.gui.getPanelHeight() - ballSize, ballSize);
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
