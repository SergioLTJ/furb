package trabalho1;

public class Disciplina {

	private String nome;
	private Professor professorMinistrando;
	private int cargaSemanal;
	
	public Disciplina(String nome, CargaSemanalDisciplina carga) {
		this.nome = nome;
		if (carga == CargaSemanalDisciplina.QUATRO_HORAS) {
			this.cargaSemanal = 2;
		} else {
			this.cargaSemanal = 1;
		}
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Professor getProfessorMinistrando() {
		return professorMinistrando;
	}
	
	public void setProfessorMinistrando(Professor professorMinistrando) {
		this.professorMinistrando = professorMinistrando;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Disciplina)) return false;
		
		Disciplina outra = (Disciplina) obj;
		
		if (!this.nome.equals(outra.getNome())) return false;
		if (!this.professorMinistrando.equals(outra.getProfessorMinistrando())) return false;
		
		return true;
	}
	
}