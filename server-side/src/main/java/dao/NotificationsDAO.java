package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Event;
import model.Invitation;
import model.Notification;
import model.User;

public class NotificationsDAO extends BasicDAO<Notification>
{
	public NotificationsDAO(Class<Notification> eClass) 
	{
		super(eClass);
	}
	
	public List<Notification> getNotificationsForUser(User user)
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<Notification> root = cq.from(Notification.class);
			cq.select(root);
			cq.where(cb.equal(root.get("user"), user));
			List<Notification> returnValues = em.createQuery(cq).getResultList();
			em.close();
			return returnValues;
		} 
		catch (Exception e) 
		{
			em.close();
			return null;
		} 
	}
}
