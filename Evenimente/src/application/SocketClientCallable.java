package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Callable;

public class SocketClientCallable implements Callable<String> 
{

	private String host;
	private int port;
	private Socket socket;
	private String command;
	private Map<String, String> data;

	public SocketClientCallable(String host, int port, String command, Map<String, String> map) 
	{
		this.host = host;
		this.port = port;
		this.command = command;
		this.data = map;
	}

	@Override
	public String call() throws IOException 
	{
		try 
		{
			this.socket = new Socket(this.host, this.port);
			BufferedWriter bufferedOutputWriter = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			
			BufferedReader bufferedInputReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "UTF-8"));

			bufferedOutputWriter.write(command + "\n" + data);
			bufferedOutputWriter.newLine();
			bufferedOutputWriter.flush();

			return bufferedInputReader.readLine();

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
