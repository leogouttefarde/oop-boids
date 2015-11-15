package tests;

import groups.Balls;
import java.awt.Point;

public class TestBalls {

	public static void main(String[] args) {
		Balls b = new Balls();
		
		b.addBall(new Point(10, 20));
		b.addBall(new Point(15, 25));
		b.addBall(new Point(28, 43));
		b.addBall(new Point(52, 68));
		
		System.out.println(b.toString());
		
		b.translate(5, 10);
		System.out.println(b.toString());
		
		b.reInit();
		System.out.println(b.toString());
		
	}

}
