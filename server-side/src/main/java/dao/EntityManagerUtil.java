package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil 
{
	private static final String PERSISTENCE_UNIT_NAME = "clientServer";
	private static EntityManagerFactory factory;

	public static EntityManagerFactory getEntityManagerFactory() 
	{
		if (factory == null) 
		{
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return factory;
	}

	public static void shutdown() 
	{
		if (factory != null) 
		{
			factory.close();
		}
	}
}
