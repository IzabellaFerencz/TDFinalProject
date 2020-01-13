package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole
{
	@Id
    @Column(name = "roleId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	private int roleId;
	
	@Id
    @Column(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private int userId;

	public int getRoleId()
	{
		return roleId;
	}

	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}
}
