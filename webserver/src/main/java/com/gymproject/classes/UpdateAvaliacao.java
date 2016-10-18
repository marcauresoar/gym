package com.gymproject.classes;

import com.gymproject.model.Avaliacao;

public class UpdateAvaliacao {
	private String acao;
	private String mid;
	private Avaliacao avaliacao;
	public UpdateAvaliacao(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Avaliacao getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
