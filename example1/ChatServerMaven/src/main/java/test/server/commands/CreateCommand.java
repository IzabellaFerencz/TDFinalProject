package test.server.commands;

import org.apache.log4j.Logger;

import test.server.ClientHandler;
import net.forlornly.mip.chat.api.Room;

/**
 * Implements the CREATE command, creates a new room
 *
 */
public class CreateCommand implements Command {
	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(this.toString());

	@Override
	public String getCommand() {
		return "CREATE";
	}

	@Override
	public boolean process(String argument, ClientHandler clientHandler) {
		if (clientHandler.getRoom() == null) {
			clientHandler.setRoom(new Room());

			logger.debug("created room " + clientHandler.getRoom().getId());

			clientHandler.write("CREATED " + clientHandler.getRoom().getId());

			return true;
		}

		return false;
	}
}
