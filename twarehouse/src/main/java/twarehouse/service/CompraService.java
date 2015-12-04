/**
 * 
 */
package twarehouse.service;

import java.util.List;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Compra;
import twarehouse.model.consulta.FiltroEntrada;
import twarehouse.util.Paginator;

/**
 * Interface da camada Service do padrãpo MVC para 
 * a entidade Compra.
 * 
 * @author Sidronio
 * 26/11/2105
 */
public interface CompraService extends SimpleServiceLayer<Compra, Long>{

	/**
	 * Busca um registro de compra pelo código com os 
	 * relacionamentos de Fornecedor, Itens e Documento.
	 * 
	 * @param paramCodigo Código da compra.
	 * @return Compra localizada.
	 */
	public Compra buscaPeloCodigoCompleta(Long paramCodigo);

	/**
	 * Filtra os registros da entrada pelo filtro na 
	 * pesquisa.
	 * 
	 * @param filtro Filtro específico da pesquisa.
	 * @return Registros localizados de acordo com o filtro.
	 */
	public List<Compra> filtraPelaPesquisa(FiltroEntrada filtro, Paginator paginator);

	/**
	 * Verifica se é uma edição ou não de uma entidade compra.
	 * 
	 * @param entidade Compra para verificaão.
	 * @return True se for edição, false se contrário.
	 */
	public boolean isEdicao(Compra entidade);
	
	/**
	 * Busca uma compra pelo código e retorna-a com seus itens.
	 * 
	 * @param codigo Código da compra.
	 * @return Compra com itens.
	 */
	public Compra buscaPeloCodigoComItens(Long codigo);
	
}
