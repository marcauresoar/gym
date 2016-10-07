package com.gymproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gymproject.classes.Status;
import com.gymproject.model.Usuario;
import com.gymproject.services.AcessoServices;
import com.gymproject.utils.ValidateUtils;


@Controller
@RequestMapping("/acesso")
public class AcessoController {
	
	@Autowired
	AcessoServices acessoServices;
	
	@RequestMapping(value = "/autenticarLogin", method = RequestMethod.POST)
	public @ResponseBody 
	Status autenticarLogin(@RequestParam("email") String email, 
			@RequestParam("senha") String senha) {
		
		if(email.isEmpty()) {
			return new Status(0, "O campo Email precisa ser preenchido!", null);
		}
		
		if(!ValidateUtils.isEmailValid(email)){
			return new Status(0, "O campo Email contêm um valor inválido!", null);
		}
		
		if(senha.isEmpty()) {
			return new Status(0, "O campo Senha precisa ser preenchido!", null);
		}
		
		

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
	
	@RequestMapping(value = "/criarConta", method = RequestMethod.POST)
	public @ResponseBody 
	Status criarConta(@RequestParam("nome") String nome, @RequestParam("email") String email, 
			@RequestParam("senha") String senha) {
		
		if(nome.isEmpty()) {
			return new Status(0, "O campo Nome precisa ser preenchido!", null);
		}
		
		if(email.isEmpty()) {
			return new Status(0, "O campo Email precisa ser preenchido!", null);
		}
		
		if(!ValidateUtils.isEmailValid(email)){
			return new Status(0, "O campo Email contêm um valor inválido!", null);
		}
		
		if(senha.isEmpty()) {
			return new Status(0, "O campo Senha precisa ser preenchido!", null);
		}
		
		try {
			if(acessoServices.emailDisponivel(email)) {
				Usuario usuario = acessoServices.inserirUsuario(nome, email, senha);
				return new Status(1, "Conta criada com sucesso!", usuario);
			} else {
				return new Status(0, "Já existe um usuário utilizando este email!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Status(0, "Houve um erro ao tentar realizar essa ação!", null);
	}
	
	
}
