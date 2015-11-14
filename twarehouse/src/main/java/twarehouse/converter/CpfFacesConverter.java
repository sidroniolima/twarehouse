/**
 * 
 */
package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import twarehouse.model.Cpf;

/**
 * Conversor Faces para a classe Cpf.
 * 
 * @author Sidronio
 * 04/11/2015
 */
@FacesConverter("cpfFacesConverter")
public class CpfFacesConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (null == value || value.isEmpty()) {
			return null;
		}
		
		return new Cpf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null == value) {
			return "";
		}
		
		return ((Cpf) value).toString();
	}

}
