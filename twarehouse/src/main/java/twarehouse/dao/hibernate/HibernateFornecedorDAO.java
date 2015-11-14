/**
 * 
 */
package twarehouse.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import twarehouse.dao.FornecedorDAO;
import twarehouse.model.Documento;
import twarehouse.model.Entrada;
import twarehouse.model.Fornecedor;

/**
 * Implementação para Hibernate da Camada DAO da Entidade Fornecedor.
 * 
 * @author Sidronio
 * 04/11/2015
 */
@Stateless
public class HibernateFornecedorDAO extends HibernateGenericDAO<Fornecedor, Long> implements FornecedorDAO, Serializable {

	private static final long serialVersionUID = -3175975171461962720L;

	@Override
	public Fornecedor buscarPeloDocumentoDeIdentificacao(Documento documento) {
		
		Criteria criteria = getSession().createCriteria(Fornecedor.class);
		criteria.setFetchMode("pessoa", FetchMode.JOIN);
		
		criteria.add(
				Restrictions.or(
						Restrictions.eq("pessoa.cpf", documento), 
						Restrictions.eq("pessoa.cnpj", documento)));
		
		return (Fornecedor) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entrada> listarEntradasDoFornecedor(Fornecedor fornecedor) {
		
		Criteria criteria = getSession().createCriteria(Entrada.class);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		criteria.createAlias("documento", "documento");
		
		criteria.add(Restrictions.eq("fornecedor", fornecedor));
		
		criteria.addOrder(Order.asc("documento.data"));
		
		return criteria.list();
	}

	/**
	 * Faz um filtro nos registros de Fornecedores pelo nome (nome fantaisa ou nome), 
	 * documento (cpf ou cnpj) ou por um telefone.
	 * 
	 * @param filtro Instância de Fornecedor contendo os valores do filtro.
	 * @return List<Fornecedor> Lista de fornecedores que obdeceram o filtro.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Fornecedor> filtrarFornecedorPeloNomeDocumentoOuTelefone(
			String nome, String documento, String numeroDeTelefone) {
		
		Criteria criteria = getSession().createCriteria(Fornecedor.class);
		
		criteria.setFetchMode("pessoa", FetchMode.JOIN);
		criteria.setFetchMode("telefones", FetchMode.JOIN);
		
		criteria.createAlias("pessoa", "pessoa");
		criteria.createAlias("telefones", "telefones");
		
		if (null != documento && !documento.isEmpty()) {
			
			criteria.add(
					Restrictions.or(Restrictions.eq("pessoa.cpf", documento),
									Restrictions.eq("pessoa.cnpj", documento)));
		}
		
		if (null != nome && !nome.isEmpty()) {
			
			criteria.add(
					Restrictions.or(Restrictions.like("pessoa.nome", nome, MatchMode.ANYWHERE),
									Restrictions.like("pessoa.nomeFantasia", nome, MatchMode.ANYWHERE)));
		}

		if (null != numeroDeTelefone && !numeroDeTelefone.isEmpty()) {
			
			criteria.add(Restrictions.like("telefones.numero", numeroDeTelefone, MatchMode.ANYWHERE));
		}
		
		return criteria.list();
	}

}
