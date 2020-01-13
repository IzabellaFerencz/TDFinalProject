package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.SocketClientCallable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class UserController {
	@FXML
	private Button btnLogIn;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;

	@FXML
	void initialize() {
		System.out.println("Application is running!");
		txtUsername.setText("Introduceti username");
		txtPassword.setText("Introduceti password");
		btnLogIn.setDisable(true);

	}

	@FXML
	private void btnSearchClick(ActionEvent event) {

		String username = "";
		String password = "";
		ServerSocket serverSocket = null;
		String jsonResp = "";
		try {

			if (!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
				username = txtUsername.getText();
				password = txtPassword.getText();
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("username", username);
				inputMap.put("password", password);
				jsonResp = mapper.writeValueAsString(inputMap);
				mapper.writeValueAsString(jsonResp);
				Socket socket = serverSocket.accept();
				BufferedWriter bufferedOutputWriter = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream()));

				String serverResponse;
				ExecutorService es = Executors.newCachedThreadPool();

				System.out.println("Sending command to server");
				SocketClientCallable commandWithSocket = new SocketClientCallable("localhost", 9001, "login", jsonResp);
				Future<String> response = es.submit(commandWithSocket);
				try {
					serverResponse = response.get();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}

			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
