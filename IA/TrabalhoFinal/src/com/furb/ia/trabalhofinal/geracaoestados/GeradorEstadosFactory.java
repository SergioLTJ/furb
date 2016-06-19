package com.furb.ia.trabalhofinal.geracaoestados;

import com.furb.ia.trabalhofinal.dados.TipoGeracaoEstado;

public class GeradorEstadosFactory {

	public GeradorEstadosBase criar(TipoGeracaoEstado tipoGerador, int numeroSemestres) {
		
		switch (tipoGerador) {
		case COMBINACAO_DIAS:
			return new CombinacaoDias(numeroSemestres);

		case COMBINACAO_SEMESTRES:
			return new CombinacaoSemestres(numeroSemestres);
		
		default:
			throw new IllegalArgumentException("Tipo de geração de estados não conhecida:" + tipoGerador.toString());
		}
	}
	
}
