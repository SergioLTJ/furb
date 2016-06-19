package com.furb.ia.trabalhofinal.geracaoestados;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.furb.ia.trabalhofinal.EstadoSemestre;
import com.furb.ia.trabalhofinal.Utils;
import com.furb.ia.trabalhofinal.dados.Semestre;

/**
 * Combinação semestres, corte único, muito difícil de gerar um estado perfeito com poucos estados 
 * iniciais (aleatórios) pois não existe nos estados iniciais uma combinação de semestres que combinados 
 * gerem um estado válido.
 *
 */
public class CombinacaoSemestres extends GeradorEstadosBase {

	public CombinacaoSemestres(int numeroSemestres) {
		super(numeroSemestres);
	}

	@Override
	protected EstadoSemestre combinar(EstadoSemestre umPai, EstadoSemestre outroPai) {
		List<Semestre> semestresFilho = new ArrayList<>();
		
		Random r = new Random();
		
		for (int i = 1; i < 8; i++) {
			EstadoSemestre pai = r.nextInt(2) == 0 ? umPai : outroPai;
			Semestre semestreFilho = pai.obterSemestrePorNumero(i);
			semestresFilho.add(semestreFilho);
		}

		EstadoSemestre filho = new EstadoSemestre(Utils.clonarLista(umPai.getProfessores()), semestresFilho);
		
		return filho;
	}

	@Override
	public List<EstadoSemestre> processar(List<EstadoSemestre> estados) {
		return super.processarBase(estados, null);
	}

}
