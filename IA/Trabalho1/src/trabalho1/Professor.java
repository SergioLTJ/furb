package trabalho1;

import java.util.ArrayList;

public class Professor {

	private String nome;
	private ArrayList<Disciplina> disciplinasAptoMinistrar;

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ArrayList<Disciplina> getDisciplinasAptoMinistrar() {
		return disciplinasAptoMinistrar;
	}
	
	public void addDisciplinasAptoMinistrar(Disciplina disciplina) {
		this.disciplinasAptoMinistrar.add(disciplina);
	}
	
	public boolean podeMinistrarDisciplina(Disciplina disciplina) {
		return disciplinasAptoMinistrar.contains(disciplina);
	}
	
}