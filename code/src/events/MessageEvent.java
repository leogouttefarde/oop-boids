package events;

public class MessageEvent extends Event {

	private String message;

	public MessageEvent(int date, String message) {
		super(date);
		this.message = message;
	}

	public void execute() {
		System.out.println(this.getDate() + this.message);
	}
}

