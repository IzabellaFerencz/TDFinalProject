package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.SocketClientCallable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import application.SocketClientCallable;

public class UserController 
{
	@FXML
	private Button btnLogIn;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	private int port = 9001;
	
    @FXML 
    protected void handleSubmitButtonAction(ActionEvent event) 
    {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		if (sendToServer(map).compareTo("    ")!=0)
		{
			redirect(event, "../fxml/NewUser.fxml");
		}
    }
    
    public String sendToServer(Map<String, String> map) {

		System.out.println("Sending command to server");
		SocketClientCallable commandWithSocket = new SocketClientCallable("localhost", port, "login", map);
		System.out.println(receiveFromServer(commandWithSocket));
		return receiveFromServer(commandWithSocket);

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

	public void redirect(ActionEvent event, String view)
	{
		try 
		{
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource(view));
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
