package br.unitins.farmacia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.farmacia.application.Session;
import br.unitins.farmacia.application.Util;
import br.unitins.farmacia.dao.RemedioDAO;
import br.unitins.farmacia.model.ItemVenda;
import br.unitins.farmacia.model.Remedio;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = -637345764345173797L;
	private Integer tipoFiltro;
	private String filtro;
	private List<Remedio> listaFarmacia;
	
	public void pesquisar() {
		RemedioDAO dao = new RemedioDAO();
		setListaFarmacia(dao.getByNome(filtro));
	}

	public void comprar(Remedio farmacia) {
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		
		ItemVenda item = new ItemVenda();
		item.setFarmacia(farmacia);
		item.setValor(farmacia.getValor());
		item.setQuantidade(1);
		
		// verificando se contem o remedio no carrinho para alterar a quantidade
		if (carrinho.contains(item)) {
			int index = carrinho.indexOf(item);
			int quantidade = carrinho.get(index).getQuantidade();
			carrinho.get(index).setQuantidade(quantidade + 1);
			carrinho.get(index).setValor(farmacia.getValor());
		} else {
			carrinho.add(item);
		}
		
		
		// cria/ atualiza o objeto na sessao
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addMessageInfo("Remedio adicionado na sessão");
		
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
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
