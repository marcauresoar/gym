package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.Avaliacao;

public interface AvaliacaoDao {
	public List<Avaliacao> listar(String id) throws Exception;
	boolean save(Avaliacao dados) throws Exception;
	boolean delete(String id) throws Exception;
}
