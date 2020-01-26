package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NotificationModel
{
	private SimpleIntegerProperty id;
    private SimpleStringProperty message;
    private SimpleStringProperty date;
    
	public NotificationModel(int id, String message, String date)
	{
		this.id = new SimpleIntegerProperty(id);
		this.message = new SimpleStringProperty(message);
		this.date = new SimpleStringProperty(date);
	}

	public int getId()
	{
		return id.get();
	}

	public void setId(int id)
	{
		this.id = new SimpleIntegerProperty(id);
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String message)
	{
		this.message = new SimpleStringProperty(message);
	}

	public String getDate()
	{
		return date.get();
	}

	public void setDate(String date)
	{
		this.date = new SimpleStringProperty(date);
	}
	
}
