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
import twarehouse.model.estoque.Ajuste;
import twarehouse.model.estoque.Almoxarifado;
import twarehouse.model.estoque.ItemAjuste;
import twarehouse.model.estoque.TipoMovimento;
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

	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private AlmoxarifadoService almoxarifadoService;
	
	@Inject
	private ProdutoService produtoService;
	
	private Ajuste ajuste;
	
	private ItemAjuste itemAjuste;
	private ItemAjuste itemSelecionado;
	
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
			
			itemAjuste.setProduto(
					produtoService.buscaPeloCodigoComSubgrupoEUnidades(produtoSelecionado.getCodigo()));
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
		return null != itemSelecionado;
	}

	/* Edita um item.
	 * @see twarehouse.controller.CadastroMasterDetail#editaItem()
	 */
	@Override
	public void editaItem() {
		itemAjuste = itemSelecionado;

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
			
			Produto produtoComUnidadesESubgrupo =
					produtoService.buscaPeloCodigoComSubgrupoEUnidades(itemAjuste.getProduto().getCodigo());
			itemAjuste.setProduto(produtoComUnidadesESubgrupo);
			
			itemAjuste.valida();
			
			if (isEdicaoDeItem()) {
				
				FacesUtil.addSuccessMessage(
						this.getMensagemDeAlteracaoDeItem(itemAjuste.getProduto().getDescricao()));
				
			} else {
				FacesUtil.addSuccessMessage(
						this.getMensagemDeInclusaoDeItem(itemAjuste.getProduto().getDescricao()));
			}
			
			ajuste.adicionaMovimento(itemAjuste);
			
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
		ajuste.removeItem(itemSelecionado);
		
		novoItem();
	}
	
	/* Novo movimento.
	 * @see twarehouse.controller.CadastroMasterDetail#novoItem()
	 */
	@Override
	public void novoItem() {
		itemAjuste = new ItemAjuste();
		
		itemSelecionado = null;
	}

	public Ajuste getAjuste() {
		return ajuste;
	}
	public void setAjuste(Ajuste ajuste) {
		this.ajuste = ajuste;
	}

	public ItemAjuste getItemAjuste() {
		return itemAjuste;
	}
	public void setItemAjuste(ItemAjuste itemMovimento) {
		this.itemAjuste = itemMovimento;
	}

	public ItemAjuste getItemSelecionado() {
		return itemSelecionado;
	}
	public void setItemSelecionado(ItemAjuste itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
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
