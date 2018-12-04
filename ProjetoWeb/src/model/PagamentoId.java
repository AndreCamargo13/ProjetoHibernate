package model;

// Generated 29/11/2018 20:14:38 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PagamentoId generated by hbm2java
 */
@Embeddable
public class PagamentoId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long cpf;
	private long codcurso;

	public PagamentoId() {
	}

	public PagamentoId(long cpf, long codcurso) {
		this.cpf = cpf;
		this.codcurso = codcurso;
	}

	@Column(name = "CPF", nullable = false, precision = 22, scale = 0)
	public long getCpf() {
		return this.cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	@Column(name = "CODCURSO", nullable = false, precision = 22, scale = 0)
	public long getCodcurso() {
		return this.codcurso;
	}

	public void setCodcurso(long codcurso) {
		this.codcurso = codcurso;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PagamentoId))
			return false;
		@SuppressWarnings("unused")
		PagamentoId castOther = (PagamentoId) other;

		return true;
	}

	public int hashCode() {
		int result = 17;

		return result;
	}

}