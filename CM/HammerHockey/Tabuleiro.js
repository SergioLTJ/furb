function Tabuleiro() {
	this.tamanhoCelula = 50;

	this.matriz = [
		[new Celula(0, 0, 1), null,                null,                null,                   new Celula(4, 0, 10)],
		[new Celula(0, 1, 2), null,                new Celula(2, 1, 8), new Celula(3, 1, 9),    new Celula(4, 1, 11)],
		[new Celula(0, 2, 3), null,                new Celula(2, 2, 7), null,                   new Celula(4, 2, 12)],
		[new Celula(0, 3, 4), new Celula(1, 3, 5), new Celula(2, 3, 6), null,                   new Celula(4, 3, 13)],
		[null,                null,                null,                null,                   null],
	];

	this.desenhar = function (contextoGrafico) {
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = 0; j < this.matriz[i].length; j++) {
				if (this.matriz[i][j]) {
					this.matriz[i][j].desenhar(contextoGrafico);
				}
			}
		}
	}

	this.posicionarJogador = function(x, y, posicao, cor) {
		var jogador = new Jogador(x, y, posicao, cor);
		this.matriz[x][y].atribuirJogador(jogador);
	}

	this.montarOrdem = function() {
		var atual = this.matriz[0][0];
		for (var i = 2; i < 14; i++) {
			for (var j = 0; j < this.matriz.length; j++) {
				for (var k = 0; k < this.matriz[j].length; k++) {
					if (this.matriz[j][k] && this.matriz[j][k].ordem == i) {
						atual.atribuirSucessor(this.matriz[j][k]);
						atual = this.matriz[j][k];
					}
				}
			}
		}
 	}

 	this.montarOrdem();
}