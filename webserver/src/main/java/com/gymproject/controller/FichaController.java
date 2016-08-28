package com.gymproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gymproject.classes.Status;
import com.gymproject.model.Ficha;
import com.gymproject.services.FichaServices;

@Controller
@RequestMapping("/ficha")
public class FichaController {
	
	@Autowired
	FichaServices fichaServices;
	
	@RequestMapping(value = "/listar/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Status getEmployee(@PathVariable("id") String id) {
		try {
			List<Ficha> fichas = fichaServices.listarFichas(id);
			return new Status(1, "Fichas listadas com sucesso!", fichas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Status(0, "Houve um erro ao tentar realizar esta ação!", null);
	}
	
}
