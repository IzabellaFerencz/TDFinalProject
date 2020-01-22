package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userroles")
public class Userroles
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userRoleId;
	
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	public int getUserRoleId()
	{
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId)
	{
		this.userRoleId = userRoleId;
	}
	
	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	
}
