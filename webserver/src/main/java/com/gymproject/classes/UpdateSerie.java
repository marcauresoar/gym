package com.gymproject.classes;

import com.gymproject.model.Serie;

public class UpdateSerie {
	private String acao;
	private String mid;
	private Serie serie;
	public UpdateSerie(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
