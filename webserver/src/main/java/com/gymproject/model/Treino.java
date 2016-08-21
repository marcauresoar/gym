package com.gymproject.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "treino")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Treino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "data", nullable = false)
	private Date data;

	@Column(name = "hora_inicio", nullable = false)
	private Time hora_inicio;

	@Column(name = "hora_fim", nullable = true)
	private Time hora_fim;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ficha")
	private Set<ExercicioTreino> exercicios = new HashSet<ExercicioTreino>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(Time hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public Time getHora_fim() {
		return hora_fim;
	}

	public void setHora_fim(Time hora_fim) {
		this.hora_fim = hora_fim;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<ExercicioTreino> getExercicios() {
		return exercicios;
	}

	public void setExercicios(Set<ExercicioTreino> exercicios) {
		this.exercicios = exercicios;
	}

}
