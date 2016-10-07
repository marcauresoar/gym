package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.ExercicioTreino;

public interface ExercicioTreinoDao {
	public List<ExercicioTreino> listar(String id) throws Exception;
	boolean save(ExercicioTreino dados) throws Exception;
	boolean delete(String id) throws Exception;
}
