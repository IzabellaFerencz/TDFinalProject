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
@Table(name = "event_participant")
public class EventParticipant {

	@Id
	@Column(name = "idevent_participant")
	private int idEventParticipant;
	
	@ManyToOne
	@JoinColumn(name = "idParticipant")
	private User idParticipant;
	
	@ManyToOne
	@JoinColumn(name = "idEvent")
	private Event idEvent;

	public int getIdEventParticipant()
	{
		return idEventParticipant;
	}

	public void setIdEventParticipant(int idEventParticipant)
	{
		this.idEventParticipant = idEventParticipant;
	}

	public User getIdParticipant()
	{
		return idParticipant;
	}

	public void setIdParticipant(User idParticipant)
	{
		this.idParticipant = idParticipant;
	}

	public Event getIdEvent()
	{
		return idEvent;
	}

	public void setIdEvent(Event idEvent)
	{
		this.idEvent = idEvent;
	}

	public boolean isAvailable()
	{
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	@Column(name = "isAvailable")
	private boolean isAvailable;

}
