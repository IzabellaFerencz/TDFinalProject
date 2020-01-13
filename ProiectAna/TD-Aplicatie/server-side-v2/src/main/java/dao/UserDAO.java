package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.User;

public class UserDAO extends BasicDAO<User> {

	private EntityManagerFactory factory;

	public UserDAO(EntityManagerFactory factory) {
		super(User.class);
		this.factory = factory;
	}

	@Override
	public EntityManager getEntityManager() {
		try {
			return factory.createEntityManager();
		} catch (Exception ex) {
			System.out.println("The entity manager cannot be created!");
			return null;

		}

	}
}
