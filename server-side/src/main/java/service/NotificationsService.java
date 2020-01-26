package service;

import java.util.List;

import com.google.gson.Gson;

import dao.NotificationsDAO;
import model.Invitation;
import model.Notification;
import model.User;

public class NotificationsService
{
	private Gson gson;
	private NotificationsDAO notificationDao;

	public NotificationsService()
	{
		super();
		this.gson = new Gson();
		this.notificationDao = new NotificationsDAO(Notification.class);
	}
	
	public String sendNotification(String receivedData)
	{		
		Notification notification = gson.fromJson(receivedData, Notification.class);
		boolean result = notificationDao.create(notification);
	
		if(result) 
		{
			return "Success";
		}
		else
		{
			return "Fail";
		}
	}
	
	public String getNotificationsOfUser(String receivedData)
	{
		User user = gson.fromJson(receivedData, User.class);

		List<Notification> notifications = notificationDao.getNotificationsForUser(user);
		if(notifications != null)
		{
			return gson.toJson(notifications);
		}
		else
		{
			return "Fail";
		}
	}
}
