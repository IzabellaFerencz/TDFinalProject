package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

	@Id
	@Column(name = "idEvent")
	private int idEvent;

	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;

	@Column(name = "datetime")
	private String datetime;

	@Column(name = "nrofseats")
	private int nrOfSeats;

	@Column(name = "nrofinvites")
	private int nrOfInvites;

	@Column(name = "idOrganizer")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iduser")
	private int idOrganizer;

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

	public int getIdOrganizer()
	{
		return idOrganizer;
	}

	public void setIdOrganizer(int idOrganizer)
	{
		this.idOrganizer = idOrganizer;
	}

	@Override
	public String toString()
	{
		return "Event [idEvent=" + idEvent + ", name=" + name + ", location=" + location + ", datetime=" + datetime
				+ ", nrOfSeats=" + nrOfSeats + ", nrOfInvites=" + nrOfInvites + ", idOrganizer=" + idOrganizer + "]";
	}
}
