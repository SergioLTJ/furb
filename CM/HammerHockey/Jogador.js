var idJogador = 0;

function Jogador(celula, posicao, cor) {
	this.celulaAtual = celula;
	this.posicao = posicao;
	this.cor = cor;

	this.corMenu = cor;
	this.bufferCor = 'yellow';

	this.estaNoTurno = false;

	this.amarelo = false;
	this.tempoAnimacao = 0;

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
		
		var data = new Date();
		var tempoMillis = data.getTime();
		if (this.estaNoTurno && 
			(tempoMillis - this.tempoAnimacao) > configuracoes.TEMPO_ANIMACAO_TURNO) {
			this.tempoAnimacao = tempoMillis;
			var corIntermediaria = this.corMenu;
			this.corMenu = this.bufferCor;
			this.bufferCor = corIntermediaria;
		}

		contexto.fillStyle = this.corMenu;
		
		var altura = configuracoes.ALTURA_MENU;
		var largura = configuracoes.LARGURA_MENU;
		
		contexto.fillRect(this.xMenu, this.yMenu, largura, altura);
		contexto.strokeRect(this.xMenu, this.yMenu, largura, altura);
		
		contexto.fillStyle = 'black';
		contexto.textAlign = 'center';
		contexto.font = '16px Arial';
		contexto.fillText("Jogador " + (this.posicao + 1), this.xMenu + configuracoes.LARGURA_MENU / 2, this.yMenu + configuracoes.ALTURA_MENU / 2 + 5);

		contexto.restore();
	}

	this.verificarClique = function(evento, jogo) {
		var altura = configuracoes.ALTURA_MENU;
		var largura = configuracoes.LARGURA_MENU;

		var xInferiorMenu = this.xMenu + largura;
		var yInferiorMenu = this.yMenu + altura;

		if (evento.offsetX > this.xMenu &&
			evento.offsetX < xInferiorMenu &&
			evento.offsetY > this.yMenu &&
			evento.offsetY < yInferiorMenu)
		{
			if (this.estaNoTurno)
			{
				this.estaNoTurno = false;
				this.rolarDado();
			}
		}
	}

	this.entrarTurno = function() {
		var data = new Date();
		this.estaNoTurno = true;
		this.tempoAnimacao = data.getTime();
	}

	this.rolarDado = function() {
		var valor = Math.floor((Math.random() * 5));
		for (var i = 0; i < valor; i++) {
			this.celulaAtual = this.celulaAtual.sucessor;
		}
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
				this.yMenu = yPrimeiraLinha;
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