package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invitation")
public class Invitation 
{
	@Id
	@Column(name = "idInvitation")
	private int idInvitation;

	@Column(name = "isAccepted")
	private boolean isAccepted;

	@Column(name = "helpFile")
	private String helpFile;
	
	@Column(name = "secretCode")
	private String secretCode;
	
	@ManyToOne
	@JoinColumn(name = "idParticipant")
	private EventParticipant eventParticipant;

	public int getIdInvitation()
	{
		return idInvitation;
	}

	public void setIdInvitation(int idInvitation)
	{
		this.idInvitation = idInvitation;
	}

	public EventParticipant getEventParticipant()
	{
		return eventParticipant;
	}

	public void setEventParticipant(EventParticipant eventParticipant)
	{
		this.eventParticipant = eventParticipant;
	}

	public boolean isAccepted()
	{
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted)
	{
		this.isAccepted = isAccepted;
	}

	public String getHelpFile()
	{
		return helpFile;
	}

	public void setHelpFile(String helpFile)
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
