package com.gymproject.services;

import java.util.List;

import com.gymproject.model.SerieTreino;

public interface SerieTreinoServices {
	public List<SerieTreino> listar(String id) throws Exception;
	boolean save(SerieTreino dados) throws Exception;
	boolean delete(String id) throws Exception;
}
