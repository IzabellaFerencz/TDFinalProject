package controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import application.SocketClientCallable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserRoles;
import application.SocketClientCallable;

public class UserController extends BaseController
{
	@FXML
	private Button btnLogIn;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label message;
	
	
    @FXML 
    protected void handleSubmitButtonAction(ActionEvent event) 
    {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	
    	if(username.compareTo("") == 0 || password.compareTo("") == 0)
		{
			message.setText("Complete all fields!");
			return;
		}
    	
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		Gson gson = new Gson();
		String serverResponse = sendToServer("login", map);
		if (serverResponse.compareTo("Fail")==0)
		{
			message.setText("Incorrect username or password!");		
		}
		else
		{
			User.setUser(gson.fromJson(serverResponse, User.class));
			
			Map<String, String> roleMap = new HashMap<String, String>();
			roleMap.put("iduser", String.valueOf(User.getUser().getIdUser()));
			String roleResponse = sendToServer("getRoles", roleMap);
			if (roleResponse.compareTo("Fail")==0)
			{
				message.setText("Error occured when getting role information!");		
			}
			else
			{
				boolean isOrganizer = false;
				Type roleListType = new TypeToken<ArrayList<UserRoles>>() {}.getType();
				ArrayList<UserRoles> listw = gson.fromJson(roleResponse, roleListType);
				for (UserRoles userRole : listw)
				{
					if (userRole.getRole().getName().compareTo("Organizer") == 0)
					{
						isOrganizer = true;
						break;
					}
				}
				
				if(isOrganizer)
				{
					redirect(event, "../fxml/OrganizerEventListPage.fxml", 700, 600);
				}
				else
				{
					redirect(event, "../fxml/EventListPage.fxml", 700, 600);
				}
			}
			
		}
    }
    
    @FXML 
    protected void handleRegisterButtonAction(ActionEvent event) 
    {
		redirect(event, "../fxml/NewUser.fxml", 500, 500);
    }
    
    
}
