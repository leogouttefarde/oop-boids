import java.awt.Point;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Balls {
	private ArrayList<Point> balls;
	private ArrayList<Point> direction;
	private ArrayList<Point> initialPosition;
	private static final int rndMin = 5;
	private static int rndMax;
	private static final int vitesseMax = 25;
	private static final Point d = new Point(10, 10);
	private int width;
	private int height;

	public Balls() {
		this.balls = new ArrayList<Point>();
		this.direction = new ArrayList<Point>();
		this.initialPosition = new ArrayList<Point>();
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void addBall(Point p) {
		this.balls.add(p);
		this.direction.add(new Point(d));
		this.initialPosition.add(new Point(p));
	}

	public void translate(int dx, int dy) {
		for (Point b : balls)
			b.translate(dx, dy);
	}

	public void translateBalls() {
		final int ballSize = BallsSimulator.BALLSIZE;
		Iterator<Point> itDirection = direction.iterator();

		rndMax = Math.min(width, height);

		for (Point b : balls) {
			Point dir = itDirection.next();
			int dx = ThreadLocalRandom.current().nextInt(rndMin, rndMax + 1) % vitesseMax + 1;
			int dy = ThreadLocalRandom.current().nextInt(rndMin, rndMax + 1) % vitesseMax + 1;
			
			if ((int)b.getX() + (int)dir.getX() > width && (int)b.getY() + (int)dir.getY() > height) {
				dir.setLocation(-dx, -dy);
				b.translate(width - (int) b.getX(), height - (int) b.getY());
			}
			else if ((int)b.getX() + (int)dir.getX() > width) {
				dir.setLocation(-dx, (int)dir.getY());
				b.translate(width - (int)b.getX(), (int)dir.getY());
			}
			else if ((int)b.getY() + (int)dir.getY() > height) {
				dir.setLocation((int)dir.getX(), -dy);
				b.translate((int)dir.getX(), height - (int)b.getY());
			}
			else if ((int)b.getX() + (int)dir.getX() < ballSize && (int)b.getY() + (int)dir.getY() < ballSize){
				dir.setLocation(dx, dy);
				b.translate(ballSize - (int)b.getX(), ballSize - (int)b.getY());
			}
			else if ((int)b.getX() + (int)dir.getX() < ballSize) {
				dir.setLocation(dx, (int)dir.getY());
				b.translate(ballSize - (int)b.getX(), (int)dir.getY());
			}
			else if ((int)b.getY() + (int)dir.getY() < ballSize) {
				dir.setLocation((int)dir.getX(), dy);
				b.translate((int)dir.getX(), ballSize - (int)b.getY());
			}
			else if ((int)b.getX() + (int)dir.getX() > width && (int)b.getY() + (int)dir.getY() < ballSize) {
				dir.setLocation(-dx, dy);
				b.translate(width - (int)b.getX(), ballSize - (int)b.getY());
			}
			else if ((int)b.getX() + (int)dir.getX() < ballSize && (int)b.getY() + (int)dir.getY() > height) {
				dir.setLocation(dx, -dy);
				b.translate(ballSize - (int)b.getX(), height - (int)b.getY());
			}
			else
				b.translate((int)dir.getX(), (int)dir.getY());
		}
	}
	
	public void reInit(){
		Iterator<Point> itPosition = initialPosition.iterator();
		for(Point b : balls)
			b.setLocation(itPosition.next());
		
		for(Point dir : direction)
			dir.setLocation(d);
			
		// balls, initialPosition et direction ont le même nombre d'éléments
	}
	
	public Iterator<Point> iterator(){
		return this.balls.iterator();
	}
	
	public String toString(){
		String res = new String();
		res = "[ ";
		
		Iterator<Point> it = balls.iterator();
		while(it.hasNext()){
			Point p = it.next();
			if(it.hasNext())
				res += p.toString() + ", ";
			else
				res += p.toString();
		}

		res += " ]";
		return res;
	}
	
}
