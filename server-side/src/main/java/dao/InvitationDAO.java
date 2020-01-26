package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import model.Event;
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
	
	public List<Invitation> getInvitesOEvent(Event event)
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<Invitation> root = cq.from(Invitation.class);
			cq.select(root);
			cq.where(cb.equal(root.get("eventParticipant").get("event"), event));
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
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();

		synchronized(this)
		{
			Event event = invitation.getEventParticipant().getEvent();
			List<Invitation> invitesForEvent = getInvitesOEvent(event);
			int nrOfReservedSeats = 0;
			for (Invitation inv : invitesForEvent)
			{
				if(inv.isAccepted())
				{
					nrOfReservedSeats++;
				}
			}
			if(nrOfReservedSeats < event.getNrOfSeats())
			{
				try
				{
					CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
					CriteriaUpdate<Invitation> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Invitation.class);
					Root<Invitation> invRoot = criteriaUpdate.from(Invitation.class);
					
					criteriaUpdate.set(invRoot.get("isAccepted"), true)
									.where(criteriaBuilder.equal(invRoot.get("idInvitation"), invitation.getIdInvitation()));
					
					em.getTransaction().begin();
					em.createQuery(criteriaUpdate).executeUpdate();
					em.getTransaction().commit();
					return true;
					
				} catch (Exception e)
				{
					// TODO: handle exception
				}

			}
		}
		return false;
	}
	
}
