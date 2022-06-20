package br.unitins.farmacia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.unitins.farmacia.dao.AutorDAO;
import br.unitins.farmacia.model.Autor;

@FacesConverter(forClass = Autor.class)
public class AutorConverter implements Converter<Autor> {

	@Override
	public Autor getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.isBlank())
			return null;
		AutorDAO dao = new AutorDAO();
		return dao.getById(Integer.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Autor autor) {
		if (autor == null || autor.getId() == null)
			return null;
		return autor.getId().toString();
	}

}
