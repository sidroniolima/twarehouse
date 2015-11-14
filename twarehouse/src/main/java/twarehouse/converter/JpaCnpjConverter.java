/**
 * 
 */
package twarehouse.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import twarehouse.model.Cnpj;

/**
 * @author Sidronio
 *
 */
@Converter
public class JpaCnpjConverter implements AttributeConverter<Cnpj, String>{

	@Override
	public String convertToDatabaseColumn(Cnpj cnpj) {
		return cnpj.getValor();
	}

	@Override
	public Cnpj convertToEntityAttribute(String stringDoBanco) {
		return new Cnpj(stringDoBanco);
	}

}
