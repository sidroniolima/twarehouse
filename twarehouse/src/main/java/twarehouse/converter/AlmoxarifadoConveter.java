package twarehouse.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import twarehouse.dao.AlmoxarifadoDAO;
import twarehouse.molde.estoque.Almoxarifado;

@FacesConverter(forClass=Almoxarifado.class)
public class AlmoxarifadoConveter implements Converter {

	@Inject
	private AlmoxarifadoDAO almoxarifadoDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.equals("")) {
			return almoxarifadoDAO.buscarPeloCodigo(new Long(value));
		}
		
		return new Almoxarifado();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null != value) {
			
			Long codigo =  ((Almoxarifado) value).getCodigo(); 
			
			return (codigo == null ? "" : codigo.toString());
		}
		
		return "";
		
	}

}
