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
	
	@Column(name = "grupos_musculares", unique = true, nullable = false, length = 256)
	private String grupos_musculares;
	
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

	public String getGrupos_musculares() {
		return grupos_musculares;
	}

	public void setGrupos_musculares(String grupos_musculares) {
		this.grupos_musculares = grupos_musculares;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}
	
}
