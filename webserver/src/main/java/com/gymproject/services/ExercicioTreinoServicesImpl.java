package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.ExercicioTreinoDao;
import com.gymproject.model.ExercicioTreino;

public class ExercicioTreinoServicesImpl implements ExercicioTreinoServices {

	@Autowired
	ExercicioTreinoDao exercicioDao;
	
	@Override
	public List<ExercicioTreino> listar(String id) throws Exception {
		return exercicioDao.listar(id);
	}

	@Override
	public boolean save(ExercicioTreino dados) throws Exception {
		return exercicioDao.save(dados);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return exercicioDao.delete(id);
	}

}
