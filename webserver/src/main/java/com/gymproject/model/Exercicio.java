package com.gymproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "exercicio")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Exercicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "nome", nullable = false, length = 64)
	private String nome;
	
	@Column(name = "grupo_muscular", unique = true, nullable = false, length = 32)
	private String grupo_muscular;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ficha_id", nullable = false)
	private Ficha ficha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getGrupo_muscular() {
		return grupo_muscular;
	}

	public void setGrupo_muscular(String grupo_muscular) {
		this.grupo_muscular = grupo_muscular;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}
	
	public Exercicio() {
		super();
	}
}
