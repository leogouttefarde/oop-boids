
public class BoidsEvent extends Event {

	private Boids boids;

	public BoidsEvent(long date, Boids boids) {
		super(date);
		this.boids = boids;
	}

	public void execute() {
		boids.update();
		EventManager.Get().addEvent(new BoidsEvent(getDate() + 1, boids));
	}
}

