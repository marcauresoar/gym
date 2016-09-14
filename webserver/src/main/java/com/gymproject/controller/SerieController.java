package com.gymproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gymproject.classes.Status;
import com.gymproject.classes.UpdateSerie;
import com.gymproject.model.Serie;
import com.gymproject.services.SerieServices;

@Controller
@RequestMapping("/serie")
public class SerieController {

	@Autowired
	SerieServices serieServices;

	@RequestMapping(value = "/listar/{id}", method = RequestMethod.GET)
	public @ResponseBody Status listar(@PathVariable("id") String id) {
		try {
			List<Serie> dados = serieServices.listar(id);
			return new Status(1, "Series listadas com sucesso!", dados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Status(0, "Houve um erro ao tentar realizar esta ação!", null);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status update(@RequestBody List<UpdateSerie> updates) {

		if (updates.size() > 0) {
			int erros = 0, sucessos = 0;

			for(UpdateSerie update : updates){

				try {
					if (update.getAcao().equals("insert") || update.getAcao().equals("update")) {
						if(update.getAcao().equals("insert")){
							update.getSerie().setId(null);
						}
						serieServices.save(update.getSerie());
						sucessos++;
					}
					if (update.getAcao().equals("delete")) {
						serieServices.delete(update.getMid());
						sucessos++;
					}

				} catch (Exception e) {
					e.printStackTrace();
					erros++;
				}

			}
			return new Status(1, "Updates aplicados! Acertos: " + sucessos + ", Erros: " + erros, null);
		}

		return new Status(1, "Nenhum update a ser realizado!", null);
	}

}
