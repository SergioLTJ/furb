function Jogo(contexto) {

	this.tabuleiro;
	this.jogadores = [];
	this.indiceJogadorAtual = 0;

	this.contexto = contexto;

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
		requestAnimationFrame(this.step.bind(this));
		this.tabuleiro.atualizar();
		for (var i = 0; i < this.jogadores.length; i++) {
			this.jogadores[i].atualizar(this);
			this.jogadores[i].desenhar(this.contexto);
		}
	}

	this.finalizar = function() {
		this.contexto.clearRect(0, 1920, 1080)
	}
}