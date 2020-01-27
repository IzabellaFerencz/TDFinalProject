package service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import com.google.gson.Gson;

import application.IUserNotification;
import dao.InvitationDAO;
import model.Event;
import model.EventParticipant;
import model.Invitation;
import model.Notification;
import model.User;

public class InvitationService
{
	private Gson gson;
	private InvitationDAO invitationDao;
	
	public InvitationService()
	{
		super();
		this.gson = new Gson();
		this.invitationDao = new InvitationDAO(Invitation.class);
	}
	
	public void sendAlertNotification(Notification notification)
	{
		try 
		{
			int port = notification.getUser().getIdUser();
			Registry reg = 	LocateRegistry.getRegistry(port);
			IUserNotification stub = (IUserNotification) reg.lookup("Notifications");
			stub.notifyUser(notification);
			System.out.println("Alert notification sent ");
		}
		catch(Exception e)
		{
			System.err.println("Client exception "+ e.toString());
			e.printStackTrace();
		}
	}
	
	public String sendInvitation(String receivedData)
	{		
		Invitation invitation = gson.fromJson(receivedData, Invitation.class);
		boolean result = invitationDao.create(invitation);
	
		if(result) 
		{
			NotificationsService notif = new NotificationsService();
			Notification notification = new Notification();
			notification.setMessage("You received a new invitation!");
			notification.setUser(invitation.getEventParticipant().getParticipant());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			notification.setDate(dateFormat.format(new Date(System.currentTimeMillis())));
			notif.sendNotification(gson.toJson(notification));
			sendAlertNotification(notification);
			return "Success";
		}
		else
		{
			return "Fail";
		}
	}

	public String getParticipantStatus(String receivedData)
	{
		EventParticipant participant = gson.fromJson(receivedData, EventParticipant.class);
		Invitation inv = invitationDao.getInviteForEventParticipant(participant);
		if(inv != null)
		{
			if(inv.isAccepted() == true)
			{
				return "Reserved";
			}
			else
			{
				return "Invited";
			}
		}
		return "Not Invited";
	}

	public String getInvitesOfUser(String receivedData)
	{
		User participant = gson.fromJson(receivedData, User.class);

		List<Invitation> invites = invitationDao.getInvitesOfUser(participant);
		if(invites != null)
		{
			return gson.toJson(invites);
		}
		else
		{
			return "Fail";
		}
	}

	public String getInvitation(String receivedData)
	{
		Invitation inv = gson.fromJson(receivedData, Invitation.class);
		Invitation real = (Invitation)invitationDao.find(inv.getIdInvitation());
		if(real != null)
		{
			return gson.toJson(real);
		}
		return "Fail";
	}

	public String reserveInvitation(String receivedData)
	{
		Invitation inv = gson.fromJson(receivedData, Invitation.class);
		Invitation real = (Invitation)invitationDao.find(inv.getIdInvitation());
		if(real == null)
		{
			return "Invalid invitation specified";
		}
		
		try
		{
			Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(real.getEventParticipant().getEvent().getDatetime());
			Date currentDate = new Date(System.currentTimeMillis());
			if(eventDate.compareTo(currentDate) < 0)
			{
				System.out.println("Event is expired");
				return "Event is expired";
			}
			if(inv.getSecretCode().compareTo(real.getSecretCode()) != 0)
			{
				System.out.println("Wrong secret code");
				return "Wrong secret code";
			}
			if(invitationDao.reserveInvitation(real))
			{
				return "Success";
			}
			return "All seats are already booked, sorry";
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fail";
		}
	}
}
