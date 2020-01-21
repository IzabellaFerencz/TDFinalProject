package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.Gson;

import application.SocketClientCallable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class RegisterController
{
	@FXML
	private Button btnLogIn;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confPasswordField;
	@FXML
	private Label message;
	
	private int port = 9001;
	
	@FXML 
    protected void handleSubmitButtonAction(ActionEvent event) 
    {
		String username = usernameField.getText();
		String email = emailField.getText();
		String password = passwordField.getText();
		String confPassword = confPasswordField.getText();
		
		if(username.compareTo("") == 0 || email.compareTo("") == 0 || password.compareTo("") == 0)
		{
			message.setText("Complete all fields!");
			return;
		}
		
		if(password.compareTo(confPassword) != 0)
		{
			message.setText("Password and Confirmation password do not match!");
			return;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("email", email);
		map.put("password", password);
		
		Gson gson = new Gson();

		String serverResponse = sendToServer(map);
		if (serverResponse.compareTo("Fail")==0)
		{
			message.setText("Registration failed! Username or email already in use!");
		}
		else
		{
			User.setUser(gson.fromJson(serverResponse, User.class));
			redirect(event, "../fxml/EventListPage.fxml", 700, 600);
		}
    }
	
	public String sendToServer(Map<String, String> map) 
    {
		System.out.println("Sending command to server");
		SocketClientCallable commandWithSocket = new SocketClientCallable("localhost", port, "register", map);
		String response = receiveFromServer(commandWithSocket);
		System.out.println(response);
		return response;

	}

	public String receiveFromServer(SocketClientCallable commandWithSocket) 
	{
		String serverResponse;
		ExecutorService es = Executors.newCachedThreadPool();
		Future<String> response = es.submit(commandWithSocket);
		try 
		{
			serverResponse = response.get();
			return serverResponse;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public void redirect(ActionEvent event, String view, double width, double height)
	{
		try 
		{
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource(view));
			Scene scene = new Scene(root, width, height);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
