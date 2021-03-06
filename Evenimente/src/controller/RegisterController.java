package controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import model.UserRoles;

public class RegisterController extends BaseController
{
	@FXML
	private Button btnLogIn;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confPasswordField;
	@FXML
	private Label message;
	@FXML
	private Button backToLogInBtn;
	
	@FXML 
    protected void handleSubmitButtonAction(ActionEvent event) 
    {
		String username = usernameField.getText();
		String email = emailField.getText();
		String password = passwordField.getText();
		String confPassword = confPasswordField.getText();
		
		if(username.compareTo("") == 0 || email.compareTo("") == 0 || password.compareTo("") == 0)
		{
			message.setText("Complete all fields!");
			return;
		}
		
		if(password.compareTo(confPassword) != 0)
		{
			message.setText("Password and Confirmation password do not match!");
			return;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("email", email);
		map.put("password", password);
		
		Gson gson = new Gson();

		String serverResponse = sendToServer("register", map);
		if (serverResponse.compareTo("Fail")==0)
		{
			message.setText("Registration failed! Username or email already in use!");
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
					redirect(event, "../fxml/OrganizerEventListPage.fxml", 1000, 600);
				}
				else
				{
					redirect(event, "../fxml/EventListPage.fxml", 1000, 600);
				}
			}
			
		}
    }
	
	@FXML 
    protected void handleLogInBtn(ActionEvent event) 
    {
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
}
