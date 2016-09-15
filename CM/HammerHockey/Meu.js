$(document).ready(function () {
	var indice = 0;
	var jogadores = [];

	var canvas = document.getElementById("canvasJogo");
	var contexto = canvas.getContext("2d");
	var tabuleiro = new Tabuleiro();
	
	tabuleiro.posicionarJogador(0, 0, 0, 'red');
	tabuleiro.posicionarJogador(0, 0, 1, 'green');
	tabuleiro.posicionarJogador(0, 0, 2, 'blue');
	tabuleiro.posicionarJogador(0, 0, 3, 'orange');

	$(window).resize(function() {
		resizeCanvas();
	});

	resizeCanvas();

	function rolarDado() {
		var dado = Math.floor((Math.random() * 6) + 1);
		$("#txResultado").val(dado);
		
		for (var i = 0; i < dado; i++) {
			
		}
	}

	function animar() {
		requestAnimationFrame(function() {
			tabuleiro.desenhar(contexto);
			requestAnimationFrame(animar);
		});
	}

	function resizeCanvas() {
		var htmlCanvas = document.getElementById('canvasJogo');

		htmlCanvas.width = window.innerWidth;
		htmlCanvas.height = window.innerHeight;
	}

	$("#btnDado").click(rolarDado);

	animar();
});