package controller;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Event;
import model.EventModel;
import model.Notification;
import model.NotificationModel;
import model.User;

public class NotificationsController extends BaseController implements Initializable
{
	@FXML
	private Label username;
	@FXML
	private Label message;
	@FXML
	private TableView<NotificationModel> notifications;
	@FXML
	private TableColumn<NotificationModel, Integer> idCell;
	@FXML
	private TableColumn<NotificationModel, String> messageCell;
	@FXML
	private TableColumn<NotificationModel, String> dateCell;
	private ObservableList<NotificationModel> notificationModels;
	
	@FXML 
    protected void handleLogOut(ActionEvent event) 
    {
		User.setUser(null);
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
	@FXML 
    protected void handleBack(ActionEvent event) 
    {
		redirect(event, "../fxml/EventListPage.fxml", 1000, 600);
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
		  loadNotifications();
	}
	
	private void loadNotifications()
	{
		Gson gson = new Gson();
		String serverResponse = sendToServer("getUsersNotifications", gson.toJson(User.getUser()));
		if (serverResponse.compareTo("Fail") == 0) 
		{
			System.out.println("Failed to get notifcations");
		} 
		else 
		{
			Type notificationListType = new TypeToken<ArrayList<Notification>>() {}.getType();
			ArrayList<Notification> listw = gson.fromJson(serverResponse, notificationListType);
			ArrayList<NotificationModel> notificationList = new ArrayList<>();
			for (Notification notifObj : listw)
			{
				notificationList.add(new NotificationModel(notifObj.getId(),notifObj.getMessage(), notifObj.getDate()));
			}
			this.notificationModels = FXCollections.observableList(notificationList);
			idCell.setCellValueFactory(new PropertyValueFactory<>("Id"));
			messageCell.setCellValueFactory(new PropertyValueFactory<>("Message"));
			dateCell.setCellValueFactory(new PropertyValueFactory<>("Date"));
			notifications.setItems(this.notificationModels);
		}
	}
}
