package br.unitins.farmacia.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

import br.unitins.farmacia.application.Util;
import br.unitins.farmacia.dao.RemedioDAO;
import br.unitins.farmacia.model.Remedio;

@Named
@ApplicationScoped
public class Farmacia1Controller implements Serializable {

	private static final long serialVersionUID = 1258970559285652391L;

	private String filtro;
	private List<Remedio> listaFarmacia;

	public void editar(int id) {
		RemedioDAO dao = new RemedioDAO();
		Remedio farmacia = dao.getById(id);
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("farmaciaFlash", farmacia);
		
		Util.redirect("farmacia2.xhtml");
	}
	
	public void novo() {
		Util.redirect("farmacia2.xhtml");
	}
	
	public void pesquisar() {
		RemedioDAO dao = new RemedioDAO();
		setListaFarmacia(dao.getByNome(getFiltro()));
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Remedio> getListaFarmacia() {
		return listaFarmacia;
	}

	public void setListaFarmacia(List<Remedio> listaFarmacia) {
		this.listaFarmacia = listaFarmacia;
	}

}
