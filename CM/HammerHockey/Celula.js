function Celula(x, y, ordem) {
	this.x = x;
	this.y = y;
	this.ordem = ordem;
	this.sucessor = null;

	this.jogadores = [ ];

	this.desenhar = function(contexto) {
		contexto.save();

		contexto.fillStyle = 'green';

		var xTopoEsquerdoCelula = configuracoes.TAMANHO_CELULA * this.x;
		var yTopoEsquerdoCelula = configuracoes.TAMANHO_CELULA * this.y;
		
		if (this.sucessor == null) {
			contexto.fillRect(
				xTopoEsquerdoCelula,
				yTopoEsquerdoCelula, 
				configuracoes.TAMANHO_CELULA, 
				configuracoes.TAMANHO_CELULA
			);
		}

		contexto.strokeRect(
			xTopoEsquerdoCelula,
			yTopoEsquerdoCelula, 
			configuracoes.TAMANHO_CELULA, 
			configuracoes.TAMANHO_CELULA
		);

		contexto.fillStyle = 'black';
		contexto.textAlign = 'center';
		contexto.font = '20px Arial';
		contexto.fillText(this.ordem, xTopoEsquerdoCelula + configuracoes.TAMANHO_CELULA / 2, yTopoEsquerdoCelula + configuracoes.TAMANHO_CELULA / 2 + 5);
		
		contexto.restore();
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