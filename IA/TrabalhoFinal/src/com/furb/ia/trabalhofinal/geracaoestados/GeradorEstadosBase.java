package com.furb.ia.trabalhofinal.geracaoestados;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.furb.ia.trabalhofinal.EstadoSemestre;

public abstract class GeradorEstadosBase {

	protected int numeroSemestres;
	
	public GeradorEstadosBase(int numeroSemestres) {
		this.numeroSemestres = numeroSemestres;
	}
	
	protected abstract EstadoSemestre combinar(EstadoSemestre umPai, EstadoSemestre outroPai);
	
	public abstract List<EstadoSemestre> processar(List<EstadoSemestre> estados);
	
	protected List<EstadoSemestre> processarBase(List<EstadoSemestre> estados, List<EstadoSemestre> filhos) {
		filhos = filhos == null ? new ArrayList<EstadoSemestre>() : filhos;
		
		while (filhos.size() < 10) {
			EstadoSemestre umPai = encontrarPai(estados, null);
			EstadoSemestre outroPai = encontrarPai(estados, umPai);
			filhos.add(combinar(umPai, outroPai));
		}
		
		return filhos;
	}

	private EstadoSemestre encontrarPai(List<EstadoSemestre> estados, EstadoSemestre outroPai) {
		Random r = new Random();
		float valor = r.nextFloat() * 1.0f;

		EstadoSemestre pai = null;

		for (EstadoSemestre estado : estados) {
			if (estado.getFitness() > valor &&
			    (outroPai != null && !outroPai.equals(estado))) {
				pai = estado;
				break;
			}
		}

		if (pai == null)
			pai = estados.get(estados.size() - 1);

		return pai;
	}
	
}
