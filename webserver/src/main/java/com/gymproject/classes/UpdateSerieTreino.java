package com.gymproject.classes;

import com.gymproject.model.SerieTreino;

public class UpdateSerieTreino {
	private String acao;
	private String mid;
	private SerieTreino serie_treino;
	public UpdateSerieTreino(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public SerieTreino getSerie_treino() {
		return serie_treino;
	}
	public void setSerie_treino(SerieTreino serie_treino) {
		this.serie_treino = serie_treino;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
