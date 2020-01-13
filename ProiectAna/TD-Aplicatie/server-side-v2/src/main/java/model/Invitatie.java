package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invitatie")
public class Invitatie {
	@Id
	@Column(name = "idInvitatie")
	private int idInvitatie;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEveniment")
	private int idEveniment;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idParticipant")
	private int idParticipant;

	@Column(name = "dataCreare")
	private String dataCreare;
	
	

	public Invitatie(int idInvitatie, int idEveniment, int idParticipant, String dataCreare) {
		super();
		this.idInvitatie = idInvitatie;
		this.idEveniment = idEveniment;
		this.idParticipant = idParticipant;
		this.dataCreare = dataCreare;
	}

	public int getIdInvitatie() {
		return idInvitatie;
	}

	public void setIdInvitatie(int idInvitatie) {
		this.idInvitatie = idInvitatie;
	}

	public int getIdEveniment() {
		return idEveniment;
	}

	public void setIdEveniment(int idEveniment) {
		this.idEveniment = idEveniment;
	}

	public int getIdParticipant() {
		return idParticipant;
	}

	public void setIdParticipant(int idParticipant) {
		this.idParticipant = idParticipant;
	}

	public String getDataCreare() {
		return dataCreare;
	}

	public void setDataCreare(String dataCreare) {
		this.dataCreare = dataCreare;
	}
	

}
