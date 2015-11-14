package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import twarehouse.model.Permissao;

@FacesConverter("permissaoConverter")
public class PermissaoConveter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.equals("")) {
			
			return Permissao.valueOf(value);
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null != value) {
			
			String descricaoDaEnum = value.toString();
			
			return (descricaoDaEnum == null ? "" : descricaoDaEnum);
		}
		
		return "";
		
	}

}
