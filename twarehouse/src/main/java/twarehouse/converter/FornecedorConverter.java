/**
 * 
 */
package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import twarehouse.model.Fornecedor;
import twarehouse.service.impl.FornecedorService;

/**
 * Faces Converter para a Entidade Fornecedor.
 * 
 * @author Sidronio
 * 04/11/2015
 */
@FacesConverter(forClass=Fornecedor.class)
public class FornecedorConverter implements Converter {

	@Inject
	private FornecedorService fornecedorService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (null == value || value.isEmpty()) {
			return null;
		}
		
		return fornecedorService.buscaPeloCodigo(Long.parseLong(value.toString()));
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null != value) {
			
			Long codigo =  ((Fornecedor) value).getCodigo(); 
			
			return (codigo == null ? "" : codigo.toString());
		}
		
		return "";
	}

}
