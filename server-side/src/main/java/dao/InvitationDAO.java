package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.EventParticipant;
import model.Invitation;
import model.User;
import model.Userroles;

public class InvitationDAO extends BasicDAO
{
	public InvitationDAO(Class<Invitation> eClass) 
	{
		super(eClass);
	}

	public Invitation getInviteForEventParticipant(EventParticipant participant)
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<Invitation> root = cq.from(Invitation.class);
			cq.select(root);
			cq.where(cb.equal(root.get("eventParticipant"), participant));
			Invitation returnValues = (Invitation) em.createQuery(cq).getSingleResult();
			em.close();
			return returnValues;
		} 
		catch (Exception e) 
		{
			em.close();
			return null;
		} 
	}

	public List<Invitation> getInvitesOfUser(User participant)
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<Invitation> root = cq.from(Invitation.class);
			cq.select(root);
			cq.where(cb.equal(root.get("eventParticipant").get("participant"), participant));
			List<Invitation> returnValues = em.createQuery(cq).getResultList();
			em.close();
			return returnValues;
		} 
		catch (Exception e) 
		{
			em.close();
			return null;
		} 
	}

	public boolean reserveInvitation(Invitation invitation)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
