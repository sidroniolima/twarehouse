package twarehouse.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {
	
	public void salvar(T entidade);
	public void excluir(ID id);
	public T buscarPeloCodigo(ID id);
	public List<T> filtrar(T entidade, List<String> propriedades, List<String> relacionamentos);
	
	public List<T> listarComPaginacao(int firstResult, int numberPerPage, 
			List<String> ordenacao, List<String> relacionamentos, List<String> aliases);

}
