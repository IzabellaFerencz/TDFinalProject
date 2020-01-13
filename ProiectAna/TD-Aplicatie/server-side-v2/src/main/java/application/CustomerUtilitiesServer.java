package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import service.UserService;

public class CustomerUtilitiesServer implements Runnable {
	private ServerSocket serverSocket;
	private UserService userService;

	public CustomerUtilitiesServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(250);

	}

	public void accept() throws IOException {
		String raspuns = "";
		System.out.println("Accepting connections on port " + serverSocket.getLocalPort());
		while (!Thread.interrupted()) {
			try (Socket socket = serverSocket.accept()) {
				System.out.println("connection accepted");
				BufferedWriter bufferedOutputWriter = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream()));
				BufferedReader bufferedInputReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String tipComandaClient = bufferedInputReader.readLine();
				String data_server = bufferedInputReader.readLine();
				System.out.println("Command received:  " + tipComandaClient);

				boolean ok = false;

				switch (tipComandaClient) {

				case "login":
					ObjectMapper mapper = new ObjectMapper();
					User deLaClient = mapper.readValue(data_server, User.class);
					User findedUser = userService.findUser(deLaClient.getNume());
					if (deLaClient.getNume().equals(deLaClient.getNume())) {
						System.out.println("Hello" + " " + deLaClient.getNume());
						Map<String, String> inputMap = new HashMap<String, String>();
						inputMap.put("username", deLaClient.getNume());
						inputMap.put("password", deLaClient.getPassword());
						inputMap.put("nume", deLaClient.getNume());
						inputMap.put("email", deLaClient.getEmail());
						String jsonResp = mapper.writeValueAsString(inputMap);
						mapper.writeValueAsString(jsonResp);
						bufferedOutputWriter.write(jsonResp);
						bufferedOutputWriter.flush();
						raspuns = "is ok";
						System.out.print(raspuns);
					} else {

						raspuns = "no user";
						System.out.print(raspuns);
					}

					ok = true;
					break;

				default:
					break;
				}

				if (ok == true) {
					bufferedOutputWriter.write("    " + raspuns);
					bufferedOutputWriter.newLine();
					bufferedOutputWriter.flush();
				}

			} catch (SocketTimeoutException ste) {
			}

		}
		System.out.println("Done accepting");
	}


	@Override
	public void run() {
		try {
			accept();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
