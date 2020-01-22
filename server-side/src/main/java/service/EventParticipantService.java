package service;

import java.util.List;

import com.google.gson.Gson;

import dao.EventDAO;
import dao.EventParticipantDAO;
import model.Event;
import model.EventParticipant;
import model.User;
import model.Userroles;

public class EventParticipantService
{
	private Gson gson;
	private EventParticipantDAO eventPartDao;

	public EventParticipantService()
	{
		super();
		this.gson = new Gson();
		this.eventPartDao = new EventParticipantDAO(EventParticipant.class);
	}

	public String getEventParticipant(String receivedData) 
	{
		String response = null;
		EventParticipant ep = gson.fromJson(receivedData, EventParticipant.class);
		EventParticipant part = eventPartDao.findByEventAndParticipant(ep.getParticipant(), ep.getEvent());
		
		if (part != null)
		{
			response = gson.toJson(part);
		}
		else
		{
			response = "Fail";
		}

		return response;
	}
	
	public String newEventParticipant(String receivedData)
	{
		EventParticipant eventPart = gson.fromJson(receivedData, EventParticipant.class);
		boolean result = eventPartDao.create(eventPart);
	
		if(result) 
		{
			return "Success";
		}
		else
		{
			return "Fail";
		}
	}
}
