package trabalho1;

import java.util.List;

public class Semestre {

	private int numero;
	private List<Dia> dias;
	private List<Disciplina> disciplinas;
	
	public boolean ehValido() {
		if (temDisciplinaNoMesmoDia()) return false;
		
		return true;
	}

	
	
	private boolean temDisciplinaNoMesmoDia() {
		for (Dia dia : this.dias) {
			if (dia.estaCompleto() && dia.ehMesmaAula()) {
				return true;
			}
		}
		return false;
	}
	
	public void adicionarDisciplina(int indiceProfessor, int indiceDisciplina) {
		Disciplina disciplina = this.disciplinas.get(indiceDisciplina);
		disciplina.setProfessorMinistrando(this.professores.get(indiceProfessor));
		
		for (Dia dia : this.dias) {
			if (!dia.estaCompleto()) {
				dia.adicionarDisciplina(disciplina);
				return;
			}
		}
	}

	private boolean diasIguais(Semestre outro) {
		for (int i = 0; i < dias.size(); i++) {
			if (!dias.get(i).equals(outro.getDias().get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean professorTemDiaLivre(Professor professor) {
		for (Dia dia : this.dias) {
			if (dia.professorEstaLivre(professor)) {
				return true;
			}
		}
		return false;
	}
	
	private List<Dia> getDias() {
		return this.dias;
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		
		for (Dia dia : this.dias) {
			hash = 31 * hash + dia.hashCode();
		}

		return hash;
	}
	
	@Override
	public String toString() {
		String descricaoSemestre = "--------------------------------------------\n";
		descricaoSemestre += "Semestre número " + this.numero + ":\n";
		
		for (Dia dia : this.dias) {
			descricaoSemestre += dia.toString() + "\n";
		}

		return descricaoSemestre;
	}
	
}
