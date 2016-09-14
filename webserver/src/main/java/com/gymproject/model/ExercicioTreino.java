package com.gymproject.model;

import java.io.Serializable;
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
@Table(name = "exercicio_treino")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ExercicioTreino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "nome", nullable = false, length = 64)
	private String nome;
	
	@Column(name = "grupo_muscular", unique = true, nullable = false, length = 32)
	private String grupo_muscular;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "treino_id", nullable = false)
	private Treino treino;

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

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}
	
}
