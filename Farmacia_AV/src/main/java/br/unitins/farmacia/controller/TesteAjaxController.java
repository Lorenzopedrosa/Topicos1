package br.unitins.farmacia.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class TesteAjaxController implements Serializable {

	private static final long serialVersionUID = -8814401002996558828L;
	
	private String nome;
	
	public void imprimir() {
		System.out.println(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	
}
