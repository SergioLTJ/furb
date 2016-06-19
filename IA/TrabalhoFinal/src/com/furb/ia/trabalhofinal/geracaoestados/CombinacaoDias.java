package com.furb.ia.trabalhofinal.geracaoestados;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.furb.ia.trabalhofinal.EstadoSemestre;
import com.furb.ia.trabalhofinal.Utils;
import com.furb.ia.trabalhofinal.dados.Dia;
import com.furb.ia.trabalhofinal.dados.Semestre;

/**
 * Combinação de dias, gera problemas pois as semanas dos semestres filhos não são validadas,
 * gerando combinações inválidas onde as matérias são repetidas mais vezes que o permitido em
 * uma semana. 
 */
public class CombinacaoDias extends GeradorEstadosBase {

	public CombinacaoDias(int numeroSemestres) {
		super(numeroSemestres);
	}

	@Override
	protected EstadoSemestre combinar(EstadoSemestre umPai, EstadoSemestre outroPai) {
		List<Semestre> semestresFilho = new ArrayList<>();
		
		for (int i = 1; i <= super.numeroSemestres; i++) {
			Semestre semestreUmPai = umPai.obterSemestrePorNumero(i);
			Semestre semestreOutroPai = outroPai.obterSemestrePorNumero(i);
			Semestre semestre = combinarSemestres(semestreUmPai, semestreOutroPai);
			semestresFilho.add(semestre);
		}
		
		EstadoSemestre filho = new EstadoSemestre(Utils.clonarLista(umPai.getProfessores()), semestresFilho);
		
		return filho;
	}

	private EstadoSemestre determinarEstadoMaiorFitness(List<EstadoSemestre> estados) {
		EstadoSemestre maior = estados.get(0);
		for (int i = 1; i < estados.size(); i++) {
			if (estados.get(i).getFitness() > maior.getFitness())
				maior = estados.get(i);
		}
		return maior;
	}
	
	private Semestre combinarSemestres(Semestre semestreUmPai, Semestre semestreOutroPai) {
		Random r = new Random();
		List<Dia> dias = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Semestre semestre = r.nextInt(2) == 1 ? semestreUmPai : semestreOutroPai;
			dias.add(semestre.getDias().get(i));
		}
		return new Semestre(semestreUmPai.getNumero(), dias, semestreUmPai.getDisciplinas());
	}

	@Override
	public List<EstadoSemestre> processar(List<EstadoSemestre> estados) {
		List<EstadoSemestre> filhos = new ArrayList<>();
		filhos.add(determinarEstadoMaiorFitness(estados));
		return super.processarBase(estados, filhos);
	}

}
