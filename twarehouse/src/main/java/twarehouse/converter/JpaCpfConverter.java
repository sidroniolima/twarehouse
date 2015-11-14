/**
 * 
 */
package twarehouse.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import twarehouse.model.Cpf;

/**
 * @author Sidronio
 *
 */
@Converter(autoApply=true)
public class JpaCpfConverter implements AttributeConverter<Cpf, String>{

	
	@Override
	public String convertToDatabaseColumn(Cpf cpf) {
		return cpf.getValor();
	}

	@Override
	public Cpf convertToEntityAttribute(String stringDoBanco) {
		return new Cpf(stringDoBanco);
	}

}
