package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event_participant")
public class EventParticipant {

	@Id
	@Column(name = "idevent_participant")
	private int idEventParticipant;
	
	@ManyToOne
	@JoinColumn(name = "idParticipant")
	private User participant;
	
	@ManyToOne
	@JoinColumn(name = "idEvent")
	private Event event;

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
