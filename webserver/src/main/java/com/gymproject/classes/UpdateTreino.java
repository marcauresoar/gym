package com.gymproject.classes;

import com.gymproject.model.Treino;

public class UpdateTreino {
	private String acao;
	private String mid;
	private Treino treino;
	public UpdateTreino(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Treino getTreino() {
		return treino;
	}
	public void setTreino(Treino treino) {
		this.treino = treino;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
