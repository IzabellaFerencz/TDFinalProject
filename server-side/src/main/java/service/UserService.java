package service;

import com.google.gson.Gson;

import dao.UserDAO;
import model.User;

public class UserService
{
	private Gson gson;
	private UserDAO userDao;

	public UserService()
	{
		super();
		this.gson = new Gson();
		this.userDao = new UserDAO(User.class);
	}

	/**
	 * This method is used for the user login action. Receives the credentials
	 * introduced by the user and checks them in the database.
	 * 
	 * @param receivedData -user credentials
	 * @return a user object if the user exists in bd , else returns null object.
	 */
	public String login(String receivedData) 
	{
		String response = null;
		User user = gson.fromJson(receivedData, User.class);
		User loggedUser = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if (loggedUser != null)
		{
			response = gson.toJson(loggedUser);
		}
		else
		{
			response = "Fail";
		}

		return response;
	}

	/**
	 * This method is used for the user register action. Receives the new user data.
	 * 
	 * @param receivedData
	 * @return no if there is already an account on that username, yes if the
	 *         registration is a success.
	 */
	public String register(String receivedData)
	{
		String response = null;

		User user = gson.fromJson(receivedData, User.class);
		userDao = new UserDAO(User.class);
		
		boolean result = userDao.create(user);
		if(result == true)
		{
			response = login(receivedData);
		}
		else
		{
			response = "Fail";
		}
		
		return response;

	}

	public String findUserByUsername(String receivedData)
	{
		String response = null;
		User user = gson.fromJson(receivedData, User.class);
		User loggedUser = userDao.findByUsername(user.getUsername());
		
		if (loggedUser != null)
		{
			response = gson.toJson(loggedUser);
		}
		else
		{
			response = "Fail";
		}

		return response;
	}


}
