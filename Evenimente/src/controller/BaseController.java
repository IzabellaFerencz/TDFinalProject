package controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.IUserNotification;
import application.SocketClientCallable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Notification;
import model.User;
import javafx.scene.control.ButtonBar;

public class BaseController implements IUserNotification
{
	private int port = 9001;
	
	@Override
	public void notifyUser(Notification notification) throws RemoteException
	{
		if(User.getUser().getIdUser() == notification.getUser().getIdUser())
		{
			 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	         alert.setTitle("New Notification");
	         alert.setContentText(notification.getMessage());
	         ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
	         alert.getButtonTypes().setAll(okButton);
	         alert.show();
			System.out.println(notification.getMessage() + " for user "+notification.getUser().getUsername());
		}

	}
	
	public String sendToServer(String command, Map<String, String> map) 
    {
		System.out.println("Sending command to server");
		SocketClientCallable commandWithSocket = new SocketClientCallable("localhost", port, command, map);
		String response = receiveFromServer(commandWithSocket);
		System.out.println(response);
		return response;

	}
	
	public String sendToServer(String command, String data) 
    {
		System.out.println("Sending command to server");
		SocketClientCallable commandWithSocket = new SocketClientCallable("localhost", port, command, data);
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
	
	public void openNewPage(ActionEvent event, String view, double width, double height)
	{
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(view));
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root, width, height));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}
