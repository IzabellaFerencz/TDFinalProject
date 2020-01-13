package test.server.commands;

import org.apache.log4j.Logger;

import net.forlornly.mip.chat.api.Room;
import test.server.ClientHandler;

/**
 * Implements the JOIN command, attempts to find the specified room and joins
 * the room if present
 *
 */
public class JoinCommand implements Command {
	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(this.toString());

	@Override
	public String getCommand() {
		return "JOIN ";
	}
 public static String hardcodedRoom = "";
 public static Boolean isFirst = true;
	@Override
	public boolean process(String argument, ClientHandler clientHandler) {
		if (clientHandler.getRoom() == null) {
			//clientHandler.setRoom(clientHandler.getServer().findRoom(argument));
			if(isFirst == true){
				clientHandler.setRoom(new Room());
				isFirst = false;
				hardcodedRoom = clientHandler.getRoom().getId();
			}
			else{
				clientHandler.setRoom(clientHandler.getServer().findRoom(hardcodedRoom));
			}
			
			
			if (clientHandler.getRoom() != null) {
				logger.debug("joined room " + clientHandler.getRoom().getId());

				clientHandler.write("JOINED " + clientHandler.getRoom().getId());
			} else {
				return false;
			}

			return true;
		}

		return false;
	}
}
