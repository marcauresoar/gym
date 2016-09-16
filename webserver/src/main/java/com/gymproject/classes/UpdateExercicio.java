package com.gymproject.classes;

import com.gymproject.model.Exercicio;

public class UpdateExercicio {
	private String acao;
	private String mid;
	private Exercicio exercicio;
	public UpdateExercicio(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Exercicio getExercicio() {
		return exercicio;
	}
	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
