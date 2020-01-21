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
import model.EventModel;
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
	private TableView<EventModel> events;
	@FXML
	private Label username;
	@FXML
	private TableColumn<EventModel, Integer> idCell;
	@FXML
	private TableColumn<EventModel, String> nameCell;
	@FXML
	private TableColumn<EventModel, String> locationCell;
	@FXML
	private TableColumn<EventModel, String> dateCell;
	@FXML
	private TableColumn<EventModel, Integer> seatsCell;
	@FXML
	private TableColumn<EventModel, String> orgCell;
	
	private ObservableList<EventModel> eventsModels;
	
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
			ArrayList<EventModel> eventList = new ArrayList<>();
			for (Event eventObj : listw)
			{
				eventList.add(new EventModel(eventObj.getIdEvent(),eventObj.getName(), eventObj.getLocation(), eventObj.getDatetime(), eventObj.getNrOfSeats(), eventObj.getNrOfInvites(), eventObj.getUser().getUsername()));
			}
			this.eventsModels = FXCollections.observableList(eventList);
			idCell.setCellValueFactory(new PropertyValueFactory<>("Id"));
			nameCell.setCellValueFactory(new PropertyValueFactory<>("Name"));
			locationCell.setCellValueFactory(new PropertyValueFactory<>("Location"));
			dateCell.setCellValueFactory(new PropertyValueFactory<>("Datetime"));
			seatsCell.setCellValueFactory(new PropertyValueFactory<>("NrOfSeats"));
			orgCell.setCellValueFactory(new PropertyValueFactory<>("Organizer"));
			events.setItems(this.eventsModels);
		}

	}	
}
