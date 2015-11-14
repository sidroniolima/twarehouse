/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.excpetion.RegraDeNegocioException;
import twarehouse.model.Produto;
import twarehouse.molde.estoque.Ajuste;
import twarehouse.molde.estoque.Almoxarifado;
import twarehouse.molde.estoque.TipoMovimento;
import twarehouse.service.AlmoxarifadoService;
import twarehouse.service.EstoqueService;
import twarehouse.service.impl.ProdutoService;
import twarehouse.util.FacesUtil;

/**
 * Controller do ajuste de estoque.
 * 
 * @author Sidronio
 * 
 * 09/11/2015
 */
@Named
@ViewScoped
public class AjusteEstoqueBean extends CadastroMasterDetail implements Serializable {

	private static final long serialVersionUID = -7573957900715930579L;

	/**
	 * Classe auxiliar utilizada para encapsular o Produto e sua 
	 * quantidade que será transformada em movimentação do ajuste.
	 * 
	 * @author Sidronio
	 * 09/11/2015
	 */
	public class ItemAjuste {
		
		private Produto produto;
		private BigDecimal qtd;
		
		public ItemAjuste() { }
		
		/**
		 * @param produto
		 * @param qtd
		 */
		public ItemAjuste(Produto produto, BigDecimal qtd) {
			this.produto = produto;
			this.qtd = qtd;
		}

		public Produto getProduto() {
			return produto;
		}
		public void setProduto(Produto produto) {
			this.produto = produto;
		}
		
		public BigDecimal getQtd() {
			return qtd;
		}
		public void setQtd(BigDecimal qtd) {
			this.qtd = qtd;
		}
		
		public void valida() throws RegraDeNegocioException{
			
			if (null == produto) {
				throw new RegraDeNegocioException("É necessário selecionar o produto para o ajuste.");
			}
			
			if (null == qtd || qtd.compareTo(BigDecimal.ZERO) <= 0) {
				throw new RegraDeNegocioException("A quantidade deve ser maior que 0.");
			}
		}
		
	}
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private AlmoxarifadoService almoxarifadoService;
	
	@Inject
	private ProdutoService produtoService;
	
	private Ajuste ajuste;
	
	private ItemAjuste itemMovimento;
	private Produto produtoSelecionado;
	
	private List<Almoxarifado> almoxarifados;
	
	private TipoMovimento[] tipos;
	
	private List<Produto> produtos;
	
	/* Inicia o Bean.
	 * @see twarehouse.controller.CadastroSingle#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		
		tipos = TipoMovimento.values();
		
		almoxarifados = almoxarifadoService.buscaTodas();
		produtos = produtoService.listaTodos();
		
		novoRegistro();
	}
	
	/* (non-Javadoc)
	 * @see twarehouse.controller.CadastroSingle#novoRegistro()
	 */
	@Override
	public void novoRegistro() {
		ajuste = new Ajuste();
		
		this.novoItem();
	}

	/* Salva a movimentação de acordo com o Tipo: 
	 * 		Entrada
	 * 		Saída
	 * 		Transferência
	 * @see twarehouse.controller.CadastroSingle#salvar()
	 */
	@Override
	public void salvar() {
		try {
			
			this.ajuste.valida();
			
			this.estoqueService.ajusta(ajuste);
			
			FacesUtil.addSuccessMessage("Ajuste de estoque realizado com sucesso.");
			
			novoRegistro();
		} catch (RegraDeNegocioException e) {
			
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	/**
	 * Utilizado na seleção do produto. Completa as informações de 
	 * subgrupo e unidades.
	 * 
	 * @param event
	 */
	public void completaInformacoesDoProduto(ValueChangeEvent evento) {
		
		Produto produtoSelecionado = ((Produto) evento.getNewValue());
		
		if (null != produtoSelecionado) {
			
			//itemMovimento.setProduto(produtoService.buscaPeloCodigoComSubgrupoEUnidades(produtoSelecionado.getCodigo()));
		}
	}

	/* Informa se é edição. (Não utilizado).
	 * @see twarehouse.controller.CadastroSingle#isEdicao()
	 */
	@Override
	public boolean isEdicao() {
		return false;
	}

	/* Mensagem de inclusão.
	 * @see twarehouse.controller.CadastroSingle#getMensagemDeInclusao(java.lang.String)
	 */
	@Override
	public String getMensagemDeInclusao(String registro) {
		return null;
	}

	/* Mensagem de alteração. (Não utilizada).
	 * @see twarehouse.controller.CadastroSingle#getMensagemDeAlteracao(java.lang.String)
	 */
	@Override
	public String getMensagemDeAlteracao(String registro) {
		return null;
	}
	
	/* Informa se é uma edição de item.
	 * @see twarehouse.controller.CadastroMasterDetail#isEdicaoDeItem()
	 */
	@Override
	public boolean isEdicaoDeItem() {
		return null != produtoSelecionado;
	}

	/* (não utilizado)
	 * @see twarehouse.controller.CadastroMasterDetail#editaItem()
	 */
	@Override
	public void editaItem() {

	}
	
	/**
	 * Seleciona um produto para Edição ou Remoção.
	 * 
	 * @param produto
	 * @param qtd
	 */
	public void selecionaProduto(Produto produto) {
		
		BigDecimal qtd = BigDecimal.ZERO;
		
		produtoSelecionado = produto;

		qtd = ajuste.getMovimentacao().get(produto);
		
		itemMovimento = new ItemAjuste(produtoSelecionado, qtd);
	}

	/* Mensage de inclusão de movimento.
	 * @see twarehouse.controller.CadastroMasterDetail#getMensagemDeInclusaoDeItem(java.lang.String)
	 */
	@Override
	public String getMensagemDeInclusaoDeItem(String registro) {
		return String.format("Movimento do produto %s incluído.", registro);
	}

	/* Mensage de alteração de movimento.
	 * @see twarehouse.controller.CadastroMasterDetail#getMensagemDeAlteracaoDeItem(java.lang.String)
	 */
	@Override
	public String getMensagemDeAlteracaoDeItem(String registro) {
		return String.format("Movimento do produto %s alterado.", registro);
	}

	/* Mensage de exclusão de movimento.
	 * @see twarehouse.controller.CadastroMasterDetail#getMensagemDeExclusaoDeItem(java.lang.String)
	 */
	@Override
	public String getMensagemDeExclusaoDeItem(String registro) {
		return String.format("Movimento do produto %s removido.", registro);
	}

	/* Adiciona um movimento.
	 * @see twarehouse.controller.CadastroMasterDetail#adicionaItem()
	 */
	@Override
	public void adicionaItem() {
		
		try {
			
			itemMovimento.valida();
			
			if (isEdicaoDeItem()) {
				
				FacesUtil.addSuccessMessage(this.getMensagemDeAlteracaoDeItem(itemMovimento.getProduto().getDescricao()));
				
			} else {
				FacesUtil.addSuccessMessage(this.getMensagemDeInclusaoDeItem(itemMovimento.getProduto().getDescricao()));
			}
			
			itemMovimento.setProduto(produtoService.buscaPeloCodigoComSubgrupoEUnidades(itemMovimento.getProduto().getCodigo()));
			
			ajuste.adicionaMovimento(itemMovimento.getProduto(), itemMovimento.getQtd());
			
			novoItem();			
			
		} catch (RegraDeNegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	/* Remove um item.
	 * @see twarehouse.controller.CadastroMasterDetail#removeItem()
	 */
	@Override
	public void removeItem() {
		ajuste.removeMovimento(produtoSelecionado);
		
		novoItem();
	}
	
	/* Novo movimento.
	 * @see twarehouse.controller.CadastroMasterDetail#novoItem()
	 */
	@Override
	public void novoItem() {
		itemMovimento = new ItemAjuste();
		
		produtoSelecionado = null;
	}

	public Ajuste getAjuste() {
		return ajuste;
	}
	public void setAjuste(Ajuste ajuste) {
		this.ajuste = ajuste;
	}

	public ItemAjuste getItemMovimento() {
		return itemMovimento;
	}
	public void setItemMovimento(ItemAjuste itemMovimento) {
		this.itemMovimento = itemMovimento;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<Almoxarifado> getAlmoxarifados() {
		return almoxarifados;
	}

	public TipoMovimento[] getTipos() {
		return tipos;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
	
}
