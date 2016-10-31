var idJogador = 0;

function Jogador(celula, posicao, cor) {
	this.celulaAtual = celula;
	this.posicao = posicao;
	this.cor = cor;

	this.desenhar = function (contexto) {
		contexto.fillStyle = this.cor;

		contexto.beginPath();
		contexto.arc(this.posicaoX, this.posicaoY, configuracoes.TAMANHO_JOGADOR / 2, 0, 2 * Math.PI);
		contexto.fill();
	}

	this.mover = function (novaCelula, contexto, tabuleiro) {
		this.celulaAtual = novaCelula;
		this.posicaoX = this.definirX(novaCelula);
		this.posicaoY = this.definirY(novaCelula);
	}

	this.definirX = function(celula) {
		var umQuartoCelula = configuracoes.TAMANHO_CELULA / 4;
		if (this.posicao == 0 || 
			this.posicao == 2) 
			return (celula.x * configuracoes.TAMANHO_CELULA) + (umQuartoCelula);
		else 
			return (celula.x * configuracoes.TAMANHO_CELULA) + (configuracoes.TAMANHO_CELULA - umQuartoCelula);
	}

	this.definirY = function(celula) {
		var umQuartoCelula = configuracoes.TAMANHO_CELULA / 4;
		if (this.posicao == 0 || 
			this.posicao == 1) 
			return (celula.y * configuracoes.TAMANHO_CELULA) + (umQuartoCelula);
		else 
			return (celula.y * configuracoes.TAMANHO_CELULA) + (configuracoes.TAMANHO_CELULA - umQuartoCelula);
	}

	this.gerarNovoIdentificador = function() {
		this.identificador = ++idJogador;
	}

	this.desenharMenu = function(contexto) {
		switch (this.posicao) {
			case 0:
				contexto.rect(configuracoes.primeiraColunaPreenchida * configuracoes.TAMANHO_CELULA, configuracoes.primeiraLinhaPreenchida * configuracoes.TAMANHO_CELULA - 11, 10, 10);
				return;
			case 1:
				return;
			case 2:
				return;
			case 3:
				return;
		}		
	}

	this.verificarClique = function(evento) {
		
	}
	
	this.posicaoX = this.definirX(this.celulaAtual);
	this.posicaoY = this.definirY(this.celulaAtual);

	this.identificador = this.gerarNovoIdentificador();
}