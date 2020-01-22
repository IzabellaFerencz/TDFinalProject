package dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Event;
import model.EventParticipant;
import model.User;

public class EventParticipantDAO extends BasicDAO<EventParticipant>
{
	public EventParticipantDAO(Class<EventParticipant> eClass) 
	{
		super(eClass);
	}
	
	public EventParticipant findByEventAndParticipant(User participant, Event event) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<EventParticipant> root = cq.from(EventParticipant.class);
			cq.select(root);
			cq.where(cb.equal(root.get("participant"), participant), cb.equal(root.get("event"),event));
			EventParticipant returnValues = (EventParticipant) em.createQuery(cq).getSingleResult();
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
