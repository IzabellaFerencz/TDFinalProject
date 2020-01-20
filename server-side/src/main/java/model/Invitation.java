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
@Table(name = "invitation")
public class Invitation {
	@Id
	@Column(name = "idInvitation")
	private int idInvitation;

	@ManyToOne
	@JoinColumn(name = "idEvent")
	private Event idEvent;

	@ManyToOne
	@JoinColumn(name = "idParticipant")
	private User idParticipant;

	@Column(name = "isAccepted")
	private boolean isAccepted;
	
	@Column(name = "helpFile")
	private Object helpFile;
	
	@Column(name = "secretCode")
	private String secretCode;

	public int getIdInvitation()
	{
		return idInvitation;
	}

	public void setIdInvitation(int idInvitation)
	{
		this.idInvitation = idInvitation;
	}

	public Event getIdEvent()
	{
		return idEvent;
	}

	public void setIdEvent(Event idEvent)
	{
		this.idEvent = idEvent;
	}

	public User getIdParticipant()
	{
		return idParticipant;
	}

	public void setIdParticipant(User idParticipant)
	{
		this.idParticipant = idParticipant;
	}

	public boolean isAccepted()
	{
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted)
	{
		this.isAccepted = isAccepted;
	}

	public Object getHelpFile()
	{
		return helpFile;
	}

	public void setHelpFile(Object helpFile)
	{
		this.helpFile = helpFile;
	}

	public String getSecretCode()
	{
		return secretCode;
	}

	public void setSecretCode(String secretCode)
	{
		this.secretCode = secretCode;
	}

}
