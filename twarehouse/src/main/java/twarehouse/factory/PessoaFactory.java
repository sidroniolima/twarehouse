/**
 * 
 */
package twarehouse.factory;

import twarehouse.model.Pessoa;
import twarehouse.model.PessoaFisica;
import twarehouse.model.PessoaJuridica;
import twarehouse.model.TipoPessoa;

/**
 * Padrão Factory para criação de Pessoa, física ou jurídica.
 * 
 * @author Sidronio
 * 04/11/2015
 * 
 */
public class PessoaFactory {

	public static Pessoa criaPessoa(TipoPessoa tipo) {

		if (tipo.equals(TipoPessoa.FISICA)) {
			return new PessoaFisica();
		}
		
		if (tipo.equals(TipoPessoa.JURIDICA)) {
			return new PessoaJuridica();
		}
		
		return null;
	}
	
}
