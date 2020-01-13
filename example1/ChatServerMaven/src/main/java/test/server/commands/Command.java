package test.server.commands;



import test.server.ClientHandler;

/**
 * Interface to enable processing of commands from the client
 *
 */
public interface Command {
	/**
	 * Specifies the implemented command
	 * 
	 * @return the implemented command
	 */
	public String getCommand();

	/**
	 * Process the implemented command
	 * 
	 * @param argument      the argument to the received command
	 * @param clientHandler the handler that calls this command implementation
	 * @return true on success, false on error
	 */
	public boolean process(String argument, ClientHandler clientHandler);
}
