package com.gymproject.services;

import java.util.List;

import com.gymproject.model.Ficha;

public interface FichaServices {
	public List<Ficha> listarFichas(String id) throws Exception;
	boolean insert(Ficha ficha) throws Exception;
}
