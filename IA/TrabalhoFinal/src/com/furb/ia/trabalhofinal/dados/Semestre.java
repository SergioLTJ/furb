package com.furb.ia.trabalhofinal.dados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.furb.ia.trabalhofinal.Utils;

public class Semestre implements Clonavel<Semestre> {

	private int numero;
	private List<Dia> dias;
	private List<Disciplina> disciplinas;

	public Semestre(int numero, List<Disciplina> disciplinas) {
		this(numero, Arrays.asList(new Dia("Segunda"), new Dia("Terça"), new Dia("Quarta"), new Dia("Quinta"), new Dia("Sexta")), disciplinas);
	}

	public Semestre(int numero, List<Dia> dias, List<Disciplina> disciplinas) {
		this.numero = numero;
		this.dias = dias;
		this.disciplinas = disciplinas;
	}

	public boolean ehValido() {
		if (temDisciplinaNoMesmoDia())
			return false;

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

	public void adicionarDisciplina(Professor professor, int indiceDisciplina) {
		Disciplina disciplina = this.disciplinas.get(indiceDisciplina);
		disciplina.setProfessorMinistrando(professor);

		for (Dia dia : this.dias) {
			if (!dia.estaCompleto()) {
				dia.adicionarDisciplina(disciplina);
				return;
			}
		}
	}

	public List<Semestre> gerarEstadosSemestre(List<Professor> professores) {
		List<Semestre> semestresRetornar = new ArrayList<>();

		for (Disciplina disciplina : this.disciplinas) {
			if (!disciplina.estaAlocada()) {
				List<Professor> professoresParaDisciplina = this.encontrarProfessoresParaDisciplina(professores, disciplina);
				List<Semestre> semestres = this.gerarEstadosParaDisciplina(professoresParaDisciplina, disciplina);
				semestresRetornar.addAll(semestres);
			}
		}

		return semestresRetornar;
	}

	private List<Semestre> gerarEstadosParaDisciplina(List<Professor> professores, Disciplina disciplina) {
		List<Semestre> semestres = new ArrayList<>();

		for (Professor professor : professores) {
			Semestre semestre = this.clonar();
			int indiceDisciplina = disciplinas.indexOf(disciplina);
			semestre.adicionarDisciplina(professor, indiceDisciplina);
			semestres.add(semestre);
		}

		return semestres;
	}

	private List<Professor> encontrarProfessoresParaDisciplina(List<Professor> professores, Disciplina disciplina) {
		List<Professor> professoresParaDisciplina = new ArrayList<>();

		for (Professor professor : professores) {
			if (professor.podeMinistrarDisciplina(disciplina)) {
				professoresParaDisciplina.add(professor);
			}
		}

		return professoresParaDisciplina;
	}

	public boolean estaCompleto() {
		for (Disciplina disciplina : this.disciplinas) {
			if (!disciplina.estaAlocada()) {
				return false;
			}
		}
		return true;
	}

	private boolean diasIguais(Semestre outro) {
		for (int i = 0; i < dias.size(); i++) {
			if (!dias.get(i).equals(outro.getDias().get(i))) {
				return false;
			}
		}
		return true;
	}

	public List<Dia> obterDiasProfessor(Professor professor, boolean temAula) {
		List<Dia> diasLivres = new ArrayList<>();

		for (Dia dia : dias) {
			if (temAula ? !dia.professorEstaLivre(professor) : dia.professorEstaLivre(professor)) {
				diasLivres.add(dia);
			}
		}

		return diasLivres;
	}

	public void validarDiasLivres(List<Dia> diasLivres, Professor professor) {
		List<Dia> paraRemover = new ArrayList<>();

		for (Dia dia : diasLivres) {
			Dia diaNoSemestre = obterDiaPorNome(dia.getNome());
			if (!diaNoSemestre.professorEstaLivre(professor)) {
				paraRemover.add(dia);
			}
		}

		diasLivres.removeAll(paraRemover);
	}

	public boolean validarHorariosProfessor(List<Dia> diasComAula, Professor professor) {
		List<Dia> paraAdicionar = new ArrayList<>();
		List<Dia> diasComAulaNoSemestre = this.obterDiasProfessor(professor, true);

		for (Dia diaNoSemestre : diasComAulaNoSemestre) {
			Dia diaForaSemestre = this.obterDiaPorNome(diaNoSemestre.getNome(), diasComAula);
			if (diaForaSemestre != null) {
				if (diaForaSemestre.professorEstaNoMesmoHorario(diaNoSemestre, professor)) {
					return false;
				} else {
					paraAdicionar.add(diaForaSemestre);
				}
			} else {
				paraAdicionar.add(diaNoSemestre);
			}
		}

		diasComAula.addAll(paraAdicionar);
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

	public List<Dia> getDias() {
		return this.dias;
	}

	public Dia obterDiaPorNome(String nome) {
		return obterDiaPorNome(nome, this.dias);
	}

	public Dia obterDiaPorNome(String nome, List<Dia> dias) {
		for (Dia dia : dias) {
			if (dia.getNome().equals(nome)) {
				return dia;
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Semestre))
			return false;

		Semestre outro = (Semestre) obj;

		if (!diasIguais(outro))
			return false;

		return this.numero == outro.getNumero();
	}

	public int getNumero() {
		return this.numero;
	}

	@Override
	public int hashCode() {
		int hash = 17;

		for (Dia dia : this.dias) {
			hash = 31 * hash + dia.hashCode();
		}

		return 31 * hash + this.numero;
	}

	@Override
	public String toString() {
		String descricaoSemestre = "--------------------------------------------\n";
		descricaoSemestre += "Semestre número " + this.numero + ":\n\n";

		for (Dia dia : this.dias) {
			descricaoSemestre += dia.toString() + "\n";
		}

		return descricaoSemestre;
	}

	@Override
	public Semestre clonar() {
		List<Disciplina> disciplinas = Utils.clonarLista(this.disciplinas);
		List<Dia> dias = Utils.clonarLista(this.dias);

		return new Semestre(this.numero, dias, disciplinas);
	}

	public boolean professorEstaTrabalhando(Professor professor) {
		for (Dia dia : this.dias) {
			if (!dia.professorEstaLivre(professor)) {
				return true;
			}
		}
		return false;
	}

	public void preencherAleatorio(List<Professor> professores) {
		Random r = new Random();

		for (Dia dia : dias) {
			while (!dia.estaCompleto()) {
				Disciplina disciplina;
				do {
					disciplina = this.disciplinas.get(r.nextInt(this.disciplinas.size()));
				} while (disciplina.estaAlocada());

				Professor professor;

				do {
					professor = professores.get(r.nextInt(professores.size()));
				} while (!professor.getDisciplinasAptoMinistrar().contains(disciplina));

				disciplina.setProfessorMinistrando(professor);
				dia.adicionarDisciplina(disciplina);
			}
		}
	}

	public float calcularFitness(List<Semestre> semestres) {
		float fitness = 0;
		semestres.remove(this);
		for (Dia dia : this.dias) {
			for (Semestre semestre : semestres) {
				if (semestre.obterDiaPorNome(dia.getNome()).getAula1().getProfessorMinistrando() == dia.getAula1().getProfessorMinistrando())
					fitness -= 1;
				if (semestre.obterDiaPorNome(dia.getNome()).getAula2().getProfessorMinistrando() == dia.getAula2().getProfessorMinistrando())
					fitness -= 1;
			}

			if (dia.ehMesmaAula())
				fitness -= 1;
			
			for (Disciplina disciplina : this.disciplinas) {
				fitness += this.validarDisciplina(disciplina);
			}
		}

		return fitness;
	}
	
	private float validarDisciplina(Disciplina disciplina) {
		int tempoDisciplina = disciplina.getCargaSemanal();
		
		for (Dia dia : dias) {
			if (dia.getAula1().equals(disciplina)) tempoDisciplina--;
			if (dia.getAula2().equals(disciplina)) tempoDisciplina--;
		}
		
		if (tempoDisciplina > 0) tempoDisciplina = -tempoDisciplina;
		
		return tempoDisciplina * 2;
	}

	public List<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}
	
	public String paraHtml() {
		String html = "<h4>" + this.numero + "º Semestre</h4><table class=\"table table-bordered\"><tr><th></th><th>Segunda</th><th>Terça</th><th>Quarta</th><th>Quinta</th><th>Sexta</th></tr><tr><th>1ª aula</th>";
		
		for (Dia dia : this.dias) {
			html += "<td>" + dia.getAula1().getNome() + " (" + dia.getAula1().getProfessorMinistrando().getNome() + ")" + "</td>";
		}
		
		html += "</tr><tr><th>2ª aula</th>";
		
		for (Dia dia : this.dias) {
			html += "<td>" + dia.getAula2().getNome() + " (" + dia.getAula2().getProfessorMinistrando().getNome() + ")" + "</td>";
		}
		
		return html + "</tr></table>";
	}
}
