package br.unitins.farmacia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.farmacia.application.Session;
import br.unitins.farmacia.application.Util;
import br.unitins.farmacia.dao.VendaDAO;
import br.unitins.farmacia.model.Usuario;
import br.unitins.farmacia.model.Venda;

@Named
@ViewScoped
public class HistoricoController implements Serializable {
	
	private static final long serialVersionUID = 7781080328315608995L;
	private List<Venda> listaVenda;
	private Double valorTotal; 
	
	public HistoricoController() {
		
	}
	
	public List<Venda> getListaVenda() {
		Usuario usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuarioLogado == null) {
			listaVenda = new ArrayList<Venda>();
		} else {
			if (listaVenda == null) {
				VendaDAO dao = new VendaDAO();
				listaVenda = dao.getByUsuario(usuarioLogado);
				if (listaVenda == null)
					listaVenda = new ArrayList<Venda>();
			}
		}
		return listaVenda;
	}
	
	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
	public void detalhes(Venda venda) {
		VendaDAO dao = new VendaDAO();
		
		@SuppressWarnings("unused")
		Venda vendaCompleta = dao.getByVenda(venda);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		
		flash.put("vendaFlash", venda);
		
		Util.redirect("detalhevenda.xhtml");
		
	}
	
	
}
