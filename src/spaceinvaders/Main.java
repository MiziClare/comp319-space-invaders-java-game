package spaceinvaders;

public class Main {
	public static void main(String[] args) {
		// Create GameController and Screen to start the game
		GameController controller = new GameController();
		Screen screen = new Screen(controller);

		// Initialise the screen and display it
		screen.init();
	}
}
