package events;

import groups.Balls;

public class BallsEvent extends Event {

	private Balls balls;

	public BallsEvent(long date, Balls balls) {
		super(date);
		this.balls = balls;
	}

	public void execute() {
		balls.translateBalls();
		EventManager.Get().addEvent(new BallsEvent(getDate() + 1, balls));
	}
}

