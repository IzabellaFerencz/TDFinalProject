package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participant")
public class Participant {

	@Id
	@Column(name = "idParticipant")
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
	private int idParticipant;

	@Column(name = "isAvailable")
	private boolean isAvailable;

}
