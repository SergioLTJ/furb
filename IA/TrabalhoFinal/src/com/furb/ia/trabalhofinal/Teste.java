package com.furb.ia.trabalhofinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.furb.ia.trabalhofinal.dados.CargaSemanalDisciplina;
import com.furb.ia.trabalhofinal.dados.Disciplina;
import com.furb.ia.trabalhofinal.dados.Professor;
import com.furb.ia.trabalhofinal.dados.Semestre;

public class Teste {

	private HashMap<String, Professor> professores;
	private HashMap<Integer, List<Disciplina>> mapaDisciplinasSemestre;
	private HashMap<Integer, List<Professor>> mapaProfessoresSemestre;

	public Teste() {
		professores = new HashMap<>();
		
		professores.put("Daniel", new Professor("Daniel"));
		professores.put("Dalton", new Professor("Dalton"));
		professores.put("Mauro", new Professor("Mauro"));
		professores.put("Aur�lio", new Professor("Aur�lio"));
		professores.put("Matheus", new Professor("Matheus"));
		professores.put("Ratike", new Professor("Ratike"));
		professores.put("Jairson", new Professor("Jairson"));
		professores.put("Jacques", new Professor("Jacques"));
		professores.put("Paulo", new Professor("Paulo"));
		professores.put("P�ricas", new Professor("P�ricas"));
		professores.put("Pedro", new Professor("Pedro"));
		professores.put("Joyce", new Professor("Joyce"));
		professores.put("Luciana", new Professor("Luciana"));
		professores.put("Miguel", new Professor("Miguel"));
		professores.put("Lamar", new Professor("Lamar"));
		professores.put("Evandro", new Professor("Evandro"));
		professores.put("Waldir", new Professor("Waldir"));
		professores.put("Marcel", new Professor("Marcel"));
		professores.put("Tavares", new Professor("Tavares"));
		professores.put("Gilvan", new Professor("Gilvan"));
		professores.put("Henriete", new Professor("Henriete"));
		professores.put("Gabriele", new Professor("Gabriele"));
		professores.put("Cintia", new Professor("Cintia"));
		professores.put("Neumann", new Professor("Neumann"));

		mapaProfessoresSemestre = new HashMap<>();
		mapaProfessoresSemestre.put(1, professoresPrimeiroSemestre());
		mapaProfessoresSemestre.put(2, professoresSegundoSemestre());
		mapaProfessoresSemestre.put(3, professoresTerceiroSemestre());
		mapaProfessoresSemestre.put(4, professoresQuartoSemestre());
		mapaProfessoresSemestre.put(5, professoresQuintoSemestre());
		mapaProfessoresSemestre.put(6, professoresSextoSemestre());
		mapaProfessoresSemestre.put(7, professoresSetimoSemestre());
		
		mapaDisciplinasSemestre = new HashMap<>();
		
		criarDisciplinasPrimeiroSemestre();
		criarDisciplinasSegundoSemestre();
		criarDisciplinasTerceiroSemestre();
		criarDisciplinasQuartoSemestre();
		criarDisciplinasQuintoSemestre();
		criarDisciplinasSextoSemestre();
		criarDisciplinasSetimoSemestre();
	}
	
	private List<Professor> professoresPrimeiroSemestre() {
		return Arrays.asList(professores.get("Miguel"), professores.get("Evandro"), professores.get("Mauro"), professores.get("Aur�lio"), professores.get("Lamar"));
	}
	
	private List<Professor> professoresSegundoSemestre() {
		return Arrays.asList(professores.get("Waldir"), professores.get("Joyce"), professores.get("Marcel"));
	}

	private List<Professor> professoresTerceiroSemestre() {
		return Arrays.asList(professores.get("Tavares"), professores.get("Henriete"), professores.get("Gilvan"), professores.get("Gabriele"));
	}

	private List<Professor> professoresQuartoSemestre() {
		return Arrays.asList(professores.get("Cintia"), professores.get("Luciana"), professores.get("Neumann"), professores.get("P�ricas"));
	}

	private List<Professor> professoresQuintoSemestre() {
		return Arrays.asList(professores.get("Paulo"), professores.get("Pedro"));
	}

	private List<Professor> professoresSextoSemestre() {
		return Arrays.asList(professores.get("Ratike"), professores.get("Jairson"), professores.get("Jacques"));
	}

	private List<Professor> professoresSetimoSemestre() {
		return Arrays.asList(professores.get("Daniel"), professores.get("Matheus"), professores.get("Dalton"));
	}
	
	private void criarDisciplinasPrimeiroSemestre() {
		Disciplina compDig = CriarDisciplina("Computa��o Digital", CargaSemanalDisciplina.QUATRO_HORAS, "Miguel");
		Disciplina fundMat = CriarDisciplina("Fundamentos Matem�ticos", CargaSemanalDisciplina.QUATRO_HORAS, "Evandro");
		Disciplina intComp = CriarDisciplina("Introdu��o a Computa��o", CargaSemanalDisciplina.QUATRO_HORAS, "Mauro");
		Disciplina progComp = CriarDisciplina("Programa��o de Computadores", CargaSemanalDisciplina.QUATRO_HORAS, "Aur�lio");
		Disciplina univ = CriarDisciplina("Universidade Ci�ncia e Pesquisa", CargaSemanalDisciplina.QUATRO_HORAS, "Lamar");
		
		List<Disciplina> disciplinasPrimeiro = Arrays.asList(compDig, fundMat, intComp, progComp, univ);

		mapaDisciplinasSemestre.put(1, disciplinasPrimeiro);
	}
	
	private void criarDisciplinasSegundoSemestre() {
		Disciplina algebLin = CriarDisciplina("�lgebra Linear", CargaSemanalDisciplina.QUATRO_HORAS, "Evandro");
		Disciplina arqComp = CriarDisciplina("Arquitetura de Computadores", CargaSemanalDisciplina.QUATRO_HORAS, "Miguel");
		Disciplina lingCien = CriarDisciplina("Linguagem Cient�fica", CargaSemanalDisciplina.QUATRO_HORAS, "Waldir");
		Disciplina logicComp = CriarDisciplina("L�gica para Computa��o", CargaSemanalDisciplina.QUATRO_HORAS, "Joyce");
		Disciplina progObj1 = CriarDisciplina("Programa��o Orientada a Objetos I", CargaSemanalDisciplina.QUATRO_HORAS, "Marcel");
		
		List<Disciplina> disciplinasSegundo = Arrays.asList(algebLin, arqComp, lingCien, logicComp, progObj1);
		
		mapaDisciplinasSemestre.put(2, disciplinasSegundo);
	}

	private void criarDisciplinasTerceiroSemestre() {
		Disciplina algEstDad = CriarDisciplina("Algoritmos e Estruturas de Dados", CargaSemanalDisciplina.QUATRO_HORAS, "Tavares");
		Disciplina estatistica = CriarDisciplina("Estat�stica", CargaSemanalDisciplina.QUATRO_HORAS, "Henriete");
		Disciplina progObj2 = CriarDisciplina("Programa��o Orientada a Objetos II", CargaSemanalDisciplina.QUATRO_HORAS, "Gilvan");
		Disciplina sisOp = CriarDisciplina("Sistemas Operacionais", CargaSemanalDisciplina.QUATRO_HORAS, "Tavares");
		Disciplina teoriComp = CriarDisciplina("Teoria da Computa��o", CargaSemanalDisciplina.QUATRO_HORAS, "Gabriele");
		
		List<Disciplina> disciplinasTerceiro = Arrays.asList(algEstDad, estatistica, progObj2, sisOp, teoriComp);
		
		mapaDisciplinasSemestre.put(3, disciplinasTerceiro);
	}

	private void criarDisciplinasQuartoSemestre() {
		Disciplina desSoc = CriarDisciplina("Desafios Sociais e Contempor�neos", CargaSemanalDisciplina.QUATRO_HORAS, "Cintia");
		Disciplina lingProg = CriarDisciplina("Linguagens de Programa��o", CargaSemanalDisciplina.QUATRO_HORAS, "Luciana");
		Disciplina lingForm = CriarDisciplina("Linguagens Formais", CargaSemanalDisciplina.DUAS_HORAS, "Joyce");
		Disciplina metQuant = CriarDisciplina("M�todos Quantitativos", CargaSemanalDisciplina.QUATRO_HORAS, "Neumann");
		Disciplina protComDad = CriarDisciplina("Protocolos de Comunica��o de Dados", CargaSemanalDisciplina.DUAS_HORAS, "P�ricas");
		Disciplina grafos = CriarDisciplina("Teoria dos Grafos", CargaSemanalDisciplina.QUATRO_HORAS, "Aur�lio");
		
		List<Disciplina> disciplinasQuarto = Arrays.asList(desSoc, lingProg, lingForm, metQuant, protComDad, grafos);
		
		mapaDisciplinasSemestre.put(4, disciplinasQuarto);
	}

	private void criarDisciplinasQuintoSemestre() {
		Disciplina bd1 = CriarDisciplina("Banco de Dados I", CargaSemanalDisciplina.QUATRO_HORAS, "Luciana");
		Disciplina compiladores = CriarDisciplina("Compiladores", CargaSemanalDisciplina.QUATRO_HORAS, "Joyce");
		Disciplina concorr = CriarDisciplina("Gal�xias", CargaSemanalDisciplina.QUATRO_HORAS, "Paulo");
		Disciplina engSoft = CriarDisciplina("Engenharia de Software", CargaSemanalDisciplina.QUATRO_HORAS, "Pedro");
		Disciplina redes = CriarDisciplina("Redes de Computadores", CargaSemanalDisciplina.QUATRO_HORAS, "P�ricas");
		
		List<Disciplina> disciplinasQuinto = Arrays.asList(bd1, compiladores, concorr, engSoft, redes);
		
		mapaDisciplinasSemestre.put(5, disciplinasQuinto);
	}

	private void criarDisciplinasSextoSemestre() {
		Disciplina bd2 = CriarDisciplina("Banco de Dados II", CargaSemanalDisciplina.QUATRO_HORAS, "Ratike");
		Disciplina compOrg = CriarDisciplina("Comportamento Organizacional", CargaSemanalDisciplina.QUATRO_HORAS, "Jairson");
		Disciplina ps1 = CriarDisciplina("Processos de Software I", CargaSemanalDisciplina.QUATRO_HORAS, "Jacques");
		Disciplina sisDist = CriarDisciplina("Sistemas Distribu�dos", CargaSemanalDisciplina.QUATRO_HORAS, "Paulo");
		Disciplina prRedes = CriarDisciplina("Pr�tica de Redes", CargaSemanalDisciplina.QUATRO_HORAS, "P�ricas");
		
		List<Disciplina> disciplinasSexto = Arrays.asList(bd2, compOrg, ps1, sisDist, prRedes);
		
		mapaDisciplinasSemestre.put(6, disciplinasSexto);
	}

	private void criarDisciplinasSetimoSemestre() {
		Disciplina ia = CriarDisciplina("Intelig�ncia Artificial", CargaSemanalDisciplina.QUATRO_HORAS, "Daniel");
		Disciplina ps2 = CriarDisciplina("Processos de Software II", CargaSemanalDisciplina.QUATRO_HORAS, "Mauro");
		Disciplina rob = CriarDisciplina("Rob�tica", CargaSemanalDisciplina.QUATRO_HORAS, "Aur�lio");
		Disciplina web = CriarDisciplina("Desenvolvimento Web", CargaSemanalDisciplina.QUATRO_HORAS, "Matheus");
		Disciplina cg = CriarDisciplina("Computa��o Gr�fica", CargaSemanalDisciplina.QUATRO_HORAS, "Dalton");
		
		List<Disciplina> disciplinasSetimo = Arrays.asList(ia, ps2, rob, web, cg);
		
		mapaDisciplinasSemestre.put(7, disciplinasSetimo);
	}

	private Disciplina CriarDisciplina(String nome, CargaSemanalDisciplina carga, String... nomeProfessores) {
		Disciplina disciplina = new Disciplina(nome, carga);
		for (String nomeProfessor : nomeProfessores) {
			professores.get(nomeProfessor).addDisciplinasAptoMinistrar(disciplina);
		}
		return disciplina;
	}
	
	public EstadoSemestre criarEstadoInicial(int numeroSemestres) {
		List<Semestre> semestres = new ArrayList<>();
		
		for (int i = 1; i <= numeroSemestres; i++) {
			Semestre semestre = new Semestre(i, mapaDisciplinasSemestre.get(i));
			semestres.add(semestre);
		}

		List<Professor> professores = criarListaProfessores(numeroSemestres);
		
		EstadoSemestre estadoInicial = new EstadoSemestre(professores, semestres);
		return estadoInicial;
	}

	private List<Professor> criarListaProfessores(int numeroSemestres) {
		List<Professor> professores = new ArrayList<Professor>();
		
		for (int i = 1; i <= numeroSemestres; i++) {
			professores.addAll(mapaProfessoresSemestre.get(i));
		}
		
		return professores;
	}
	
}
