package test.server.commands;

import org.apache.log4j.Logger;

import test.server.ClientHandler;

/**
 * Implements the INVITE command, sends an email to a user with the room
 * identifier
 *
 */
public class InviteCommand implements Command {
	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(this.toString());

	@Override
	public String getCommand() {
		return "INVITE ";
	}

	@Override
	public boolean process(String argument, ClientHandler clientHandler) {
		if (clientHandler.getRoom() != null) {
			clientHandler.getServer().inviteToRoom(clientHandler.getRoom(), argument);

			logger.debug("invited " + argument + " to room " + clientHandler.getRoom().getId());

			clientHandler.write("INVITED " + argument);

			return true;
		}

		return false;
	}
}
