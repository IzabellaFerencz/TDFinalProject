package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.Participant;

public class ParticipantDAO extends BasicDAO<Participant> {

	private EntityManagerFactory factory;

	public ParticipantDAO(EntityManagerFactory factory) {
		super(Participant.class);
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
