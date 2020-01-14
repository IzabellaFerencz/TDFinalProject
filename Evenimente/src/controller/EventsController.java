package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import application.SocketClientCallable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import model.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class EventsController
{
	@FXML
	private TableView events;
	
	@FXML
	public void getEvents(ActionEvent event) throws ParseException 
	{
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		if (sendToServer(map, "getEvents").compareTo("Success") != 0) 
		{
			System.out.println("Failed to get events");
		} 
		else 
		{
			Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
			ArrayList<Event> listw = gson.fromJson(sendToServer(map, "getEvents"), eventListType);
			ObservableList<Event> list = FXCollections.observableList(listw);
			
			for (int i = 0; i < listw.size(); i++)
			{
				SimpleDateFormat fmt = new SimpleDateFormat("yyy-MM-dd");

				System.out.println(listw.get(i).toString());
			}

			System.out.println("Evenimente in Client: " + list.toString());
			//this.cel1.setCellValueFactory(new PropertyValueFactory<Event, String>("location"));
			//this.cel2.setCellValueFactory(new PropertyValueFactory<Event, Date>("stratTime"));
			//this.evenimente.setItems(list);

		}

	}
	
	public String sendToServer(Map<String, String> map, String action) 
	{
		System.out.println("Sending command to server");
		SocketClientCallable commandWithSocket = new SocketClientCallable("localhost", 9001, action, map);
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
}
