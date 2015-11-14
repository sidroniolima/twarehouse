/**
 * 
 */
package twarehouse.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import twarehouse.model.Cnpj;
import twarehouse.model.Cpf;
import twarehouse.model.Fornecedor;
import twarehouse.model.PessoaFisica;
import twarehouse.model.PessoaJuridica;
import twarehouse.model.TipoPessoa;

public class FornecedorTest {

	private Fornecedor fornecedorPF;
	private Fornecedor fornecedorPJ;
	
	@Before
	public void setUp() throws Exception {
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf(new Cpf("101.712.957-65"));
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj(new Cnpj("72-562-895/1000-69"));
		
		fornecedorPF = new Fornecedor();
		fornecedorPF.setPessoa(pessoaFisica);
		
		fornecedorPJ = new Fornecedor();
		fornecedorPJ.setPessoa(pessoaJuridica);
	}

	@Test
	public void deveCriarFornecedoresComDiferentesPessoas() {
		
		assertEquals(fornecedorPF.getPessoa().getTipo(), TipoPessoa.FISICA);
		assertEquals(fornecedorPJ.getPessoa().getTipo(), TipoPessoa.JURIDICA);
	}
	
	@Test
	public void deveRetornarOCnpjDoFornecedorPJ() {
		
		PessoaJuridica pessoaJuridica = (PessoaJuridica) fornecedorPJ.getPessoa();
		System.out.println(pessoaJuridica.getCnpj().getValor());
	}
	
	@Test
	public void deveRetornarOCpfDoFornecedorPF() {
		
		PessoaFisica pessoaFisica = (PessoaFisica) fornecedorPF.getPessoa();
		System.out.println(pessoaFisica.getCpf().getValor());
	}

}
