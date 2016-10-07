package com.gymproject.classes;

import com.gymproject.model.ExercicioTreino;

public class UpdateExercicioTreino {
	private String acao;
	private String mid;
	private ExercicioTreino exercicio_treino;
	public UpdateExercicioTreino(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}

	public ExercicioTreino getExercicio_treino() {
		return exercicio_treino;
	}
	public void setExercicio_treino(ExercicioTreino exercicio_treino) {
		this.exercicio_treino = exercicio_treino;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
