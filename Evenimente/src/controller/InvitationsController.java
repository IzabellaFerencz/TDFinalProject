package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.EventParticipant;
import model.Invitation;
import model.User;

public class InvitationsController extends BaseController implements Initializable
{
	@FXML
	private Label username;
	@FXML
	private Label message;
	@FXML
	private Label eventLabel;
	@FXML
	private Label participantLabel;
	@FXML
	private TextField fileField;
	@FXML
	private TextField secretCodeField;
	
	@FXML 
    protected void handleCancel(ActionEvent event) 
    {
		redirect(event, "../fxml/ParticipantListPage.fxml", 800, 600);
    }
	
	@FXML 
    protected void handleSend(ActionEvent event) 
    {
		String filePath = fileField.getText();
		String secretCode = secretCodeField.getText();
		
		if(filePath.isEmpty() || secretCode.isEmpty())
		{
			message.setText("Help file and secret code must be specified!");
			return;
		}
		
		Invitation invite = new Invitation();
		invite.setAccepted(false);
		invite.setHelpFile(filePath);
		invite.setSecretCode(secretCode);
		invite.setEventParticipant(EventParticipant.getEventParticipant());
		Gson gson = new Gson();
		String serverResponse = sendToServer("sendInvitation", gson.toJson(invite));
		if(serverResponse.compareTo("Fail")==0)
		{
			message.setText("Failed to send invitation");
		}
		else
		{
			redirect(event, "../fxml/ParticipantListPage.fxml", 1000, 600);
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
		  
		  participantLabel.setText(EventParticipant.getEventParticipant().getParticipant().getUsername());
		  eventLabel.setText(EventParticipant.getEventParticipant().getEvent().getName());
	}
	

	
}
