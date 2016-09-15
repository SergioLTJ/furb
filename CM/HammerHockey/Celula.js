function Celula(x, y, ordem) {
	this.x = x;
	this.y = y;
	this.ordem = ordem;
	this.sucessor = null;

	this.jogadores = [ ];

	this.desenhar = function(contexto) {
		contexto.strokeRect(
			configuracoes.TAMANHO_CELULA * this.x,
			configuracoes.TAMANHO_CELULA * this.y, 
			configuracoes.TAMANHO_CELULA, 
			configuracoes.TAMANHO_CELULA
		);

		if (this.jogadores.length > 0) {
			for (var i = 0; i < this.jogadores.length; i++) {
				this.jogadores[i].desenhar(contexto);
			}
		}
	}

	this.avancarJogador = function(posicao, contexto, tabuleiro) {
		var jogador = this.jogadores[posicao];
		jogador.mover(this.sucessor, contexto, tabuleiro);
	}

	this.atribuirJogador = function(jogador) {
		this.jogadores[jogador.posicao] = jogador;
	}	

	this.atribuirSucessor = function(sucessor) {
		this.sucessor = sucessor;
	}

	this.moverJogador = function(posicao) {
		this.jogadores[posicao - 1]
	}
}