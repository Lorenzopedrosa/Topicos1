package br.unitins.farmacia.model;

public enum Tipo {
	
	SOLIDO(1, "Sólido"), 
	LIQUIDO(2, "Líquido"), 
	COMESTICO(3, "Cosmético"), 
	COMPRIMIDO(4, "Comprimido");
	
	private int id;
	private String label;
	
	Tipo(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Tipo valueOf(int id) {
		for (Tipo tipo : Tipo.values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}

}
