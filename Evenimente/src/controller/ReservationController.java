package controller;


import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.EventParticipant;
import model.Invitation;
import model.User;

public class ReservationController extends BaseController implements Initializable
{
	@FXML
	private Label username;
	@FXML
	private Label fileField;
	@FXML
	private Label nameField;
	@FXML
	private Label locationField;
	@FXML
	private Label datetimeField;
	@FXML
	private TextField secretCodeField;
	@FXML
	private Label message;
	
	@FXML 
    protected void handleCancel(ActionEvent event) 
    {
		Stage stage = (Stage) username.getScene().getWindow();
	    stage.close();	
    }
	
	@FXML 
    protected void handleReserve(ActionEvent event) 
    {
		Gson gson = new Gson();
		String secretCode = secretCodeField.getText();
		Invitation invToReserve = new Invitation();
		invToReserve.setIdInvitation(Invitation.getInvitation().getIdInvitation());
		invToReserve.setSecretCode(secretCode);
		String serverResponse = sendToServer("reserveInvitation", gson.toJson(invToReserve));
		if(serverResponse.compareTo("Fail")==0)
		{
			message.setText("Failed to reserve seat. Check if secret code is correct! If secret code is correct we are sorry all seats are already booked!");
			System.out.println("Failed to reserve seat. Check if secret code is correct! If secret code is correct we are sorry all seats are already booked!");
		}
		else
		{
			redirect(event,"../fxml/Success.fxml", 600, 500);
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
		  loadModel();
	}
	
	private void loadModel()
	{
		Gson gson = new Gson();
		Invitation inv = Invitation.getInvitation();
		String serverResponse = sendToServer("getInvite", gson.toJson(inv));
		if(serverResponse.compareTo("Fail")==0)
		{
			System.out.println("Failed to load data");
		}
		else
		{
			Invitation real= gson.fromJson(serverResponse, Invitation.class);
			datetimeField.setText(real.getEventParticipant().getEvent().getDatetime());
			locationField.setText(real.getEventParticipant().getEvent().getLocation());
			nameField.setText(real.getEventParticipant().getEvent().getName());
			fileField.setText(real.getHelpFile());
		}
	}
}
