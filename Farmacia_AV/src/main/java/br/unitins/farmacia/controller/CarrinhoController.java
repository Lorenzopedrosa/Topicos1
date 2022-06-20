package br.unitins.farmacia.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.farmacia.application.Session;
import br.unitins.farmacia.application.Util;
import br.unitins.farmacia.dao.RemedioDAO;
import br.unitins.farmacia.dao.VendaDAO;
import br.unitins.farmacia.model.ItemVenda;
import br.unitins.farmacia.model.Usuario;
import br.unitins.farmacia.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = 8749169417126959084L;
	
	private List<ItemVenda> listaItemVenda = null;
	private Double valorTotal;
	
	@SuppressWarnings("unchecked")
	public CarrinhoController() {
		listaItemVenda = (List<ItemVenda>) Session.getInstance().get("carrinho");
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}
	
	public void finalizar() {
		Usuario usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuarioLogado == null) {
			Util.addMessageError("Faça o Login para finalizar a compra.");
			return;
		}
		if (getListaItemVenda() == null || getListaItemVenda().isEmpty()) {
			Util.addMessageError("Selecione um produto antes de finalizar uma compra.");
			return;
		}
		Venda venda = new Venda();
		venda.setUsuario(usuarioLogado);
		venda.setDataVenda(LocalDateTime.now( ));
		venda.setListaItemVenda(getListaItemVenda());
		
		VendaDAO dao = new VendaDAO();
		dao.insert(venda);
		
		Util.addMessageInfo("Compra realizada com sucesso.");
	}
	
	public void remover(ItemVenda venda) {
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		carrinho.remove(venda);
	}
	
	private Double valorTotalVenda(List<ItemVenda> lista) {
		Double aux = 0.0;
		if (lista != null) {
			for (ItemVenda itemVenda : lista) {
				aux = aux + itemVenda.getValor();
			}
			return aux;
		}
		return null;
	}

	public Double getValorTotal() {
		if (valorTotal == null)
			valorTotal = valorTotalVenda(listaItemVenda);

		if (valorTotal == null)
			valorTotal = 0.0;

		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}