package service;

import java.util.List;

import com.google.gson.Gson;

import dao.UserRolesDAO;
import model.User;
import model.Userroles;

public class UserRolesService
{
	private Gson gson;
	private UserRolesDAO userRolesDao;

	public UserRolesService()
	{
		super();
		this.gson = new Gson();
		this.userRolesDao = new UserRolesDAO(Userroles.class);
	}
	
	public String getRolesOfUser(String receivedData) 
	{
		String response = null;
		User user = gson.fromJson(receivedData, User.class);
		List<Userroles> roles = userRolesDao.findRolesOfUser(user);
		
		if (roles != null)
		{
			response = gson.toJson(roles);
		}
		else
		{
			response = "Fail";
		}

		return response;
	}
}
