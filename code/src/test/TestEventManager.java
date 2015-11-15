package test;

import event.Event;
import event.EventManager;
import event.MessageEvent;

/**
 * Classe de démarrage du test des classes Event et EventManager. 
 * (Question 9 du sujet)
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 * @see Event
 * @see EventManager
 */
public class TestEventManager {

	/**
	 * Méthode main de démarrage du test des classes Event et EventManager.
	 * @param args Arguments (non utilisés)
	 */
	public static void main(String[] args) throws InterruptedException {

		// On crée un simulateur
		EventManager manager = EventManager.Get();

		// On poste un événement [PING] tous les deux pas de temps
		for (int i = 2; i <= 10; i += 2) {
			manager.addEvent(new MessageEvent(i, " [PING]"));
		}

		// On poste un événement [PONG] tous les trois pas de temps
		for (int i = 3; i <= 9; i += 3) {
			manager.addEvent(new MessageEvent(i, " [PONG]"));
		}

		while (!manager.isFinished()) {
			manager.next();
			Thread.sleep(1000);
		}
	}
}

