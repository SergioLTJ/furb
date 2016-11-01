var idJogador = 0;

function Jogador(celula, posicao, cor) {
	this.celulaAtual = celula;
	this.posicao = posicao;
	this.cor = cor;

	this.xPrimeiraColuna = configuracoes.primeiraColunaPreenchida * configuracoes.TAMANHO_CELULA;
	this.yPrimeiraLinha = configuracoes.primeiraLinhaPreenchida * configuracoes.TAMANHO_CELULA - (configuracoes.ALTURA_MENU + 5);
	this.xSegundaColuna = (configuracoes.ultimaColunaPreenchida + 1) * configuracoes.TAMANHO_CELULA - (configuracoes.LARGURA_MENU);
	this.ySegundaLinha = (configuracoes.ultimaLinhaPreenchida + 1) * configuracoes.TAMANHO_CELULA + 5;
	
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
			this.posicao == 2) {
			return (celula.x * configuracoes.TAMANHO_CELULA) + (umQuartoCelula);
		} else {
			return (celula.x * configuracoes.TAMANHO_CELULA) + (configuracoes.TAMANHO_CELULA - umQuartoCelula);
		}
	}

	this.definirY = function(celula) {
		var umQuartoCelula = configuracoes.TAMANHO_CELULA / 4;
		
		if (this.posicao == 0 || 
			this.posicao == 1) {
			return (celula.y * configuracoes.TAMANHO_CELULA) + (umQuartoCelula);
		} else {
			return (celula.y * configuracoes.TAMANHO_CELULA) + (configuracoes.TAMANHO_CELULA - umQuartoCelula);
		}
	}

	this.gerarNovoIdentificador = function() {
		this.identificador = ++idJogador;
	}

	this.desenharMenu = function(contexto) {
		contexto.save();
		
		contexto.fillStyle = this.cor;
		
		var altura = configuracoes.ALTURA_MENU;
		var largura = configuracoes.LARGURA_MENU;
		
		switch (this.posicao) {
			case 0:
				contexto.fillRect(this.xPrimeiraColuna, this.yPrimeiraLinha, largura, altura);
				contexto.strokeRect(this.xPrimeiraColuna, this.yPrimeiraLinha, largura, altura);
				break;
			case 1:
				contexto.fillRect(this.xSegundaColuna, this.yPrimeiraLinha, largura, altura);
				contexto.strokeRect(this.xSegundaColuna, this.yPrimeiraLinha, largura, altura);
				break;
			case 2:
				contexto.fillRect(this.xPrimeiraColuna, this.ySegundaLinha, largura, altura);
				contexto.strokeRect(this.xPrimeiraColuna, this.ySegundaLinha, largura, altura);
				break;
			case 3:
				contexto.fillRect(this.xSegundaColuna, this.ySegundaLinha, largura, altura);
				contexto.strokeRect(this.xSegundaColuna, this.ySegundaLinha, largura, altura);
				break;
		}
		
		contexto.restore();
	}

	this.verificarClique = function(evento) {
		
	}
	
	this.posicaoX = this.definirX(this.celulaAtual);
	this.posicaoY = this.definirY(this.celulaAtual);

	this.gerarNovoIdentificador();
}