package br.unitins.farmacia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.unitins.farmacia.application.Util;
import br.unitins.farmacia.dao.UsuarioDAO;
import br.unitins.farmacia.model.Perfil;
import br.unitins.farmacia.model.Usuario;

@Named
@ApplicationScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1258970559285652391L;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	
	public void incluir() {
		UsuarioDAO dao = new UsuarioDAO();
		// gerando o hash da senha
		String senha = getUsuario().getLogin() + getUsuario().getSenha();
		senha = Util.hash(senha);
		getUsuario().setSenha(senha);
		
		if (!dao.insert(getUsuario())) {
			Util.addMessageInfo("Erro ao tentar incluir o usuário.");
			return;
		}
		limpar();
		setListaUsuario(null);
		Util.addMessageInfo("Inclusão realizada com sucesso.");
	}

	public void alterar() {
		UsuarioDAO dao = new UsuarioDAO();
		
		// gerando o hash da senha
		String senha = getUsuario().getLogin() + getUsuario().getSenha();
		senha = Util.hash(senha);
		getUsuario().setSenha(senha);
		
		if (!dao.update(getUsuario())) {
			Util.addMessageInfo("Erro ao tentar alterar o usuário.");
			return;
		}
		limpar();
		setListaUsuario(null);
		Util.addMessageInfo("Alteração realizada com sucesso.");
	}
	
	public void novo() {
		Util.redirect("usuario.xhtml");
	}
	
	public void novo2() {
		Util.redirect("usuario3.xhtml");
	}

	public void excluir() {
		excluir(getUsuario().getId());
		limpar();
	}

	public void excluir(int id) {
		UsuarioDAO dao = new UsuarioDAO();
		if (!dao.delete(id)) {
			Util.addMessageInfo("Erro ao tentar excluir o usuário.");
			return;
		}
		setListaUsuario(null);
		Util.addMessageInfo("Exclusão realizada com sucesso.");
	}

	public void editar(int id) {
		UsuarioDAO dao = new UsuarioDAO();
		setUsuario(dao.getById(id));
		Util.redirect("usuario.xhtml");

	}

	public void limpar() {
		usuario = null;
	}
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.getAll();
			if (listaUsuario == null) 
				listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

}
