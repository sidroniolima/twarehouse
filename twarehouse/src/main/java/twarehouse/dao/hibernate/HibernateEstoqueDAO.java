package twarehouse.dao.hibernate;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import twarehouse.dao.EstoqueDAO;
import twarehouse.model.Produto;
import twarehouse.model.consulta.ProdutoEmReposicao;
import twarehouse.model.consulta.SaldoPorAlmoxarifado;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.Movimento;
import twarehouse.model.estoque.TipoMovimento;

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
	
	private final String sqlProdutosEmReposicao = 
			" SELECT "
			+" p.codigo AS 'codigoProduto', "
			+" p.descricao AS 'descricaoProduto', "
			+" s.descricao AS 'descricaoSubgrupo', "
			+" u_ent.descricao AS 'unidadeEntrada', "
			+" u_sai.descricao AS 'unidadeSaida', "
			+" us.razao AS 'razao', "
			+" p.qtd_reposicao AS 'reposicao', "           
				        
			+" 			 COALESCE( "
			+" 				(SELECT "
			+" 					SUM(qtd) as 'ENTRADA'  "
			+" 				FROM "
			+" 					movimento mENTRADA "  
			+" 				WHERE "
			+" 					mENTRADA.tipo_movimento = 'ENTRADA' "
			+" 					AND  mENTRADA.produto_codigo = p.codigo "
			+" 					), "
			+" 				0.00) - "
			+" 	         COALESCE( "
			+" 				(SELECT "
			+" 					SUM(qtd) as 'SAIDA' "
			+" 				FROM "
			+" 					movimento mSAIDA " 
			+" 				WHERE "
			+" 					mSAIDA.tipo_movimento = 'SAIDA' "
			+" 					AND  mSAIDA.produto_codigo = p.codigo "
			+"                    ), "
			+" 				0.00) AS 'saldo' "  
				       
			+" FROM "
			+" 	produto p, "
			+"  sub_grupo s, "
			+"  produto_unidade us, "
			+"  unidade u_ent, "
			+"  unidade u_sai "
						
			+" WHERE "
			+" 	p.codigo_subgrupo = s.codigo	AND "
			+"  p.codigo = us.produto_codigo	AND "
			+"  us.unidade_entrada = u_ent.codigo	AND "
			+"  us.unidade_saida = u_sai.codigo "
			                
			+" GROUP BY "
			+" 	p.codigo "
			+" ORDER BY "
			+"	p.descricao	"
			
			+" LIMIT :first, :results "
	
			+" HAVING saldo > 0 ";
	
	private final String sqlProdutosEsgotados = 
			" SELECT "
			+" p.codigo AS 'codigoProduto', "
			+" p.descricao AS 'descricaoProduto', "
			+" s.descricao AS 'descricaoSubgrupo', "
			+" u_ent.descricao AS 'unidadeEntrada', "
			+" u_sai.descricao AS 'unidadeSaida', "
			+" us.razao AS 'razao', "
			+" p.qtd_reposicao AS 'reposicao', "           
				        
			+" 			 COALESCE( "
			+" 				(SELECT "
			+" 					SUM(qtd) as 'ENTRADA'  "
			+" 				FROM "
			+" 					movimento mENTRADA "  
			+" 				WHERE "
			+" 					mENTRADA.tipo_movimento = 'ENTRADA' "
			+" 					AND  mENTRADA.produto_codigo = p.codigo "
			+" 					), "
			+" 				0.00) - "
			+" 	         COALESCE( "
			+" 				(SELECT "
			+" 					SUM(qtd) as 'SAIDA' "
			+" 				FROM "
			+" 					movimento mSAIDA " 
			+" 				WHERE "
			+" 					mSAIDA.tipo_movimento = 'SAIDA' "
			+" 					AND  mSAIDA.produto_codigo = p.codigo "
			+"                    ), "
			+" 				0.00) AS 'saldo' "  
				       
			+" FROM "
			+" 	produto p, "
			+"  sub_grupo s, "
			+"  produto_unidade us, "
			+"  unidade u_ent, "
			+"  unidade u_sai "
						
			+" WHERE "
			+" 	p.codigo_subgrupo = s.codigo	AND "
			+"  p.codigo = us.produto_codigo	AND "
			+"  us.unidade_entrada = u_ent.codigo	AND "
			+"  us.unidade_saida = u_sai.codigo "
			                
			+" GROUP BY "
			+" 	p.codigo "
			+" ORDER BY "
			+"	p.descricao	"
			
			+" LIMIT :first, :results "
	
			+" HAVING saldo <= 0 ";

	private final String qtdDeProdutosEsgotados = 
			" SELECT "
			+"	count(*) " 
				                
			+" FROM "
			+"	produto p "
			
			+" WHERE "

			+" COALESCE( "
			+"	(SELECT "
			+"		SUM(qtd) as 'ENTRADA' " 
			+"	FROM "
			+"		movimento mENTRADA "  
			+"	WHERE "
			+"		mENTRADA.tipo_movimento = 'ENTRADA' "
			+"		AND  mENTRADA.produto_codigo = p.codigo "
			+"		), "
			+"	0.00) - "
			+" COALESCE( "
			+"	(SELECT "
			+"		SUM(qtd) as 'SAIDA' "
			+"	FROM "
			+"		movimento mSAIDA " 
			+"	WHERE "
			+"		mSAIDA.tipo_movimento = 'SAIDA' " 
			+"		AND  mSAIDA.produto_codigo = p.codigo "
			+"		), "
			+"	0.00) = 0.00 ";
	
	private final String qtdDeProdutosEmReposicao = 
			" SELECT "
			+"	count(*) " 
				                
			+" FROM "
			+"	produto p "

			+" WHERE "
			+" COALESCE( "
			+"	(SELECT "
			+"		SUM(qtd) as 'ENTRADA' " 
			+"	FROM "
			+"		movimento mENTRADA "  
			+"	WHERE "
			+"		mENTRADA.tipo_movimento = 'ENTRADA' "
			+"		AND  mENTRADA.produto_codigo = p.codigo "
			+"		), "
			+"	0.00) - "
			+" COALESCE( "
			+"	(SELECT "
			+"		SUM(qtd) as 'SAIDA' "
			+"	FROM "
			+"		movimento mSAIDA " 
			+"	WHERE "
			+"		mSAIDA.tipo_movimento = 'SAIDA' " 
			+"		AND  mSAIDA.produto_codigo = p.codigo "
			+"		), "
			+"	0.00) < p.qtd_reposicao ";	
	
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
	public List<SaldoPorAlmoxarifado> saldoDoProdutoEmAlmoxarifados(Long produtoCodigo) {
		
		SQLQuery query = this.getSession().createSQLQuery(sqlSaldoEstoque);
		
		query.setParameter("produto_codigo", produtoCodigo);
		
		query.setResultTransformer(Transformers.aliasToBean(SaldoPorAlmoxarifado.class));
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEmReposicao> listarProdutosEsgotados(int firstResult, int results) {
		
		SQLQuery query = this.getSession().createSQLQuery(sqlProdutosEmReposicao);
		
		query.setParameter("first", firstResult);
		query.setParameter("results", results);
		
		query.setResultTransformer(Transformers.aliasToBean(ProdutoEmReposicao.class));
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEmReposicao> listarProdutosEmReposicao(int firstResult, int results) {
		SQLQuery query = this.getSession().createSQLQuery(sqlProdutosEsgotados);
		
		query.setParameter("first", firstResult);
		query.setParameter("results", results);
		
		query.setResultTransformer(Transformers.aliasToBean(ProdutoEmReposicao.class));
		
		return query.list();
	}
	
	@Override
	public BigInteger qtdDeProdutosEmReposicao() {
	
		SQLQuery query = this.getSession().createSQLQuery(qtdDeProdutosEmReposicao);
		
		return (BigInteger) query.uniqueResult();
	}
	
	@Override
	public BigInteger qtdDeProdutosEsgotados() {
	
		SQLQuery query = this.getSession().createSQLQuery(qtdDeProdutosEsgotados);
		
		return (BigInteger) query.uniqueResult();
	}

}
