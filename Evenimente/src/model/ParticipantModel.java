package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParticipantModel
{
	private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty status;
    
    public ParticipantModel(int id, String name,  String status)
    {
    	this.id = new SimpleIntegerProperty(id);
    	this.name = new SimpleStringProperty(name);
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

	public String getName()
	{
		return name.get();
	}
	public void setName(String name)
	{
		this.name = new SimpleStringProperty(name);
	}

}
