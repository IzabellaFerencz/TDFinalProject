package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.User;
import model.Userroles;

public class UserRolesDAO extends BasicDAO<Userroles> 
{
	public UserRolesDAO(Class<Userroles> eClass) 
	{
		super(eClass);
	}
	
	public List<Userroles> findRolesOfUser(User user) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<Userroles> root = cq.from(Userroles.class);
			cq.select(root);
			cq.where(cb.equal(root.get("user"), user));
			List<Userroles> returnValues = em.createQuery(cq).getResultList();
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
