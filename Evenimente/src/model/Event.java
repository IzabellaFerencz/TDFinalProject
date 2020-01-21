package model;

public class Event 
{
	private int idEvent;

	private String name;

	private String location;

	private String datetime;

	private int nrOfSeats;

	private int nrOfInvites;

	private User user;

	public int getIdEvent()
	{
		return idEvent;
	}

	public void setIdEvent(int idEvent)
	{
		this.idEvent = idEvent;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getDatetime()
	{
		return datetime;
	}

	public void setDatetime(String datetime)
	{
		this.datetime = datetime;
	}

	public int getNrOfSeats()
	{
		return nrOfSeats;
	}

	public void setNrOfSeats(int nrOfSeats)
	{
		this.nrOfSeats = nrOfSeats;
	}

	public int getNrOfInvites()
	{
		return nrOfInvites;
	}

	public void setNrOfInvites(int nrOfInvites)
	{
		this.nrOfInvites = nrOfInvites;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}


}
