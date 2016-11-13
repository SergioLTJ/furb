function Jogo(contexto) {

	this.tabuleiro;
	this.jogadores = [];
	this.indiceJogadorAtual = 0;

	this.contexto = contexto;

	this.terminou = false;

	this.iniciar = function() {
		var self = this;
		
		this.tabuleiro = new Tabuleiro(this.contexto);
		this.jogadores.push(this.tabuleiro.adicionarJogador(0, 'red'));
		this.jogadores.push(this.tabuleiro.adicionarJogador(1, 'green'));
		this.jogadores.push(this.tabuleiro.adicionarJogador(2, 'blue'));
		this.jogadores.push(this.tabuleiro.adicionarJogador(3, 'magenta'));
		
		configuracoes.numeroJogadores = 4;

		var canvas = document.getElementById('canvasJogo');
		canvas.onclick = function (evento) { 
			for (var i = 0; i < self.jogadores.length; i++) {
				self.jogadores[i].verificarClique(evento, self);
			}
		};

		this.jogadores[0].entrarTurno();

		this.step();
	}

	this.avancarTurno = function() {
		if (++this.indiceJogadorAtual == configuracoes.numeroJogadores) {
			this.indiceJogadorAtual = 0;
		}
		this.jogadores[this.indiceJogadorAtual].entrarTurno();
	}

	this.step = function() {
		if (!this.terminou)  
		{
			requestAnimationFrame(this.step.bind(this));

			this.tabuleiro.atualizar();
			for (var i = 0; i < this.jogadores.length; i++) 
			{
				this.jogadores[i].atualizar(this);
				if (!this.terminou) 
					this.jogadores[i].desenhar(this.contexto);
			}
		}
	}

	this.finalizar = function(jogadorVencedor) {
		this.terminou = true;
		this.contexto.clearRect(0, 0, 1920, 1080)
		contexto.fillStyle = jogadorVencedor.cor;
		contexto.textAlign = 'center';
		contexto.font = '32px Arial';

		var xTexto = (configuracoes.ultimaColunaPreenchida * configuracoes.TAMANHO_CELULA + configuracoes.TAMANHO_CELULA) / 2;
		var yTexto = (configuracoes.ultimaLinhaPreenchida * configuracoes.TAMANHO_CELULA + configuracoes.TAMANHO_CELULA + configuracoes.ALTURA_MENU * 2 + configuracoes.DISTANCIA_MENU_TABULEIRO * 2) / 2;
		contexto.fillText("O jogador " + jogadorVencedor.posicao + " venceu!", xTexto, yTexto);
	}
}