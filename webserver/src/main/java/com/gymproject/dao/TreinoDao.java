package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.Treino;

public interface TreinoDao {
	public List<Treino> listar(String id) throws Exception;
	boolean save(Treino dados, String acao) throws Exception;
	boolean delete(String id) throws Exception;
}
