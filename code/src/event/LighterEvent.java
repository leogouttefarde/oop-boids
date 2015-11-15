package event;

import element.Lighter;
import java.util.Random;

public class LighterEvent extends Event {

	public LighterEvent(long date) {
		super(date);
	}

	public void execute() {
		Lighter.Event();

		Random rand = new Random();
		int next = rand.nextInt(10);

		EventManager.Get().addEvent(new LighterEvent(getDate() + next));
	}
}

