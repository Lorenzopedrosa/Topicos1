package br.unitins.farmacia.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import br.unitins.farmacia.model.Usuario;

public class Remedio implements Cloneable {
	
	private Tipo tipo;
	
	private Integer id;

	@NotNull(message = "O nome não pode ser nulo.")
	private String nome;
	
	@NotNull(message = "O valor não pode ser nulo.")
	private Double valor;


	public Usuario getClone() {
		try {
			return (Usuario) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Remedio other = (Remedio) obj;
		return Objects.equals(id, other.id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	

}
