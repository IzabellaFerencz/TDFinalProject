package controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.Date;

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
		
		if(name.isEmpty())
		{
			message.setText("Name of the event must be specified!");
			return;
		}
		if(location.isEmpty())
		{
			message.setText("Location of the event must be specified!");
			return;
		}
		if(date.isEmpty())
		{
			message.setText("Date of the event must be specified!");
			return;
		}
		try
		{
			Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} 
		catch (ParseException e)
		{
			message.setText("Incorrect date format! Date should be in yyyy-MM-dd format");
			return;
		}
		try
		{
			int nr = Integer.parseInt(seats);
			if(nr <= 0)
			{
				message.setText("Nr of seats must be a positive number");
				return;
			}
		} 
		catch (NumberFormatException e)
		{
			message.setText("Please insert a number for the nr of seats value!");
			return;
		}

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
		
		if(name.isEmpty())
		{
			message.setText("Name of the event must be specified!");
			return;
		}
		if(location.isEmpty())
		{
			message.setText("Location of the event must be specified!");
			return;
		}
		if(date.isEmpty())
		{
			message.setText("Date of the event must be specified!");
			return;
		}
		try
		{
			Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} 
		catch (ParseException e)
		{
			message.setText("Incorrect date format! Date should be in yyyy-MM-dd format");
			return;
		}
		try
		{
			int nr = Integer.parseInt(seats);
			if(nr <= 0)
			{
				message.setText("Nr of seats must be a positive number");
				return;
			}
		} 
		catch (NumberFormatException e)
		{
			message.setText("Please insert a number for the nr of seats value!");
			return;
		}
		

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
