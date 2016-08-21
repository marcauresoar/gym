package com.gymproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "serie_treino")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SerieTreino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Enumerated
	@Column(name = "tipo", nullable = false, length = 64)
	private String tipo;
	
	@Column(name = "repeticoes", nullable = true)
	private Integer repeticoes;
	
	@Column(name = "peso", nullable = true)
	private Integer peso;
	
	@Column(name = "tempo", nullable = true)
	private Integer tempo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exercicio_treino_id", nullable = false)
	private ExercicioTreino exercicio_treino;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(Integer repeticoes) {
		this.repeticoes = repeticoes;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public ExercicioTreino getExercicio() {
		return exercicio_treino;
	}

	public void setExercicio(ExercicioTreino exercicio) {
		this.exercicio_treino = exercicio;
	}
	
}
