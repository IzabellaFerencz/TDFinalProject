package service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class CustomerUtilitiesServer implements Runnable
{
	private ServerSocket ss;
	private UserService userService;
	private EventsService eventService;
	private UserRolesService userRolesService;
	private EventParticipantService eventParticipantService;

	public CustomerUtilitiesServer(int port) throws IOException 
	{
		ss = new ServerSocket(port);
		ss.setSoTimeout(80000);
	}
	
	public void accept() throws IOException 
	{
		String string = "";
	    this.userService=new UserService();
	    this.eventService = new EventsService();
	    this.userRolesService = new UserRolesService();
	    this.eventParticipantService = new EventParticipantService();
		System.out.println("Accepting connections on port " + ss.getLocalPort());
		
		while (!Thread.interrupted()) 
		{
			try (Socket socket = ss.accept()) 
			{
				System.out.println("connection accepted");

				BufferedWriter bufferedOutputWriter = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream()));
				
				BufferedReader bufferedInputReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), "UTF-8"));

				String inputCommand = bufferedInputReader.readLine();
				String receivedData = bufferedInputReader.readLine();
				System.out.println("Command received:  " + inputCommand);
				System.out.println("Data received:  " + receivedData);

				boolean ok = false;

				switch (inputCommand) 
				{
					case "register":
						string = userService.register(receivedData);
						
						ok = true;
						break;

					case "login":
						string = userService.login(receivedData);
						System.out.println("string login " +string);
						ok = true;
						break;

					case "getEvents":
						string = eventService.getAllEvents();						
						ok = true;
						break; 
						
					case "getRoles":
						string = userRolesService.getRolesOfUser(receivedData);						
						ok = true;
						break; 
						
					case "newEvent":
						string = eventService.newEvent(receivedData);						
						ok = true;
						break; 
						
					case "getEventsByOrganizer":
						string = eventService.getEventsByOrganizer(receivedData);						
						ok = true;
						break; 
						
					case "getEventsParticipant":
						string = eventParticipantService.getEventParticipant(receivedData);						
						ok = true;
						break; 
						
					case "newEventsParticipant":
						string = eventParticipantService.newEventParticipant(receivedData);						
						ok = true;
						break;
						
					default:
						break;
				}

				if (ok == true) 
				{
					bufferedOutputWriter.write(string);
					bufferedOutputWriter.newLine();
					bufferedOutputWriter.flush();
				}

			} 
			catch (SocketTimeoutException ste)
			{
				// timeout every .25 seconds to see if interrupted
			}

		}
		System.out.println("Done accepting");
	}

	@Override
	public void run() 
	{
		try 
		{
			accept();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
}
