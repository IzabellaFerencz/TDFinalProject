package dao;

import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;

public abstract class BasicDAO<T> 
{
	private Class<T> entityClass;

	public BasicDAO(Class<T> eClass) 
	{
		this.entityClass = eClass;
	}


	public boolean create(T entity) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return true;
		} 
		catch (RuntimeException e) 
		{
			//em.getTransaction().rollback();
			return false;
		} 
		finally 
		{
			em.close();
		}
	}

	public void update(T entity) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} 
		catch (RuntimeException e) 
		{
			em.getTransaction().rollback();

		} 
		finally 
		{
			em.close();
		}
	}

	public void remove(T entity, int entityId) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try
		{
			em.getTransaction().begin();
			em.remove((T) em.find(this.entityClass, entityId));
			em.getTransaction().commit();
		} 
		catch (RuntimeException e) 
		{
			em.getTransaction().rollback();
		}
		finally 
		{
			em.close();
		}
	}

	public T find(int id) 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			T ret = (T) em.find(this.entityClass, id);
			return ret;
		} 
		catch (RuntimeException e) 
		{
			em.getTransaction().rollback();

		} 
		finally 
		{
			em.close();
		}
		return null;
	}

	public List<T> findAll() 
	{
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		try 
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(entityClass));
			List<T> returnValues = em.createQuery(cq).getResultList();
			return returnValues;
		}
		catch (RuntimeException e) 
		{
			em.getTransaction().rollback();

		} 
		finally 
		{
			em.close();
		}
		return null;
	}
}
