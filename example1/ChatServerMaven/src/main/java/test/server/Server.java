package test.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import net.forlornly.mip.chat.api.Room;

/**
 * Implements a chat server running on the specified TCP/IP port
 *
 */
public class Server {
	/**
	 * Chat server TCP/IP port to listen on
	 */
	private int port;

	/**
	 * The list of handlers for connected clients
	 */
	private List<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(this.toString());

	/**
	 * Create a chat server
	 * 
	 * @param port the TCP/IP port to listen on
	 */
	public Server(int port) {
		this.port = port;
	}

	/**
	 * Creates a fixed thread pool and listens on the specified port When a new
	 * connection in accepted on the port, creates a new ClientHandler to handle the
	 * new connection
	 */
	public void run() {
		logger.debug("start server on port " + port);

		ExecutorService es = Executors.newFixedThreadPool(100);

		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(port);

			while (true) {
				logger.debug("accepting connection...");

				es.execute(new ClientHandler(this, ss.accept()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a client handler to the list of client handlers
	 * 
	 * @param clientHandler the client handler to be added
	 */
	public synchronized void addClient(ClientHandler clientHandler) {
		logger.debug("added client " + clientHandler);

		clientHandlers.add(clientHandler);
	}

	/**
	 * Removes a client handler from the list of client handlers
	 * 
	 * @param clientHandler the client handler to be removed
	 */
	public synchronized void removeClient(ClientHandler clientHandler) {
		logger.debug("removed client " + clientHandler);

		clientHandlers.remove(clientHandler);
	}

	/**
	 * Sends a message to all connected clients
	 * 
	 * @param message the message to be sent
	 */
	public synchronized void broadcast(String message) {
		logger.debug("broadcasting " + message);

		for (ClientHandler ch : clientHandlers) {
			ch.write("MESSAGE " + message);
		}
	}

	/**
	 * Sends a message to all clients in a room
	 * 
	 * @param room    the room where the message is being sent
	 * @param message the message to be sent
	 */
	public synchronized void broadcastToRoom(Room room, String message) {
		logger.debug("broadcasting to room " + room.getId() + ", message " + message);

		for (ClientHandler ch : clientHandlers) {
			if (ch.getRoom() != null && ch.getRoom().getId().equals(room.getId())) {
				ch.write("MESSAGE " + message);
			}
		}
	}

	/**
	 * Finds a room belonging to one of the connected clients
	 * 
	 * @param id the room identifier
	 * @return a room if one is found, null otherwise
	 */
	public synchronized Room findRoom(String id) {
		logger.debug("looking for room " + id);

		for (ClientHandler ch : clientHandlers) {
			if (ch.getRoom() != null && ch.getRoom().getId().equals(id)) {
				logger.debug("found room " + ch.getRoom());

				return ch.getRoom();
			}
		}

		logger.debug("room not found");

		return null;
	}

	/**
	 * Sends an invitation email to the specified room
	 * 
	 * @param room  the room where the user is invited
	 * @param email the email of the user that is invited
	 */
	public void inviteToRoom(Room room, String email) {
		logger.debug("sending invite email with id " + room.getId() + " to " + email);

		try {
			Properties properties;
			Session session;
			MimeMessage mimeMessage;

			properties = System.getProperties();

			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");

			session = Session.getDefaultInstance(properties, null);

			mimeMessage = new MimeMessage(session);

			mimeMessage.setFrom(new InternetAddress("nume.prenume@student.unitbv.ro"));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mimeMessage.setSubject("Invitation to chat..");
			mimeMessage.setContent("Room id: " + room.getId(), "text/html");

			Transport transport = session.getTransport("smtp");

			transport.connect("student.unitbv.ro", "nume.prenume@student.unitbv.ro", "***PASSWORD***");
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
