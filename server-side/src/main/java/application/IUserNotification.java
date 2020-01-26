package application;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Notification;

public interface IUserNotification extends Remote
{
	public void notifyUser(Notification notification) throws RemoteException;
}
