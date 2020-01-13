package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "eveniment_participant")
public class EvenimentParticipant {
	@Id
	@Column(name = "idEveniment_participant")
	private int idEvenPart;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "idEveniment")
	private List<Eveniment> evenimente = new ArrayList<Eveniment>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "idParticipant")
	private List<Participant> participanti = new ArrayList<Participant>();
}
