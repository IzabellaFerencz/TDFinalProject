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
	private TextField invitesField;
	@FXML
	private Label username;
	
	@FXML 
    protected void handleNewEvent(ActionEvent event) 
    {
		Gson gson = new Gson();
		String name = nameField.getText();
		String location = locationField.getText();
		String date = datetimeField.getText();
		String seats = seatsField.getText();
		String invites = invitesField.getText();

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("location", location);
		map.put("datetime", date);
		map.put("nrOfSeats", seats);
		map.put("nrOfInvites", invites);
		map.put("user", gson.toJson(User.getUser()));
		
		String serverResponse = sendToServer("newEvent", map);
		if (serverResponse.compareTo("Fail")==0)
		{
				
		}
		else
		{
			redirect(event, "../fxml/OrganizerEventListPage.fxml", 700, 600);
		}
    }
	
	@FXML 
    protected void handleCancel(ActionEvent event) 
    {
		redirect(event, "../fxml/OrganizerEventListPage.fxml", 700, 600);
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
	}
}
