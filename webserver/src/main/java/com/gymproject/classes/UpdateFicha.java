package com.gymproject.classes;

import com.gymproject.model.Ficha;

public class UpdateFicha {
	private String acao;
	private String mid;
	private Ficha ficha;
	public UpdateFicha(){
		
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Ficha getFicha() {
		return ficha;
	}
	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}	
}
