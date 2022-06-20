package br.unitins.farmacia.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;


import br.unitins.farmacia.application.Util;
import br.unitins.farmacia.dao.RemedioDAO;
import br.unitins.farmacia.model.Perfil;
import br.unitins.farmacia.model.Remedio;
import br.unitins.farmacia.model.Tipo;


@Named
@ApplicationScoped
public class FarmaciaController implements Serializable {

	private static final long serialVersionUID = 8269752858637589975L;
	private Remedio farmacia;
	
	public FarmaciaController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("farmaciaFlash");
		setFarmacia((Remedio)flash.get("farmaciaFlash"));
	} 

	
	public void voltar() {
		Util.redirect("farmacia1.xhtml");
	}
	
	public void voltar1() {
		Util.redirect("usuario2.xhtml");
	}
	
	public Tipo[] getListaTipo() {
		return Tipo.values();
	}
	public void incluir() {
		RemedioDAO dao = new RemedioDAO();
		if (!dao.insert(getFarmacia())) {
			Util.addMessageInfo("Erro ao tentar incluir o remedio.");
			return;
		}
		
		limpar();
		Util.addMessageInfo("Inclusão realizada com sucesso.");
	}

	public void alterar() {
		RemedioDAO dao = new RemedioDAO();
		if (!dao.update(getFarmacia())) {
			Util.addMessageInfo("Erro ao tentar alterar o remedio.");
			return;
		}
		limpar();
		Util.addMessageInfo("Alteração realizada com sucesso.");
	}
	
	
	public void excluir() {
		RemedioDAO dao = new RemedioDAO();
		if (!dao.delete(getFarmacia().getId())) {
			Util.addMessageInfo("Erro ao tentar excluir o remedio.");
			return;
		}
		Util.addMessageInfo("Exclusão realizada com sucesso.");
		limpar();
	}

	public void limpar() {
		farmacia = null;
	}
	


	public void editar(int id) {
		RemedioDAO dao = new RemedioDAO();
		setFarmacia(dao.getById(id));
		Util.redirect("farmacia2.xhtml");

	}
	
	public Remedio getFarmacia() {
		if (farmacia == null) {
			farmacia = new Remedio();
	}
		return farmacia;
	}

	public void setFarmacia(Remedio farmacia) {
		this.farmacia = farmacia;
	}

}
