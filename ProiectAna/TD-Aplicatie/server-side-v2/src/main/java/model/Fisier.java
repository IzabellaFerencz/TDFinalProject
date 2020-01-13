package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fisier")
public class Fisier {
	@Id
	@Column(name = "idFisier")
	private int idFisier;

	@Column(name = "numeF")
	private String numeF;

	@Column(name = "path")
	private String path;

	@Column(name = "content")
	private String content;

	@Column(name = "tip")
	private String tip;
}
