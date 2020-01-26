package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Event;
import model.User;

public class EventCrudController extends BaseController implements Initializable
{
	@FXML
	private TextField nameField;
	@FXML
	private TextField locationField;
	@FXML
	private TextField datetimeField;
	@FXML
	private TextField seatsField;
	@FXML
	private Label username;
	@FXML
	private Label message;
	
	@FXML 
    protected void handleNewEvent(ActionEvent event) 
    {
		Gson gson = new Gson();
		String name = nameField.getText();
		String location = locationField.getText();
		String date = datetimeField.getText();
		String seats = seatsField.getText();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("location", location);
		map.put("datetime", date);
		map.put("nrOfSeats", seats);
		map.put("user", User.getUser());
		
		String json = gson.toJson(map);
		
		String serverResponse = sendToServer("newEvent", json);
		if (serverResponse.compareTo("Fail")==0)
		{
				message.setText("Failed to create event!");
		}
		else
		{
			redirect(event, "../fxml/OrganizerEventListPage.fxml", 1000, 600);
		}
    }
	
	@FXML 
    protected void handleCancel(ActionEvent event) 
    {
		redirect(event, "../fxml/OrganizerEventListPage.fxml", 1000, 600);
    }
	
	@FXML
	protected void handleEditEvent(ActionEvent event)
	{
		Gson gson = new Gson();
		String name = nameField.getText();
		String location = locationField.getText();
		String date = datetimeField.getText();
		String seats = seatsField.getText();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idEvent", String.valueOf(Event.getEvent().getIdEvent()));
		map.put("name", name);
		map.put("location", location);
		map.put("datetime", date);
		map.put("nrOfSeats", seats);
		map.put("user", User.getUser());
		
		String json = gson.toJson(map);
		
		String serverResponse = sendToServer("editEvent", json);
		if (serverResponse.compareTo("Fail")==0)
		{
				message.setText("Failed to create event!");
		}
		else
		{
			redirect(event, "../fxml/OrganizerEventListPage.fxml", 1000, 600);
		}
	}
	
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
		  if(Event.getEvent()!= null)
		  {
			  nameField.setText(Event.getEvent().getName());
			  datetimeField.setText(Event.getEvent().getDatetime());
			  locationField.setText(Event.getEvent().getLocation());
			  seatsField.setText(String.valueOf(Event.getEvent().getNrOfSeats()));
		  }
	}
}
