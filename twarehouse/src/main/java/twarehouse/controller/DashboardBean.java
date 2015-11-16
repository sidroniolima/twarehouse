/**
 * 
 */
package twarehouse.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import twarehouse.model.consulta.Dashboard;
import twarehouse.service.DashboardService;

/**
 * Controller das informações do Dashboard.
 * 
 * @author Sidronio
 * 16/11/2015
 */
@Named
@ViewScoped
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = -928080119350441316L;

	@Inject
	private DashboardService dashboardService;
	
	private Dashboard dashboard;

	@PostConstruct
	public void init() {
		dashboard = new Dashboard();
		
		dashboard.setQtdProdutosEmReposicao(dashboardService.qtdDeProdutosEmReposicao());
		dashboard.setQtdProdutosEsgotados(dashboardService.qtdDeProdutosEsgotados());
	}
	
	public Dashboard getDashboard() {
		return dashboard;
	}
	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	
}
