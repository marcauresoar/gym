package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.ExercicioDao;
import com.gymproject.model.Exercicio;

public class ExercicioServicesImpl implements ExercicioServices {

	@Autowired
	ExercicioDao exercicioDao;
	
	@Override
	public List<Exercicio> listar(String id) throws Exception {
		return exercicioDao.listar(id);
	}

	@Override
	public boolean save(Exercicio dados) throws Exception {
		return exercicioDao.save(dados);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return exercicioDao.delete(id);
	}

}
