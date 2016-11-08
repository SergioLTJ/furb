var idJogador = 0;

function Jogador(celula, posicao, cor) {
	this.celulaAtual = celula;
	this.posicao = posicao;
	this.cor = cor;

	this.corMenu = cor;
	this.bufferCor = 'yellow';

	this.texto = 'Jogador ' + (posicao + 1);
	this.bufferTexto = 'Clique para rolar o dado';

	this.estaNoTurno = false;

	this.amarelo = false;
	this.tempoAnimacao = 0;

	this.movimentosRestantes = 0;
	this.tempoMovimentacao = 0;

	this.atualizar = function(jogo) {
		if (this.movimentosRestantes == 0)
			return;

		var data = new Date();
		var tempoMillis = data.getTime();
		if ((tempoMillis - this.tempoMovimentacao) > configuracoes.TEMPO_AVANCO_JOGADOR) 
		{
			this.mover();
			if (this.celulaAtual.sucessor == null) 
			{
				jogo.finalizar(this);
			} 
			else 
			{
				this.movimentosRestantes--;
				this.tempoMovimentacao = tempoMillis;
				if (this.movimentosRestantes == 0)
				{
					jogo.avancarTurno();
				}
			}
		}
	}

	this.desenhar = function (contexto) {
		contexto.fillStyle = this.cor;

		contexto.beginPath();
		contexto.arc(this.posicaoX, this.posicaoY, configuracoes.TAMANHO_JOGADOR / 2, 0, 2 * Math.PI);
		contexto.fill();
		
		this.desenharMenu(contexto);
	}

	this.mover = function () {
		this.celulaAtual = this.celulaAtual.sucessor;
		this.posicaoX = this.definirX(this.celulaAtual);
		this.posicaoY = this.definirY(this.celulaAtual);
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
		contexto.fillText(this.texto, this.xMenu + configuracoes.LARGURA_MENU / 2, this.yMenu + configuracoes.ALTURA_MENU / 2 + 5);

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
				this.corMenu = this.cor;												
				this.texto = 'Jogador ' + (this.posicao + 1);
				this.rolarDado();
			}
		}
	}

	this.entrarTurno = function() {
		this.estaNoTurno = true;
		this.tempoAnimacao = new Date().getTime();
		this.texto = 'Clique para rolar o dado';
	}

	this.rolarDado = function() {
		this.movimentosRestantes = Math.floor((Math.random() * 6) + 1);
		this.tempoMovimentacao = new Date().getTime();
	}

	this.definirPosicoesMenu = function() {
		var xPrimeiraColuna = configuracoes.primeiraColunaPreenchida * configuracoes.TAMANHO_CELULA;
		var yPrimeiraLinha = configuracoes.primeiraLinhaPreenchida * configuracoes.TAMANHO_CELULA - (configuracoes.ALTURA_MENU + configuracoes.DISTANCIA_MENU_TABULEIRO);
		var xSegundaColuna = (configuracoes.ultimaColunaPreenchida + 1) * configuracoes.TAMANHO_CELULA - (configuracoes.LARGURA_MENU);
		var ySegundaLinha = (configuracoes.ultimaLinhaPreenchida + 1) * configuracoes.TAMANHO_CELULA + configuracoes.DISTANCIA_MENU_TABULEIRO;
		
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