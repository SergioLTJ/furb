package com.furb.ia.trabalhofinal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.furb.ia.trabalhofinal.dados.Dia;
import com.furb.ia.trabalhofinal.dados.Professor;
import com.furb.ia.trabalhofinal.dados.Semestre;
import com.furb.ia.trabalhofinal.dados.TipoGeracaoEstado;
import com.furb.ia.trabalhofinal.geracaoestados.GeradorEstadosBase;
import com.furb.ia.trabalhofinal.geracaoestados.GeradorEstadosFactory;

public class EstadoSemestre {

	private float fitness;

	private List<Professor> professores;
	private List<Semestre> semestres;

	private static int execucao = 0;
	private static long tempoInicial = 0;
	
	public EstadoSemestre(List<Professor> professores, List<Semestre> semestres) {
		this.professores = professores;
		this.semestres = semestres;
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

	public static void main(String[] args) throws IOException{
		// Geração aleatória
		
		// for (int i = 0; i < 10; i++) {
		// boolean parar = false;
		// int geracao = 0;
		// long tempoInicial = System.nanoTime();
		// while (!parar) {
		// geracao++;
		// List<EstadoSemestre> estados = new ArrayList<>();
		//
		// for (int j = 0; j < 100; j++) {
		// estados.add(new Teste().criarEstadoInicial(7).preecherEstadoAleatorio());
		// }
		//
		// for (EstadoSemestre estadoSemestre : estados) {
		// estadoSemestre.calcularFitness();
		//
		// if (estadoSemestre.getFitness() == 106) {
		// long tempoFinal = System.nanoTime();
		// System.out.println("--- Execução " + (i + 1) + " ---");
		// System.out.println(tempoFinal - tempoInicial);
		// System.out.println(geracao);
		// System.out.println("--------------------------------");
		// parar = true;
		// }
		// }
		// }
		// }
		//
		// System.in.read();
		
		Scanner scanner = new Scanner(System.in);
		
		String tipoGeracao = obterTipoGeracaoEstados(scanner);
		int numeroSemestres = obterNumeroSemestres(scanner);
		
		scanner.close();
		
		TipoGeracaoEstado tipoGerador = tipoGeracao.equals("0") 
						      				? TipoGeracaoEstado.COMBINACAO_DIAS 
						      				: TipoGeracaoEstado.COMBINACAO_SEMESTRES;
		
		GeradorEstadosBase gerador = new GeradorEstadosFactory().criar(tipoGerador, numeroSemestres);
		
		tempoInicial = System.nanoTime();
		
		while (execucao < 10) {
			List<EstadoSemestre> estados = new ArrayList<>();
			
			for (int i = 0; i < 100; i++) {
				estados.add(new Teste().criarEstadoInicial(numeroSemestres).preecherEstadoAleatorio());
			}

			EstadoSemestre estadoFinal = tentarEncontrarEstadoFinal(estados, gerador);
			
			if (estadoFinal != null) {
				File arquivo = new File("C:\\Teste\\Execucao" + (execucao + 1) + ".html");
				
				if (!arquivo.exists()) arquivo.createNewFile();

				try (FileOutputStream fos = new FileOutputStream(arquivo, false)) {
					try (OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
						osw.write(estadoFinal.paraHtml());
					}
				}
			}
		}
	}

	private static String obterTipoGeracaoEstados(Scanner scanner) {
		System.out.println("Como devem ser gerados os estados?");
		System.out.println("0 - Combinação de dias");
		System.out.println("1 - Combinação de semestres");
		
		String valor = "";
		while (true) {
			valor = scanner.nextLine();
			
			if (valor.equals("0") || valor.equals("1"))
				break;
			else 
				System.out.println("O valor informado não é válido, por favor insira um dos valores: 0, 1.");
		}
		return valor;
	}

	private static int obterNumeroSemestres(Scanner scanner) {
		int valor = -1;
		while (true) {
			System.out.print("Insira o número de semestres (deve ser um número entre 1 e 7) : ");
			
			String linha = scanner.nextLine();
			try {
				valor = Integer.parseInt(linha);

				if (valor > 0 && valor < 8)
					break;
				else
					System.out.println("O valor informado não é válido, por favor insira um número entre 1 e 7.");

			} catch (NumberFormatException ex) {
				System.out.println("O valor informado não é um número válido.");
			}
		}
		return valor;
	}
	
	private static EstadoSemestre tentarEncontrarEstadoFinal(List<EstadoSemestre> estados, GeradorEstadosBase geradorEstados) {
		int geracao = 0;
		while (true) {
			geracao++;
			
			if (geracao == 51) return null;
			
			for (EstadoSemestre estadoSemestre : estados) {
				estadoSemestre.calcularFitness();

				if (estadoSemestre.getFitness() == 246) {
					long tempoFinal = System.nanoTime();
					System.out.println("--- Execução " + (execucao + 1) + " ---");
					System.out.println(tempoFinal - tempoInicial);
					System.out.println(geracao);
					System.out.println("--------------------------------");
					execucao++;
					tempoInicial = System.nanoTime();
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
			
			estados = geradorEstados.processar(estados);
		}
	}

	private void calcularFitness() {
		float fitness = 246;
		
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
	
	public String paraHtml() {
		String html = "<!DOCTYPE html><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap.min.css\"><title>Grade</title><meta charset=\"UTF-8\"></head><body><div class=\"container container-fluid\">";
		
		for (Semestre semestre : this.semestres) {
			html += semestre.paraHtml() + "<br><hr>";
		}
		
		return html + "</div></body>";
	}
}
