package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EventModel
{
	private SimpleIntegerProperty id;
	private SimpleIntegerProperty nrOfSeats;
    private SimpleStringProperty name;
    private SimpleStringProperty location;
    private SimpleStringProperty datetime;
    private SimpleStringProperty organizer;
    private SimpleStringProperty status;
    
    public EventModel(int id, String name, String location, String date, int seats, String organizer, String status)
    {
    	this.id = new SimpleIntegerProperty(id);
    	this.name = new SimpleStringProperty(name);
    	this.location = new SimpleStringProperty(location);
    	this.datetime = new SimpleStringProperty(date);
    	this.nrOfSeats = new SimpleIntegerProperty(seats);
    	this.organizer = new SimpleStringProperty(organizer);
    	this.status = new SimpleStringProperty(status);
    }
    
	public String getStatus()
	{
		return status.get();
	}

	public void setStatus(String status)
	{
		this.status = new SimpleStringProperty(status);
	}

	public int getId()
	{
		return id.get();
	}

	public void setId(int id)
	{
		this.id = new SimpleIntegerProperty(id);
	}

	public int getNrOfSeats()
	{
		return nrOfSeats.get();
	}
	public void setNrOfSeats(int nrOfSeats)
	{
		this.nrOfSeats = new SimpleIntegerProperty(nrOfSeats);
	}

	public String getName()
	{
		return name.get();
	}
	public void setName(String name)
	{
		this.name = new SimpleStringProperty(name);
	}
	public String getLocation()
	{
		return location.get();
	}
	public void setLocation(String location)
	{
		this.location = new SimpleStringProperty(location);
	}
	public String getDatetime()
	{
		return datetime.get();
	}
	public void setDatetime(String datetime)
	{
		this.datetime = new SimpleStringProperty(datetime);
	}
	public String getOrganizer()
	{
		return organizer.get();
	}
	public void setOrganizer(String organizer)
	{
		this.organizer = new SimpleStringProperty(organizer);
	}
}
