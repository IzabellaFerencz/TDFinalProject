package test.server;



/**
 * Create a new chat server on port 12345 and run the server
 *
 */
public class App {
	public static void main(String[] args) {
		new Server(12345).run();
	}
}
