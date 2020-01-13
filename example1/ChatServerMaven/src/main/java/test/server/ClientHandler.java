package test.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import net.forlornly.mip.chat.api.Room;
import test.server.commands.Command;



/**
 * Handles the connection to a chat client
 * 
 *
 */
public class ClientHandler implements Runnable {
	/**
	 * The server that created this handler
	 */
	private Server server;

	/**
	 * The TCP/IP socket to communicate with the client
	 */
	private Socket socket;

	/**
	 * Scanner used for input (receiving messages from the client)
	 */
	private Scanner scanner;

	/**
	 * Print Writer used for output (sending messages to the client)
	 * 
	 */
	private PrintWriter printWriter;

	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(this.toString());

	/**
	 * The room that this user is in
	 */
	private Room room = null;

	/**
	 * A list of handled commands (instances of Command)
	 */
	private List<Command> commands = new ArrayList<Command>();

	/**
	 * Create a client handler
	 * 
	 * @param server the server that created this handler
	 * @param socket the TCP/IP socket used to communicate with the client
	 */
	public ClientHandler(Server server, Socket socket) {
		logger.debug("accepted connection...");

		this.server = server;
		this.socket = socket;
	}

	/**
	 * Retrieve the room that this user is in
	 * 
	 * @return the room that this user is in
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Set the room that this user is in
	 * 
	 * @param room the room that this user is in
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Retrieve the server that created this handler
	 * 
	 * @return the server that created this handler
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Read the messages sent from the client and attempt to process the commands.
	 * The list of valid commands is built by scanning the net.forlornly.mip.chat
	 * package for classes that implement the Command interface
	 */
	@Override
	public void run() {
		Reflections reflections = new Reflections("test.server");

		for (Class<? extends Command> cc : reflections.getSubTypesOf(Command.class)) {
			try {
				Command c = cc.newInstance();

				logger.debug("adding command processor " + c);

				commands.add(c);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		try {
			scanner = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			printWriter = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.addClient(this);

		while (true) {
			String message = read();

			if (message == null) {
				break;
			}

			logger.debug("received " + message);

			boolean processed = false;

			for (Command c : commands) {
				if (message.startsWith(c.getCommand())) {
					if (c.process(message.substring(c.getCommand().length()), this)) {
						processed = true;
					}
				}
			}

			if (!processed) {
				write("ERROR");
			}
		}

		logger.debug("disconnected");

		server.removeClient(this);
	}

	/**
	 * Read a message from the client
	 * 
	 * @return the read message or null on exception (client disconnected)
	 */
	public String read() {
		try {
			String string = scanner.nextLine();

			logger.debug("read " + string);

			return string;
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * Write a message to the client
	 * 
	 * @param string the message to be sent
	 */
	public synchronized void write(String string) {
		logger.debug("write " + string);

		printWriter.println(string);

		printWriter.flush();
	}
}
