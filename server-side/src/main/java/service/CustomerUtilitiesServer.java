package service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.google.gson.Gson;

import dao.UserDAO;
import model.User;

public class CustomerUtilitiesServer implements Runnable
{
	private ServerSocket ss;

	public CustomerUtilitiesServer(int port) throws IOException 
	{
		ss = new ServerSocket(port);
		ss.setSoTimeout(80000);
	}
	
	public void accept() throws IOException 
	{
		String string = "";
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
				String data_server = bufferedInputReader.readLine();
				System.out.println("Command received:  " + inputCommand);
				System.out.println("Data received:  " + data_server);

				boolean ok = false;

				Gson gson = new Gson();
				User user;
				UserDAO dto;

				switch (inputCommand) 
				{
					case "register":
						user = gson.fromJson(data_server, User.class);
						dto = new UserDAO(User.class);
						dto.create(user);
						string = "The user has successfully registered!";
						ok = true;
						break;

					case "login":
						user = gson.fromJson(data_server, User.class);
						dto = new UserDAO(User.class);
						User u = dto.findByUsernameAndPassword(user.getUsername(), user.getPassword());
						if (u != null)
						{
							string = "The user successfully logged in!";
						}
						else
						{
							string = "";
						}
						ok = true;
						break;

					default:
						break;
				}

				if (ok == true) 
				{
					bufferedOutputWriter.write("    " + string);
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
