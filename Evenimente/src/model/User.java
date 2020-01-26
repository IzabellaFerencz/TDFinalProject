package model;

import java.io.Serializable;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iduser;
	private String username;
	private static  User user;
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}


	public User(int id)
	{
		iduser = id;
	}

	public int getIdUser()
	{
		return iduser;
	}

	public void setIdUser(int userId)
	{
		this.iduser = userId;
	}

	public static User getUser()
	{
		return user;
	}

	public static void setUser(User user)
	{
		User.user = user;
	}
}
