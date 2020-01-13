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
	
	@Id
	@Column(name = "idParticipant")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private int idParticipant;
	
	@Id
	@Column(name = "idEvent")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEvent")
	private int idEvent;

	@Column(name = "isAvailable")
	private boolean isAvailable;

}
