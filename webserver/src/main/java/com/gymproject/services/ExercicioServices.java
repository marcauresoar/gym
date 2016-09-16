package com.gymproject.services;

import java.util.List;

import com.gymproject.model.Exercicio;

public interface ExercicioServices {
	public List<Exercicio> listar(String id) throws Exception;
	boolean save(Exercicio dados) throws Exception;
	boolean delete(String id) throws Exception;
}
