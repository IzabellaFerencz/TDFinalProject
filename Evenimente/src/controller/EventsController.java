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
import model.User;
import model.UserRoles;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.Observable;
import java.util.ResourceBundle;
import java.net.URL;

public class EventsController extends BaseController implements Initializable
{
	@FXML
	private TableView events;
	@FXML
	private Label username;
	@FXML
	private TableColumn idCell;
	@FXML
	private TableColumn nameCell;
	@FXML
	private TableColumn locationCell;
	@FXML
	private TableColumn dateCell;
	@FXML
	private TableColumn seatsCell;
	@FXML
	private TableColumn orgCell;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		  if(User.getUser().getUsername().isEmpty()==true)
		  {     
			  username.setText("Hello, Guest!");
		  }
		  else
		  {
			  username.setText("Hello, " + User.getUser().getUsername() +"!");		
		  }
	}
	
	@FXML 
    protected void handleLogOut(ActionEvent event) 
    {
		User.setUser(null);
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
	@FXML 
    protected void handleNewEvent(ActionEvent event) 
    {
		redirect(event, "../fxml/NewEvent.fxml", 400, 400);
    }
	
	@FXML
	public void getEvents(ActionEvent event) throws ParseException 
	{
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		String serverResponse = sendToServer("getEvents", map);
		if (serverResponse.compareTo("Fail") == 0) 
		{
			System.out.println("Failed to get events");
		} 
		else 
		{
			Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
			ArrayList<Event> listw = gson.fromJson(serverResponse, eventListType);
			ObservableList<Event> list = FXCollections.observableList(listw);
			
			for (int i = 0; i < listw.size(); i++)
			{
				SimpleDateFormat fmt = new SimpleDateFormat("yyy-MM-dd");

				System.out.println(listw.get(i).toString());
			}

			System.out.println("Evenimente in Client: " + list.toString());
			
			this.idCell.setCellFactory(new PropertyValueFactory<Event, String>("idEvent"));
			this.nameCell.setCellFactory(new PropertyValueFactory<Event, String>("name"));
			this.dateCell.setCellFactory(new PropertyValueFactory<Event, String>("datetime"));
			this.locationCell.setCellFactory(new PropertyValueFactory<Event, String>("location"));
			this.seatsCell.setCellFactory(new PropertyValueFactory<Event, String>("nrOfSeats"));
			this.events.setItems(list);

		}

	}	
}
