function Jogo(contexto) {

	this.tabuleiro;

	this.contexto = contexto;

	this.iniciar = function() {
		this.tabuleiro = new Tabuleiro(this.contexto);
		this.tabuleiro.adicionarJogador(0, 'red');
		this.tabuleiro.adicionarJogador(1, 'green');
		this.tabuleiro.adicionarJogador(2, 'blue');
		this.tabuleiro.adicionarJogador(3, 'yellow');
		
		var canvas = document.getElementById('canvasJogo');
		canvas.onclick = function (evento) { tabuleiro.verificarClique(evento, canvas); };

		this.step();
	}

	this.step = function() {
		requestAnimationFrame(this.step.bind(this));
		this.tabuleiro.atualizar();
	}
}