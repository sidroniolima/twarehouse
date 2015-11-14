/**
 * 
 */
package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import twarehouse.dao.SubgrupoDAO;
import twarehouse.model.Subgrupo;

/**
 * @author Sidronio
 *
 */
@FacesConverter(forClass=Subgrupo.class)
public class SubrupoConverter implements Converter {

	@Inject
	private SubgrupoDAO subgrupoDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (null == value || value.isEmpty()) {
			return null;
		}
		
		return subgrupoDAO.buscarPeloCodigo(Long.parseLong(value.toString()));
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null != value) {
			
			Long codigo =  ((Subgrupo) value).getCodigo(); 
			
			return (codigo == null ? "" : codigo.toString());
		}
		
		return "";
	}

}
