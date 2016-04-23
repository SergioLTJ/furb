package labirinto.mapeamentos;

import java.util.Hashtable;

import labirinto.dados.Direcao;

public class MapeamentoDirecoes {

	static {
		inicializarMapeamentos();
	}
	
	private static Hashtable<Direcao, Hashtable<Direcao, Direcao>> mapeamentoMapeamentos;
	private static Hashtable<Direcao, Direcao> mapeamentosLeste;
	private static Hashtable<Direcao, Direcao> mapeamentosOeste;
	private static Hashtable<Direcao, Direcao> mapeamentosNorte;
	private static Hashtable<Direcao, Direcao> mapeamentoReversos;
	private static Hashtable<Direcao, Direcao> mapeamentosSul;
	
	private static void inicializarMapeamentos() {
		mapeamentosLeste = new Hashtable<>();
		mapeamentosLeste.put(Direcao.Norte, Direcao.Leste);
		mapeamentosLeste.put(Direcao.Sul, Direcao.Oeste);
		mapeamentosLeste.put(Direcao.Leste, Direcao.Sul);
		mapeamentosLeste.put(Direcao.Oeste, Direcao.Norte);

		mapeamentosOeste = new Hashtable<>();
		mapeamentosOeste.put(Direcao.Norte, Direcao.Oeste);
		mapeamentosOeste.put(Direcao.Sul, Direcao.Leste);
		mapeamentosOeste.put(Direcao.Leste, Direcao.Norte);
		mapeamentosOeste.put(Direcao.Oeste, Direcao.Sul);
		
		mapeamentosNorte = new Hashtable<>();
		mapeamentosNorte.put(Direcao.Norte, Direcao.Norte);
		mapeamentosNorte.put(Direcao.Sul, Direcao.Sul);
		mapeamentosNorte.put(Direcao.Leste, Direcao.Leste);
		mapeamentosNorte.put(Direcao.Oeste, Direcao.Oeste);

		mapeamentosSul = new Hashtable<>();
		mapeamentosSul.put(Direcao.Norte, Direcao.Sul);
		mapeamentosSul.put(Direcao.Sul, Direcao.Norte);
		mapeamentosSul.put(Direcao.Leste, Direcao.Oeste);
		mapeamentosSul.put(Direcao.Oeste, Direcao.Leste);
		
		mapeamentoMapeamentos = new Hashtable<>();
		mapeamentoMapeamentos.put(Direcao.Norte, mapeamentosNorte);
		mapeamentoMapeamentos.put(Direcao.Sul, mapeamentosSul);
		mapeamentoMapeamentos.put(Direcao.Leste, mapeamentosLeste);
		mapeamentoMapeamentos.put(Direcao.Oeste, mapeamentosOeste);
		
		mapeamentoReversos = new Hashtable<>();
		mapeamentoReversos.put(Direcao.Norte, Direcao.Sul);
		mapeamentoReversos.put(Direcao.Sul, Direcao.Norte);
		mapeamentoReversos.put(Direcao.Oeste, Direcao.Leste);
		mapeamentoReversos.put(Direcao.Leste, Direcao.Oeste);
	}
	
	public static Direcao obterNovaDirecao(Direcao orientacao, Direcao curva) {
		return mapeamentoMapeamentos.get(curva).get(orientacao);
	}
	
	public static Direcao obterReverso(Direcao direcao) {
		return mapeamentoReversos.get(direcao);
	}
	
	public static Direcao definirDirecaoRotacao(Direcao orientacao, Direcao direcaoNovoNodo) {
		switch (direcaoNovoNodo) {
		
		case Oeste:
			return orientacao == Direcao.Norte 
								 ? Direcao.Oeste 
								 : Direcao.Leste;
		
		case Leste:
			return orientacao == Direcao.Norte 
								 ? Direcao.Leste
								 : Direcao.Oeste;
		
		case Norte:
			return orientacao == Direcao.Oeste 
								 ? Direcao.Leste 
								 : Direcao.Oeste;
		
		case Sul:
			return orientacao == Direcao.Oeste 
								 ? Direcao.Oeste 
								 : Direcao.Leste;
			
		}

		return Direcao.Norte;
	}
}
