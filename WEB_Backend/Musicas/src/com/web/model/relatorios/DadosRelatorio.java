package com.web.model.relatorios;

import java.util.ArrayList;
import java.util.List;

public class DadosRelatorio {

	private List<Object> dados;
	
	public DadosRelatorio() {
		dados = new ArrayList<>();
	}
	
	public void addDado(Object dado) {
		dados.add(dado);
	}
}
