$(document).ready(function () {
	var c = document.getElementById("canvasJogo");
	var contexto = c.getContext("2d");
	var tabuleiro = new Tabuleiro();
	tabuleiro.desenhar(contexto);
	tabuleiro.posicionarJogador(0, 0, 1, 'red');
	tabuleiro.posicionarJogador(0, 0, 2, 'green');
	tabuleiro.posicionarJogador(0, 0, 3, 'blue');
	tabuleiro.posicionarJogador(0, 0, 4, 'orange');

	$(window).resize(function() {
		resizeCanvas();
	});

	resizeCanvas();

	function resizeCanvas() {
		var htmlCanvas = document.getElementById('canvasJogo');

		htmlCanvas.width = window.innerWidth;
		htmlCanvas.height = window.innerHeight;
	}

	setInterval(function () {
		tabuleiro.desenhar(contexto);
	}, 16.66666666666666666667)
});