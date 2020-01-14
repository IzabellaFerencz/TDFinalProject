package dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.User;

public class UserDAO extends BasicDAO<User> {

	public UserDAO(Class<User> eClass) 
	{
		super(eClass);
	}

	public User findByUsernameAndPassword(String username, String password) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<User> root = cq.from(User.class);
			cq.select(root);
			cq.where(cb.equal(root.get("username"), username), cb.equal(root.get("password"),password));
			User returnValues = (User) em.createQuery(cq).getSingleResult();
			em.close();
			return returnValues;
		} 
		catch (RuntimeException e) 
		{
			//em.getTransaction().rollback();
			em.close();
			return null;
		} 

	}
}
