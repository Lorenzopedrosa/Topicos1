package br.unitins.farmacia.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.farmacia.model.Venda;

@Named
@ViewScoped
public class DetalheVendaController implements Serializable {
	
	private static final long serialVersionUID = -48994004880373855L;
	private Venda venda;
	
	public DetalheVendaController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		//mantem o objeto no flashscoped enquanto não renovar a requisição
		flash.keep("vendaFlash");
		setVenda((Venda)flash.get("vendaFlash"));
	}

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	

}