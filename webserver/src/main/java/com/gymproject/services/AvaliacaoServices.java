package com.gymproject.services;

import java.util.List;

import com.gymproject.model.Avaliacao;

public interface AvaliacaoServices {
	public List<Avaliacao> listar(String id) throws Exception;
	boolean save(Avaliacao dados) throws Exception;
	boolean delete(String id) throws Exception;
}
