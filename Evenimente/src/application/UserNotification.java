package application;

import java.rmi.RemoteException;

import com.google.gson.Gson;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import model.Notification;
import model.User;

public class UserNotification implements IUserNotification
{

	@Override
	public void notifyUser(Notification notification) throws RemoteException
	{
		if(User.getUser().getIdUser() == notification.getUser().getIdUser())
		{
//			 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//	         alert.setTitle("New Notification");
//	         alert.setContentText(notification.getMessage());
//	         ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
//	         alert.getButtonTypes().setAll(okButton);
//	         alert.show();
			System.out.println(notification.getMessage() + " for user "+notification.getUser().getUsername());
		}

	}

}
