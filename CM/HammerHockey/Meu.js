document.addEventListener("DOMContentLoaded", function(event) {
	var jogador1 = document.getElementById('jogador1');
	var jogador2 = document.getElementById('jogador2');
	var bola = document.getElementById('bola');
	
	inicializarHammerMovimentacao(jogador1);
	inicializarHammerMovimentacao(jogador2);
	inicializarHammerMovimentacao(bola);

	function inicializarHammerMovimentacao(elemento) {
		// create a simple instance
		// by default, it only adds horizontal recognizers
		var mc = new Hammer(elemento);
		
		// let the pan gesture support all directions.
		// this will block the vertical scrolling on a touch-device while on the element
		mc.get('pan').set({ direction: Hammer.DIRECTION_ALL });
		
		var variacaoX = 0;
		var variacaoY = 0;
		
		// listen to events...
		mc.on("panleft panright panup pandown", function(ev) {
			elemento.style.top = parseInt(elemento.style.top, 10) + (ev.deltaY - variacaoY);
			elemento.style.left  = parseInt(elemento.style.left, 10) + (ev.deltaX - variacaoX);
				
			variacaoX = ev.deltaX;
			variacaoY = ev.deltaY;
		});
		
		mc.on("pressup", function(ev) {
			variacaoX = 0;
			variacaoY = 0;
		});
	}
});

function toggleFullScreen() {
	var elem = document.getElementById("fundo")
	if (!elem.fullscreenElement) {
		if (elem.requestFullscreen) {
			elem.requestFullscreen();
		} else if (elem.msRequestFullscreen) {
			elem.msRequestFullscreen();
		} else if (elem.mozRequestFullScreen) {
			elem.mozRequestFullScreen();
		} else if (elem.webkitRequestFullscreen) {
			elem.webkitRequestFullscreen();
		}
	} else {
		if (elem.exitFullscreen) {
			elem.exitFullscreen(); 
		}
	}
}