/**
 * 
 */
package twarehouse.factory;

import twarehouse.model.CupomFiscal;
import twarehouse.model.DocumentoEntrada;
import twarehouse.model.EntradaAVulso;
import twarehouse.model.NotaFiscalEletronica;
import twarehouse.model.TipoDocumentoEntrada;

/**
 * Fábrica de documentos de entrada.
 * 
 * @author Sidronio
 * 26/11/2015
 */
public class DocumentoEntradaFactory {

	public static DocumentoEntrada cria(TipoDocumentoEntrada tipo) {
		
		if (tipo.equals(TipoDocumentoEntrada.AVULSO)) {
			return new EntradaAVulso();
		}
		
		if (tipo.equals(TipoDocumentoEntrada.NFE)) {
			return new NotaFiscalEletronica();
		}
		
		if (tipo.equals(TipoDocumentoEntrada.CUPOM_FISCAL)) {
			return new CupomFiscal();
		}
		
		return null;
	}
}
