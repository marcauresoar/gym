package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.Ficha;

public interface FichaDao {
	public List<Ficha> listarFichas(String id) throws Exception;
}
