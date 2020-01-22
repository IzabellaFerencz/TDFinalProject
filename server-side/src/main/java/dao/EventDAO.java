package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Event;
import model.User;
import model.Userroles;

public class EventDAO extends BasicDAO<Event>
{
	public EventDAO(Class<Event> eClass) 
	{
		super(eClass);
	}
	
	public List<Event> findByOrganizer(User organizer) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<Event> root = cq.from(Event.class);
			cq.select(root);
			cq.where(cb.equal(root.get("user"), organizer));
			List<Event> returnValues = em.createQuery(cq).getResultList();
			em.close();
			return returnValues;
		} 
		catch (RuntimeException e) 
		{
			em.close();
			return null;
		} 

	}
	
}
