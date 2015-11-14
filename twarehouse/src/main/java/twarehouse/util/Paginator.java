package twarehouse.util;

public class Paginator {

	private int firstResult = 0;
	private int qtdPorPagina = 0;
	
	public Paginator(int qtdPorPagina) {
		this.qtdPorPagina = qtdPorPagina;
	}

	public void goTo(int index) {
		
		if (index > 1) {
			
			firstResult = --index * qtdPorPagina;
		} else {
			
			firstResult = 0;
		}
		System.out.println(firstResult);
	}
	
	public void next() {
		
		firstResult = getLastResult();
	}
	
	public void previous() {
		
		if (firstResult > 0) {
			
			firstResult = firstResult - qtdPorPagina;
		}
		
	}
	
	public int getLastResult() {
		return firstResult + qtdPorPagina;
	}
	
	public int getFirstResult() {
		return this.firstResult;
	}
	
	public int getQtdPorPagina() {
		return this.qtdPorPagina;
	}

	@Override
	public String toString() {
		return "Paginator [getLastResult()=" + getLastResult()
				+ ", getFirstResult()=" + getFirstResult()
				+ ", getQtdPorPagina()=" + getQtdPorPagina() + "]";
	}
	
}
