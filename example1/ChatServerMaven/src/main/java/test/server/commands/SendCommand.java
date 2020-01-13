package test.server.commands;

/**
 * Implements the SEND command, sends a message in the room where the user is in
 *
 * @author Andrei Gavrila
 */
import org.apache.log4j.Logger;

import test.server.ClientHandler;



public class SendCommand implements Command {
	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(this.toString());

	@Override
	public String getCommand() {
		return "SEND ";
	}

	@Override
	public boolean process(String argument, ClientHandler clientHandler) {
		if (clientHandler.getRoom() != null) {
			logger.debug("sending " + argument + " to room " + clientHandler.getRoom().getId());

			clientHandler.getServer().broadcastToRoom(clientHandler.getRoom(), argument);

			return true;
		}

		return false;
	}
}
