package service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.EventDAO;
import model.Event;
import model.User;

public class EventsService
{
	private Gson gson;
	private EventDAO eventDao;

	public EventsService()
	{
		super();
		this.gson = new Gson();
		this.eventDao = new EventDAO(Event.class);
	}

	public String getAllEvents()
	{
		String results = null;
		List<Event> list = new ArrayList<Event>();
		list=eventDao.findAll();
	
		if (list != null)
		{
			results = gson.toJson(list);
			System.out.println("EVENTS:");
			System.out.println(list.toString());
		} 
		else
		{
			results = "Fail";
		}
		
		return results;
	}
	
	public String newEvent(String receivedData)
	{
		Event event = gson.fromJson(receivedData, Event.class);
		boolean result = eventDao.create(event);
	
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
