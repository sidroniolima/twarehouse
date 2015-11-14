/**
 * 
 */
package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import twarehouse.dao.UnidadeDAO;
import twarehouse.model.Unidade;

/**
 * Converter para a Entidade Unidade.
 * 
 * @author Sidronio
 * 22/10/2015
 */
@FacesConverter(forClass=Unidade.class)
public class UnidadeConverter implements Converter {

	@Inject
	private UnidadeDAO unidadeDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (null == value || value.isEmpty()) {
			return null;
		}
		
		return unidadeDAO.buscarPeloCodigo(Long.parseLong(value.toString()));
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null != value) {
			
			Long codigo =  ((Unidade) value).getCodigo(); 
			
			return (codigo == null ? "" : codigo.toString());
		}
		
		return "";
	}

}
