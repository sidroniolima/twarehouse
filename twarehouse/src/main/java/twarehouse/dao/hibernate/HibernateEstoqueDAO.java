package twarehouse.dao.hibernate;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import twarehouse.dao.EstoqueDAO;
import twarehouse.model.Produto;
import twarehouse.model.consulta.SaldoEstoque;
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.molde.estoque.Movimento;
import twarehouse.molde.estoque.TipoMovimento;

/**
 * Implementação da camada DAO para a classe Movimento que compõem o 
 * Estoque de produtos.
 * 
 * @author Sidronio
 * 01/11/2015
 */
@Stateless
public class HibernateEstoqueDAO extends HibernateGenericDAO<Movimento, Long> implements EstoqueDAO{

	private final String sqlSaldoEstoque =     
			"SELECT" 
	        +"	a.codigo AS 'codigoAlmoxarifado'," 
	        +"	a.descricao AS 'descricaoAlmoxarifado'," 
	        
			+" COALESCE(" 
			+"	(SELECT" 
			+"		SUM(qtd) as 'ENTRADA'"  
			+"	FROM" 
			+"		movimento mENTRADA"   
			+"	WHERE" 
			+"		mENTRADA.tipo_movimento = 'ENTRADA'" 
			+"		AND  mENTRADA.produto_codigo = :produto_codigo"	
			+"		AND  mENTRADA.almoxarifado_codigo = a.codigo)," 
			+"	0.00) -" 
	        +" COALESCE(" 
			+"	(SELECT" 
			+"		SUM(qtd) as 'SAIDA'" 
			+"	FROM" 
			+"		movimento mSAIDA"  
			+"	WHERE" 
			+"		mSAIDA.tipo_movimento = 'SAIDA'" 
			+"		AND  mSAIDA.produto_codigo = :produto_codigo" 
			+"		AND  mSAIDA.almoxarifado_codigo = a.codigo),"
			+"	0.00) AS 'saldo'"        
	       
		    +" FROM" 
			+"	almoxarifado a" 
		        
		    +" GROUP BY" 
		    +"    a.codigo";

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> movimentosDeEntradaDoProdutoPorAlmoxarifado(Produto produto, Almoxarifado almoxarifado) {
		
		Criteria criteria = this.getSession().createCriteria(Movimento.class);
		criteria.add(Restrictions.eq("produto", produto));
		criteria.add(Restrictions.eq("almoxarifado", almoxarifado));
		criteria.add(Restrictions.eq("tipoMovimento", TipoMovimento.ENTRADA));
		
		criteria.addOrder(Order.asc("horario"));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> movimentosDeSaidaDoProdutoPorAlmoxarifado(Produto produto, Almoxarifado almoxarifado) {
		Criteria criteria = this.getSession().createCriteria(Movimento.class);
		criteria.add(Restrictions.eq("produto", produto));
		criteria.add(Restrictions.eq("almoxarifado", almoxarifado));
		criteria.add(Restrictions.eq("tipoMovimento", TipoMovimento.SAIDA));
		
		criteria.addOrder(Order.asc("horario"));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SaldoEstoque> saldoDoProdutoEmAlmoxarifados(Long produtoCodigo) {
		
		SQLQuery query = this.getSession().createSQLQuery(sqlSaldoEstoque);
		
		query.setParameter("produto_codigo", produtoCodigo);
		
		query.setResultTransformer(Transformers.aliasToBean(SaldoEstoque.class));
		
		return query.list();
	}

}
