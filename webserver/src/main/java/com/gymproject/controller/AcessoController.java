package com.gymproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gymproject.classes.Status;
import com.gymproject.model.Usuario;
import com.gymproject.services.AcessoServices;


@Controller
@RequestMapping("/acesso")
public class AcessoController {
	
	@Autowired
	AcessoServices acessoServices;
	
	@RequestMapping(value = "/autenticarLogin", method = RequestMethod.POST)
	public @ResponseBody 
	Status autenticarLogin(@RequestParam("email") String email, 
			@RequestParam("senha") String senha) {

		List<Usuario> usuarios = null;
		try {
			usuarios = acessoServices.autenticarLogin(email, senha);
			if(usuarios.size() > 0 && usuarios.get(0) != null) {
				return new Status(1, "Login autenticado com sucesso!", usuarios.get(0));
			} else {
				return new Status(0, "Dados inválidos!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Status(0, "Houve um erro ao tentar realizar essa ação!", null);
	}
	
}
