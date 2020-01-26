package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import com.google.gson.Gson;

import dao.InvitationDAO;
import model.Event;
import model.EventParticipant;
import model.Invitation;
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
	
	public String sendInvitation(String receivedData)
	{		
		Invitation invitation = gson.fromJson(receivedData, Invitation.class);
		boolean result = invitationDao.create(invitation);
	
		if(result) 
		{
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
		
		try
		{
			Date eventDate =new SimpleDateFormat("yyyy-MM-dd").parse(real.getEventParticipant().getEvent().getDatetime());
			LocalDate currentLocalDate = java.time.LocalDate.now();
			Date currentDate = new Date(currentLocalDate.getYear(), currentLocalDate.getMonthValue(), currentLocalDate.getDayOfMonth());
			if(eventDate.compareTo(currentDate) > 0)
			{
				System.out.println("Event is expired");
				return "Fail";
			}
			if(inv.getSecretCode().compareTo(real.getSecretCode()) != 0)
			{
				System.out.println("Wrong secret code");
				return "Fail";
			}
			if(invitationDao.reserveInvitation(real))
			{
				return "Success";
			}
			return "Fail";
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fail";
		}
	}
}
