package trabalho1;

import java.util.ArrayList;
import java.util.List;

import busca.Estado;

public class EstadoSemestre implements Estado {

	private ArrayList<Professor> professores;
	private ArrayList<Dia> dias;
	private ArrayList<Disciplina> disciplinas;
	
	private boolean professorTemDiaLivre(Professor professor) {
		for (Dia dia : dias) {
			if (dia.professorEstaLivre(professor)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean ehValido() {
		if (!todosProfessoresTemDiaLivre()) return false;
//		if (!professoresSoTemUmaDisciplinaPorHorario()) Para depois...
		
		return true;
	}
	

	private boolean todosProfessoresTemDiaLivre() {
		for (Professor professor : professores) {
			if (!professorTemDiaLivre(professor)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int custo() {
		return 1;
	}

	@Override
	public boolean ehMeta() {
		for (Dia dia : dias) {
			if (!dia.estaCompleto()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Estado> sucessores() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) 
			return false;
		if (!(obj instanceof EstadoSemestre))
			return false;
		
		EstadoSemestre outro = (EstadoSemestre)obj;
		
		if (!diasIguais(outro)) 
			return false;
			
		return true;
	}

	private boolean diasIguais(EstadoSemestre outro) {
		for (int i = 0; i < dias.size(); i++) {
			if (!dias.get(i).equals(outro.getDias().get(i))) {
				return false;
			}
		}
		
		return true;
	}

	private ArrayList<Dia> getDias() {
		return this.dias;
	}
	
}