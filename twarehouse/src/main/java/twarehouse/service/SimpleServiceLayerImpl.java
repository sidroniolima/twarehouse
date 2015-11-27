package twarehouse.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import twarehouse.dao.GenericDAO;
import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.util.Paginator;

/**
 * Implementação da interface SimpleServiceLayer.
 * 
 * @author Sidronio
 *
 * @param <T>
 * @param <ID>
 */
public abstract class SimpleServiceLayerImpl<T, ID extends Serializable> implements SimpleServiceLayer<T, ID> {

	private Class<T> classeDaEntidade;
	
	@SuppressWarnings("unchecked")
	public SimpleServiceLayerImpl() {
		
		classeDaEntidade = (Class<T>) 
				( (ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void salva(T entidade) throws RegraDeNegocioException {
		this.validaInsercao(entidade);
		
		getDAO().salvar(entidade);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void exclui(ID id) throws RegraDeNegocioException {
		
		this.validaExclusao((T) getDAO().buscarPeloCodigo(id));
		
		getDAO().excluir(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T buscaPeloCodigo(ID id) {
		return (T) getDAO().buscarPeloCodigo(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscaTodas() {
		return getDAO().filtrar(classeDaEntidade, null, null, null, null);
	}

	@SuppressWarnings("rawtypes")
	public abstract GenericDAO getDAO();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listaComPaginacao(
			Paginator paginator, 
			List<String> ordenacao, 
			List<String> relacionamentos, 
			List<String> aliases) {
		
		return getDAO().listarComPaginacao(
				paginator.getFirstResult(), 
				paginator.getQtdPorPagina(), 
				ordenacao, 
				relacionamentos, 
				aliases);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> lista() {
		return getDAO().filtrar(classeDaEntidade, null, null, null, null);
	}
}
