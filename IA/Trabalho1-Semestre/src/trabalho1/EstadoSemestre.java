package trabalho1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import busca.BuscaLargura;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;

public class EstadoSemestre implements Estado {

	private List<Professor> professores;
	private List<Semestre> semestres;
	
	public EstadoSemestre(List<Professor> professores, List<Semestre> semestres) {
		this.professores = professores;
		this.semestres = semestres;
	}
	
	private boolean ehValido() {
		for (Semestre semestre : this.semestres) {
			if (!semestre.ehValido()) {
				return false;
			}
		}
		
		if (!todosProfessoresTemDiaLivre(professores)) return false;
//		if (!professoresSoTemUmaDisciplinaPorHorario()) Para depois...
		
		return true;
	}

	private boolean todosProfessoresTemDiaLivre(List<Professor> professores) {
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
		for (Disciplina disciplina : this.disciplinas) {
			if (!disciplina.estaAlocada()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Estado> sucessores() {
		ArrayList<Estado> estados = new ArrayList<>();
		
		for (Disciplina disciplina : this.disciplinas) {
			if (!disciplina.estaAlocada()) {
				List<Integer> professoresParaDisciplina = this.encontrarProfessoresParaDisciplina(disciplina);
				List<Estado> estadosValidos = this.gerarEstadosParaDisciplina(professoresParaDisciplina, disciplina); 
				estados.addAll(estadosValidos);
			}
		}
		
		return estados;
	}
	
	private List<Estado> gerarEstadosParaDisciplina(List<Integer> indiceProfessores, Disciplina disciplina) {
		List<Estado> estados = new ArrayList<>();
		
		for (Integer indiceProfessor : indiceProfessores) {
			List<Professor> professores = Utils.clonarLista(this.professores);
			List<Disciplina> disciplinas = Utils.clonarLista(this.disciplinas);
			List<Dia> dias = Utils.clonarLista(this.dias);
			
			int indiceDisciplina = disciplinas.indexOf(disciplina);
			
			EstadoSemestre estado = new EstadoSemestre(professores, dias, disciplinas); 
			estado.adicionarDisciplina(indiceProfessor, indiceDisciplina);
			
			if (estado.ehValido()) {
				estados.add(estado);
			}			
		}
		
		return estados;
	}

	private List<Integer> encontrarProfessoresParaDisciplina(Disciplina disciplina) {
		List<Integer> professores = new ArrayList<>();
		
		for (int i = 0; i < this.professores.size(); i++) {
			if (this.professores.get(i).podeMinistrarDisciplina(disciplina)) {
				professores.add(i);
			}
		}
		
		return professores;
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

	@Override
	public String toString() {
		String descricaoEstado = "";
		
		for (Semestre semestre : this.semestres) {
			descricaoEstado += "--------------------------------------------\n";
			descricaoEstado += semestre.toString();
		}

		descricaoEstado += "\n\n";
		
		return descricaoEstado;
	}
	
	public static void main(String[] args) {
		List<Dia> dias = new ArrayList<>();
		dias.add(new Dia("Segunda"));
		dias.add(new Dia("Terça"));
		dias.add(new Dia("Quarta"));
		dias.add(new Dia("Quinta"));
		dias.add(new Dia("Sexta"));
		
		Disciplina ia = new Disciplina("Inteligência Artificial", CargaSemanalDisciplina.QUATRO_HORAS);
		Disciplina ps2 = new Disciplina("Processos de Software II", CargaSemanalDisciplina.QUATRO_HORAS);
		Disciplina rob = new Disciplina("Robótica", CargaSemanalDisciplina.QUATRO_HORAS);
		Disciplina web = new Disciplina("Desenvolvimento Web", CargaSemanalDisciplina.QUATRO_HORAS);
		Disciplina cg = new Disciplina("Computação Gráfica", CargaSemanalDisciplina.QUATRO_HORAS);
		
		List<Disciplina> disciplinas = Arrays.asList(ia, ps2, rob, web, cg);
		
		Professor daniel = new Professor("Daniel").addDisciplinasAptoMinistrar(ia);
		Professor dalton = new Professor("Dalton").addDisciplinasAptoMinistrar(cg);
		Professor mauro = new Professor("Mauro").addDisciplinasAptoMinistrar(ps2);
		Professor aurelio = new Professor("Aurélio").addDisciplinasAptoMinistrar(rob);
		Professor matheus = new Professor("Matheus").addDisciplinasAptoMinistrar(web);
		
		List<Professor> professores = Arrays.asList(daniel, dalton, mauro, aurelio, matheus);
		
		EstadoSemestre inicial = new EstadoSemestre(professores, dias, disciplinas);
		Nodo nodoFinal = new BuscaLargura(new MostraStatusConsole()).busca(inicial);
		System.out.println(nodoFinal.montaCaminho());
	}
}
