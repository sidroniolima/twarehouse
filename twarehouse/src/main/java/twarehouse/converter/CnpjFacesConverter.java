/**
 * 
 */
package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import twarehouse.model.Cnpj;

/**
 * Conversor Faces para a classe Cnpj.
 * 
 * @author Sidronio
 * 04/11/2015
 */
@FacesConverter("cnpjFacesConverter")
public class CnpjFacesConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (null == value || value.isEmpty()) {
			return null;
		}
		
		return new Cnpj(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null == value) {
			return "";
		}
		
		return ((Cnpj) value).toString();
	}

}
