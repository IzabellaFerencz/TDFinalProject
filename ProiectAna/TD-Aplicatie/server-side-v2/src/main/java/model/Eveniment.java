package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eveniment")
public class Eveniment {

	@Id
	@Column(name = "idEveniment")
	private int idEveniment;

	@Column(name = "denumire")
	private String denumire;

	@Column(name = "locatie")
	private String locatie;

	@Column(name = "ora")
	private String ora;

	@Column(name = "data")
	private String data;

	@Column(name = "locuri")
	private int nrLocuri;

	@Column(name = "nrInvitatii")
	private int nrInvitatii;
}
