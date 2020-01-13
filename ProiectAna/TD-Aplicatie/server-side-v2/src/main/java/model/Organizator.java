package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "organizator")
public class Organizator {

	@Id
    @Column(name = "idOrganizator")
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
	private int idOrganizator;
 
    @Column(name = "idEveniment")
	private int idEveniment;

}
