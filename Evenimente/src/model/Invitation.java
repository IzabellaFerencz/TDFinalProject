package model;


public class Invitation
{
	private int idInvitation;

	private boolean isAccepted;

	private String helpFile;
	
	private String secretCode;

	private EventParticipant eventParticipant;
	
	private static Invitation invitation;

	public int getIdInvitation()
	{
		return idInvitation;
	}

	public static Invitation getInvitation()
	{
		return invitation;
	}

	public static void setInvitation(Invitation invitation)
	{
		Invitation.invitation = invitation;
	}

	public void setIdInvitation(int idInvitation)
	{
		this.idInvitation = idInvitation;
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

	public EventParticipant getEventParticipant()
	{
		return eventParticipant;
	}

	public void setEventParticipant(EventParticipant eventParticipant)
	{
		this.eventParticipant = eventParticipant;
	}
	
}
