function Celula(x, y, ordem) {
	this.x = x;
	this.y = y;
	this.ordem = ordem;
	this.sucessor = null;

	this.jogadores = [ ];

	this.desenhar = function(contexto) {
		contexto.save();

		contexto.fillStyle = 'green';

		if (this.sucessor == null) {
			contexto.fillRect(
				configuracoes.TAMANHO_CELULA * this.x,
				configuracoes.TAMANHO_CELULA * this.y, 
				configuracoes.TAMANHO_CELULA, 
				configuracoes.TAMANHO_CELULA
			);
		}

		contexto.strokeRect(
			configuracoes.TAMANHO_CELULA * this.x,
			configuracoes.TAMANHO_CELULA * this.y, 
			configuracoes.TAMANHO_CELULA, 
			configuracoes.TAMANHO_CELULA
		);

		contexto.restore();

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