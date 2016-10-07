package com.gymproject.services;

import java.util.List;

import com.gymproject.model.Treino;

public interface TreinoServices {
	public List<Treino> listar(String id) throws Exception;
	boolean save(Treino dados) throws Exception;
	boolean delete(String id) throws Exception;
}
