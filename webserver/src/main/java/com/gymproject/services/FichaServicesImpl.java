package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.FichaDao;
import com.gymproject.model.Ficha;

public class FichaServicesImpl implements FichaServices {

	@Autowired
	FichaDao fichaDao;
	
	@Override
	public List<Ficha> listarFichas(String id) throws Exception {
		return fichaDao.listarFichas(id);
	}

	@Override
	public boolean save(Ficha ficha) throws Exception {
		return fichaDao.save(ficha);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return fichaDao.delete(id);
	}

}
