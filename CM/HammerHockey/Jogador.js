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
		
		this.desenharMenu(contexto);
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
		
		contexto.fillRect(this.xMenu, this.yMenu, largura, altura);
		contexto.strokeRect(this.xMenu, this.yMenu, largura, altura);
		
		contexto.restore();
	}

	this.verificarClique = function(evento) {
		
	}

	this.definirPosicoesMenu = function() {
		var xPrimeiraColuna = configuracoes.primeiraColunaPreenchida * configuracoes.TAMANHO_CELULA;
		var yPrimeiraLinha = configuracoes.primeiraLinhaPreenchida * configuracoes.TAMANHO_CELULA - (configuracoes.ALTURA_MENU + 5);
		var xSegundaColuna = (configuracoes.ultimaColunaPreenchida + 1) * configuracoes.TAMANHO_CELULA - (configuracoes.LARGURA_MENU);
		var ySegundaLinha = (configuracoes.ultimaLinhaPreenchida + 1) * configuracoes.TAMANHO_CELULA + 5;
		
		switch (this.posicao) {
			case 0:
				this.xMenu = xPrimeiraColuna;
				this.yMenu = yPrimeiraLinha;
				return;
			case 1:
				this.xMenu = xSegundaColuna;
				this.y = yPrimeiraLinha;
				return;
			case 2:
				this.xMenu = xPrimeiraColuna;
				this.yMenu = ySegundaLinha;
				return;
			case 3:
				this.xMenu = xSegundaColuna;
				this.yMenu = ySegundaLinha;
				return;
		}
	}
	
	this.posicaoX = this.definirX(this.celulaAtual);
	this.posicaoY = this.definirY(this.celulaAtual);

	this.gerarNovoIdentificador();
	this.definirPosicoesMenu();
}