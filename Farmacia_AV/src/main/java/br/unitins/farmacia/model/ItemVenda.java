package br.unitins.farmacia.model;

import java.util.Objects;

public class ItemVenda {

	private Integer id;
	private String nome;
	private Double valor;
	private Remedio farmacia;
	private Integer quantidade;
	
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Remedio getFarmacia() {
		return farmacia;
	}

	public void setFarmacia(Remedio farmacia) {
		this.farmacia = farmacia;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(farmacia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVenda other = (ItemVenda) obj;
		return Objects.equals(farmacia, other.farmacia);
	}

	
	
	

}
