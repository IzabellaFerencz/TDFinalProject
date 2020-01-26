package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class SuccessController extends BaseController implements Initializable
{
	@FXML
	private Label seatNr;
	
	@FXML 
    protected void handleOK(ActionEvent event) 
    {
		Stage stage = (Stage) seatNr.getScene().getWindow();
	    stage.close();	
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		  int seat = (int)(Math.random()*100);
		  seatNr.setText("Your seat number is: "+String.valueOf(seat));
	}
}
