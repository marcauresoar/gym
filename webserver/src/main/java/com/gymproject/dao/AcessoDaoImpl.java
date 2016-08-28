package com.gymproject.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.Usuario;
import com.gymproject.utils.HashUtils;

public class AcessoDaoImpl implements AcessoDao {

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> autenticarLogin(String email, String senha) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		List<Usuario> userList = session.createCriteria(Usuario.class).add(Restrictions.eq("email", email))
				.add(Restrictions.eq("senha", HashUtils.md5(senha))).list();
		tx.commit();
		session.close();
		return userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean emailDisponivel(String email) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		List<Usuario> users = session.createCriteria(Usuario.class).add(Restrictions.eq("email", email)).list();
		tx.commit();
		session.close();
		return users.size() > 0 && users.get(0) != null ? false : true;
	}

	@Override
	public Usuario inserirUsuario(String nome, String email, String senha) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(HashUtils.md5(senha));
		session.save(usuario);
		tx.commit();
		session.close();
		return usuario;
	}

}
