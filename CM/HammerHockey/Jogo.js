function Jogo(contexto) {

	this.tabuleiro;
	this.jogadores = [];

	
	this.contexto = contexto;

	this.iniciar = function() {
		var self = this;
		
		this.tabuleiro = new Tabuleiro(this.contexto);
		this.jogadores.push(this.tabuleiro.adicionarJogador(0, 'red'));
		this.jogadores.push(this.tabuleiro.adicionarJogador(1, 'green'));
		this.jogadores.push(this.tabuleiro.adicionarJogador(2, 'blue'));
		this.jogadores.push(this.tabuleiro.adicionarJogador(3, 'yellow'));
		
		var canvas = document.getElementById('canvasJogo');
		canvas.onclick = function (evento) { 
			self.tabuleiro.verificarClique(evento, canvas);
			for (var i = 0; i < self.jogadores.length; i++) {
				self.jogadores[i].verificarClique(evento);
			}
		};

		this.step();
	}

	this.step = function() {
		requestAnimationFrame(this.step.bind(this));
		this.tabuleiro.atualizar();
		for (var i = 0; i < this.jogadores.length; i++) {
			this.jogadores[i].desenhar(this.contexto);
		}
	}
}