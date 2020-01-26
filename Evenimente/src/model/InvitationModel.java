package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InvitationModel
{
	private SimpleIntegerProperty id;
    private SimpleStringProperty eventName;
    private SimpleStringProperty file;
    private SimpleStringProperty secretCode;
    private SimpleStringProperty status;
    
	public InvitationModel(int idInvitation, String eventName, String helpFile, String secretCode, String status)
	{
		this.id = new SimpleIntegerProperty(idInvitation);
		this.eventName = new SimpleStringProperty(eventName);
		this.file = new SimpleStringProperty(helpFile);
		this.secretCode = new SimpleStringProperty(secretCode);
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
	public String getEventName()
	{
		return eventName.get();
	}
	public void setEventName(String eventName)
	{
		this.eventName = new SimpleStringProperty(eventName);
	}
	public String getFile()
	{
		return file.get();
	}
	public void setFile(String file)
	{
		this.file = new SimpleStringProperty(file);
	}
	public String getSecretCode()
	{
		return secretCode.get();
	}
	public void setSecretCode(String secretCode)
	{
		this.secretCode = new SimpleStringProperty(secretCode);
	}
	public String getStatus()
	{
		return status.get();
	}
	public void setStatus(String status)
	{
		this.status = new SimpleStringProperty(status);
	}
    

}
