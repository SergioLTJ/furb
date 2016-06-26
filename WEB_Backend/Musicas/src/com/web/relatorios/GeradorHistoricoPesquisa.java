package com.web.relatorios;

import com.web.database.dao.TermosPesquisaDao;

public class GeradorHistoricoPesquisa {

	public void gerarRegistros(String busca) {
		if (busca == null) {
			return;
		}
		
		TermosPesquisaDao dao = new TermosPesquisaDao();
		
		dao.gerarOuAtualizarRegistro(busca.trim().toLowerCase());
	}
	
}
