import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Balls {
	private Vector<Point> balls;
	private Vector<Point> direction;
	private Vector<Point> initialPosition;
	private static final int rndMin = 5;
	private static int rndMax;
	private static final int vitesseMax = 25;
	private static final Point d = new Point(10, 10);
	
	public Balls(){
		this.balls = new Vector<Point>();
		this.direction = new Vector<Point>();
		this.initialPosition = new Vector<Point>();
	}
	
	public void addBall(Point p){
		this.balls.add(p);
		this.direction.add(new Point(d));
		this.initialPosition.add(new Point(p));
	}
	
	public void translate(int dx, int dy){
		for(Point b : balls)
			b.translate(dx, dy);
	}
	
	public void translateBoundarie(int widhtBoundarie, int heightBoundarie, int ballSize){
		rndMax = Math.min(widhtBoundarie, heightBoundarie);
		Iterator<Point> itDirection = direction.iterator();
		for(Point b : balls){
			Point dir = itDirection.next();
			int dx = ThreadLocalRandom.current().nextInt(rndMin, rndMax + 1) % vitesseMax + 1;
			int dy = ThreadLocalRandom.current().nextInt(rndMin, rndMax + 1) % vitesseMax + 1;
			
			if((int)b.getX() + (int)dir.getX() > widhtBoundarie && (int)b.getY() + (int)dir.getY() > heightBoundarie){
				dir.setLocation(-dx, -dy);
				b.translate(widhtBoundarie - (int)b.getX(), heightBoundarie - (int)b.getY());
			}
			else if((int)b.getX() + (int)dir.getX() > widhtBoundarie){
				dir.setLocation(-dx, (int)dir.getY());
				b.translate(widhtBoundarie - (int)b.getX(), (int)dir.getY());
			}
			else if((int)b.getY() + (int)dir.getY() > heightBoundarie){
				dir.setLocation((int)dir.getX(), -dy);
				b.translate((int)dir.getX(), heightBoundarie - (int)b.getY());
			}
			else if((int)b.getX() + (int)dir.getX() < ballSize && (int)b.getY() + (int)dir.getY() < ballSize){
				dir.setLocation(dx, dy);
				b.translate(ballSize - (int)b.getX(), ballSize - (int)b.getY());
			}
			else if((int)b.getX() + (int)dir.getX() < ballSize){
				dir.setLocation(dx, (int)dir.getY());
				b.translate(ballSize - (int)b.getX(), (int)dir.getY());
			}
			else if((int)b.getY() + (int)dir.getY() < ballSize){
				dir.setLocation((int)dir.getX(), dy);
				b.translate((int)dir.getX(), ballSize - (int)b.getY());
			}
			else if((int)b.getX() + (int)dir.getX() > widhtBoundarie && (int)b.getY() + (int)dir.getY() < ballSize){
				dir.setLocation(-dx, dy);
				b.translate(widhtBoundarie - (int)b.getX(), ballSize - (int)b.getY());
			}
			else if((int)b.getX() + (int)dir.getX() < ballSize && (int)b.getY() + (int)dir.getY() > heightBoundarie){
				dir.setLocation(dx, -dy);
				b.translate(ballSize - (int)b.getX(), heightBoundarie - (int)b.getY());
			}
			else
				b.translate((int)dir.getX(), (int)dir.getY());		
			
			
			//System.out.println(dir.toString());
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
