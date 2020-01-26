package model;

public class EventParticipant
{
	private int idEventParticipant;
	private User participant;
	private Event event;
	private static EventParticipant eventParticipant;

	public static EventParticipant getEventParticipant()
	{
		return eventParticipant;
	}

	public static void setEventParticipant(EventParticipant eventParticipant)
	{
		EventParticipant.eventParticipant = eventParticipant;
	}

	public int getIdEventParticipant()
	{
		return idEventParticipant;
	}

	public void setIdEventParticipant(int idEventParticipant)
	{
		this.idEventParticipant = idEventParticipant;
	}

	public User getParticipant()
	{
		return participant;
	}

	public void setParticipant(User participant)
	{
		this.participant = participant;
	}

	public Event getEvent()
	{
		return event;
	}

	public void setEvent(Event event)
	{
		this.event = event;
	}

	
}
