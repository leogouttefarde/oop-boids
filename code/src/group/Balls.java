package group;

import simulator.BallsSimulator;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;


public class Balls {

	private static final Point BASE_DIR = new Point(10, 10);
	private static final int MAX_SPEED = 25;
	private static final int RND_MIN = 5;

	private LinkedList<Point> balls;
	private LinkedList<Point> direction;
	private LinkedList<Point> beginning;

	private int rndMax;
	private int width;
	private int height;

	public Balls() {
		this.balls = new LinkedList<Point>();
		this.direction = new LinkedList<Point>();
		this.beginning = new LinkedList<Point>();
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void addBall(Point p) {
		this.balls.add(p);
		this.direction.add(new Point(BASE_DIR));
		this.beginning.add(new Point(p));
	}

	public void translate(int dx, int dy) {
		for (Point b : balls)
			b.translate(dx, dy);
	}

	public int randomDir() {
		return ThreadLocalRandom.current().nextInt(RND_MIN, rndMax) % MAX_SPEED + 1;

	}

	public void translateBalls() {
		final int BALLSIZE = BallsSimulator.BALLSIZE;
		Iterator<Point> itDirection = direction.iterator();

		rndMax = Math.min(width, height) + 1;

		for (Point b : balls) {
			final Point dir = itDirection.next();
			final int dx = randomDir();
			final int dy = randomDir();

			// Verification pour toutes les collision possible
			if (b.x + dir.x > width && b.y + dir.y > height) {
				dir.setLocation(-dx, -dy);
				b.translate(width - b.x, height - b.y);
			}
			else if (b.x + dir.x > width) {
				dir.setLocation(-dx, dir.y);
				b.translate(width - b.x, dir.y);
			}
			else if (b.y + dir.y > height) {
				dir.setLocation(dir.x, -dy);
				b.translate(dir.x, height - b.y);
			}
			else if (b.x + dir.x < BALLSIZE && b.y + dir.y < BALLSIZE){
				dir.setLocation(dx, dy);
				b.translate(BALLSIZE - b.x, BALLSIZE - b.y);
			}
			else if (b.x + dir.x < BALLSIZE) {
				dir.setLocation(dx, dir.y);
				b.translate(BALLSIZE - b.x, dir.y);
			}
			else if (b.y + dir.y < BALLSIZE) {
				dir.setLocation(dir.x, dy);
				b.translate(dir.x, BALLSIZE - b.y);
			}
			else if (b.x + dir.x > width && b.y + dir.y < BALLSIZE) {
				dir.setLocation(-dx, dy);
				b.translate(width - b.x, BALLSIZE - b.y);
			}
			else if (b.x + dir.x < BALLSIZE && b.y + dir.y > height) {
				dir.setLocation(dx, -dy);
				b.translate(BALLSIZE - b.x, height - b.y);
			}
			else
				b.translate(dir.x, dir.y);
		}
	}

	public void reInit() {
		Iterator<Point> itPos = beginning.iterator();

		// balls, beginning et direction ont autant d'éléments
		for(Point b : balls)
			b.setLocation(itPos.next());
		
		for(Point dir : direction)
			dir.setLocation(BASE_DIR);
	}

	public Iterator<Point> iterator() {
		return this.balls.iterator();
	}

	public String toString() {
		String res = new String("[ ");
		
		Iterator<Point> it = balls.iterator();

		while(it.hasNext()) {
			Point p = it.next();
			res += p;

			if(it.hasNext())
				res += ", ";
		}

		res += " ]";

		return res;
	}
	
}
