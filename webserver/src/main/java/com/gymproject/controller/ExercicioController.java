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
import com.gymproject.classes.UpdateExercicio;
import com.gymproject.model.Exercicio;
import com.gymproject.services.ExercicioServices;

@Controller
@RequestMapping("/exercicio")
public class ExercicioController {

	@Autowired
	ExercicioServices exercicioServices;

	@RequestMapping(value = "/listar/{id}", method = RequestMethod.GET)
	public @ResponseBody Status listar(@PathVariable("id") String id) {
		try {
			List<Exercicio> dados = exercicioServices.listar(id);
			return new Status(1, "Exercicios listados com sucesso!", dados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Status(0, "Houve um erro ao tentar realizar esta ação!", null);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status update(@RequestBody List<UpdateExercicio> updates) {

		if (updates.size() > 0) {
			int erros = 0, sucessos = 0;

			for(UpdateExercicio update : updates){

				try {
					if (update.getAcao().equals("insert") || update.getAcao().equals("update")) {
						if(update.getAcao().equals("insert")){
							update.getExercicio().setId(null);
						}
						exercicioServices.save(update.getExercicio());
						sucessos++;
					}
					if (update.getAcao().equals("delete")) {
						exercicioServices.delete(update.getMid());
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
