package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EventModel
{
	private SimpleIntegerProperty id;
	private SimpleIntegerProperty nrOfSeats;
	private SimpleIntegerProperty nrOfInvites;
    private SimpleStringProperty name;
    private SimpleStringProperty location;
    private SimpleStringProperty datetime;
    private SimpleStringProperty organizer;
    
    public EventModel(int id, String name, String location, String date, int seats, int invites, String organizer)
    {
    	this.id = new SimpleIntegerProperty(id);
    	this.name = new SimpleStringProperty(name);
    	this.location = new SimpleStringProperty(location);
    	this.datetime = new SimpleStringProperty(date);
    	this.nrOfInvites = new SimpleIntegerProperty(invites);
    	this.nrOfSeats = new SimpleIntegerProperty(seats);
    	this.organizer = new SimpleStringProperty(organizer);
    }
    
	public SimpleIntegerProperty getId()
	{
		return id;
	}

	public void setId(SimpleIntegerProperty id)
	{
		this.id = id;
	}

	public SimpleIntegerProperty getNrOfSeats()
	{
		return nrOfSeats;
	}
	public void setNrOfSeats(SimpleIntegerProperty nrOfSeats)
	{
		this.nrOfSeats = nrOfSeats;
	}
	public SimpleIntegerProperty getNrOfInvites()
	{
		return nrOfInvites;
	}
	public void setNrOfInvites(SimpleIntegerProperty nrOfInvites)
	{
		this.nrOfInvites = nrOfInvites;
	}
	public SimpleStringProperty getName()
	{
		return name;
	}
	public void setName(SimpleStringProperty name)
	{
		this.name = name;
	}
	public SimpleStringProperty getLocation()
	{
		return location;
	}
	public void setLocation(SimpleStringProperty location)
	{
		this.location = location;
	}
	public SimpleStringProperty getDatetime()
	{
		return datetime;
	}
	public void setDatetime(SimpleStringProperty datetime)
	{
		this.datetime = datetime;
	}
	public SimpleStringProperty getOrganizer()
	{
		return organizer;
	}
	public void setOrganizer(SimpleStringProperty organizer)
	{
		this.organizer = organizer;
	}
}
