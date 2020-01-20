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

	/**
	 * This method is used for the user change events availability action. Receives
	 * the new user data.
	 * 
	 * @param receivedData
	 * @return no if there is already an account on that username, yes if the
	 *         registration is a success.
	 */
//	public String changeAvailabiliy(String receivedData) {
//		User user = gson.fromJson(receivedData, User.class);
//		userDao = new UserDAO(User.class);
//		String response = null;
//		userDao.updateAvailability(user.getUserId(), user.getUsername());
//		return response;
//
//	}

	/**
	 * This method is used to bring all the logged user future events for witch he
	 * accepted to go.
	 * 
	 * @param username
	 * @return
	 */
//	public String getUserFutureEvents(String receivedData) {
//		String response = null;
//		User user = gson.fromJson(receivedData, User.class);
//		eventDao = new EventDAO(Event.class);
//		if (eventDao.getEventsId(user.getUserId()).size() > 0) {
//			response = gson.toJson(eventDao.getEventsId(user.getUserId()));
//			System.out.println("Am adus din baza de date EVENIMENTELE");
//			System.out.println(eventDao.getEventsId(user.getUserId()).toString());
//		} else
//			response = "";
//		return response;
//	}
}
