package trabalho1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import busca.Estado;

public class EstadoSemestre implements Estado {

	private float fitness;

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

		if (!todosProfessoresTemDiaLivre())
			return false;
		if (!professoresSoTemUmaDisciplinaPorHorario())
			return false;

		return true;
	}

	private boolean professoresSoTemUmaDisciplinaPorHorario() {
		for (Professor professor : professores) {
			List<Dia> diasAula = semestres.get(0).obterDiasProfessor(professor, true);

			for (int i = 1; i < this.semestres.size(); i++) {
				Semestre semestre = this.semestres.get(i);
				boolean horarioValido = semestre.validarHorariosProfessor(diasAula, professor);
				if (!horarioValido) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean todosProfessoresTemDiaLivre() {
		for (Professor professor : this.professores) {
			List<Dia> diasLivres = semestres.get(0).obterDiasProfessor(professor, false);

			for (int i = 1; i < this.semestres.size(); i++) {
				this.semestres.get(i).validarDiasLivres(diasLivres, professor);
				if (diasLivres.isEmpty()) {
					return false;
				}
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
		for (Semestre semestre : this.semestres) {
			if (!semestre.estaCompleto()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Estado> sucessores() {
		Semestre incompleto = obterPrimeiroSemestreIncompleto();
		int indiceSemestre = this.semestres.indexOf(incompleto);

		List<Semestre> semestres = incompleto.gerarEstadosSemestre(this.professores);
		List<Estado> estados = new ArrayList<>();

		for (Semestre semestre : semestres) {
			List<Semestre> copiaSemestres = Utils.clonarLista(this.semestres);
			copiaSemestres.set(indiceSemestre, semestre);

			EstadoSemestre novo = new EstadoSemestre(this.professores, copiaSemestres);

			if (novo.ehValido()) {
				estados.add(novo);
			}
		}

		return estados;
	}

	private Semestre obterPrimeiroSemestreIncompleto() {
		for (Semestre semestre : this.semestres) {
			if (!semestre.estaCompleto()) {
				return semestre;
			}
		}
		return null;
	}

	public List<Professor> getProfessores() {
		return this.professores;
	}

	public List<Semestre> getSemestres() {
		return this.semestres;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof EstadoSemestre))
			return false;

		EstadoSemestre outro = (EstadoSemestre) obj;

		if (!semestresIguais(outro))
			return false;

		return true;
	}

	private boolean semestresIguais(EstadoSemestre outro) {
		for (int i = 0; i < this.semestres.size(); i++) {
			if (!this.semestres.get(i).equals(outro.getSemestres().get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 17;

		for (Semestre semestre : this.semestres) {
			hash = 31 * hash + semestre.hashCode();
		}

		return hash;
	}

	@Override
	public String toString() {
		String descricaoEstado = "";

		for (Semestre semestre : this.semestres) {
			descricaoEstado += semestre.toString();
		}

		descricaoEstado += "\n\n";

		return descricaoEstado;
	}

	public static void main(String[] args) {
		List<EstadoSemestre> estados = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			estados.add(new Teste().criarEstadoInicial(7).preecherEstadoAleatorio());
		}

		EstadoSemestre estadoFinal = tentarEncontrarEstadoFinal(estados);
	}

	private static EstadoSemestre tentarEncontrarEstadoFinal(List<EstadoSemestre> estados) {
		int numero = 0;
		while (true) {
			for (EstadoSemestre estadoSemestre : estados) {
				estadoSemestre.calcularFitness();

				if (estadoSemestre.getFitness() == 106) {
					estadoSemestre.setFitness(numero);
					return estadoSemestre;
				}
			}

			float fitnessTotal = 0;

			for (EstadoSemestre estadoSemestre : estados) {
				fitnessTotal += estadoSemestre.getFitness();
			}

			for (EstadoSemestre estadoSemestre : estados) {
				estadoSemestre.setFitness(estadoSemestre.getFitness() / fitnessTotal);
			}

			for (int i = 1; i < estados.size(); i++) {
				EstadoSemestre atual = estados.get(i);
				atual.setFitness(atual.getFitness() + estados.get(i - 1).getFitness());
			}

			List<EstadoSemestre> filhos = new ArrayList<>();
			// filhos.add(determinarEstadoMaiorFitness(estados));

			for (int i = 0; i < estados.size(); i++) {
				EstadoSemestre umPai = encontrarPai(estados);
				EstadoSemestre outroPai = encontrarPai(estados);
				filhos.add(combinar(umPai, outroPai));
			}

			estados = filhos;
			numero++;
		}
	}

	private static EstadoSemestre determinarEstadoMaiorFitness(List<EstadoSemestre> estados) {
		EstadoSemestre maior = estados.get(0);
		for (int i = 1; i < estados.size(); i++) {
			if (estados.get(i).getFitness() > maior.getFitness())
				maior = estados.get(i);
		}
		return maior;
	}

	private static EstadoSemestre encontrarPai(List<EstadoSemestre> estados) {
		Random r = new Random();
		float valor = r.nextFloat() * 1.0f;

		EstadoSemestre pai = null;

		for (EstadoSemestre estado : estados) {
			if (estado.getFitness() > valor) {
				pai = estado;
				break;
			}
		}

		if (pai == null)
			pai = estados.get(estados.size() - 1);

		return pai;
	}

	private static EstadoSemestre combinar(EstadoSemestre umPai, EstadoSemestre outroPai) {
		
		
		
		// Combinação de dias, gera problemas pois as semanas dos semestres filhos não são validadas,
		// gerando combinações inválidas onde as matérias são repetidas mais vezes que o permitido em
		// uma semana.
		
		// List<Semestre> semestresFilho = new ArrayList<>();
		// for (int i = 1; i <= 7; i++) {
		// Semestre semestreUmPai = umPai.obterSemestrePorNumero(i);
		// Semestre semestreOutroPai = outroPai.obterSemestrePorNumero(i);
		// Semestre semestre = combinarSemestres(semestreUmPai, semestreOutroPai);
		// semestresFilho.add(semestre);
		// }

		
		// Combinação semestres, corte único, muito difícil de gerar um estado perfeito com poucos estados 
		// iniciais (aleatórios) pois não existe nos estados iniciais uma combinação de semestres que combinados 
		// gerem um estado válido.
		
		// int semestreCorte = new Random().nextInt(5) + 1;
		//
		// List<Semestre> semestresFilho = new ArrayList<>();
		// semestresFilho = adicionarSemestresFilho(semestresFilho, umPai, 0, semestreCorte);
		// semestresFilho = adicionarSemestresFilho(semestresFilho, outroPai, semestreCorte, outroPai.getSemestres().size());
		 
		EstadoSemestre filho = new EstadoSemestre(Utils.clonarLista(umPai.getProfessores()), semestresFilho);

		return filho;
	}

	private static Semestre combinarSemestres(Semestre semestreUmPai, Semestre semestreOutroPai) {
		Random r = new Random();
		List<Dia> dias = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Semestre semestre = r.nextInt(2) == 1 ? semestreUmPai : semestreOutroPai;
			dias.add(semestre.getDias().get(i));
		}
		return new Semestre(semestreUmPai.getNumero(), dias, semestreUmPai.getDisciplinas());
	}

	private static List<Semestre> adicionarSemestresFilho(List<Semestre> semestresFilho, EstadoSemestre pai, int primeiro, int ultimo) {
		for (int i = primeiro; i < ultimo; i++) {
			semestresFilho.add(pai.getSemestres().get(i));
		}

		return semestresFilho;
	}

	private void calcularFitness() {
		float fitness = 106;
		for (Semestre semestre : this.semestres) {
			fitness += semestre.calcularFitness(Utils.clonarLista(this.semestres));
		}

		if (!todosProfessoresTemDiaLivre())
			fitness -= 1;

		this.setFitness(fitness);
	}

	private EstadoSemestre preecherEstadoAleatorio() {
		for (Semestre semestre : this.semestres) {
			semestre.preencherAleatorio(this.professores);
		}

		return this;
	}

	public Semestre obterSemestrePorNumero(int numeroSemestre) {
		for (Semestre semestre : this.semestres) {
			if (semestre.getNumero() == numeroSemestre)
				return semestre;
		}
		return null;
	}

	public float getFitness() {
		return this.fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
}
