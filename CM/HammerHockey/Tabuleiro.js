function Tabuleiro() {
	this.matriz = [
		[1, 0, 0, 0, 11],
		[2, 0, 8, 9, 10],
		[3, 0, 7, 0, 12],
		[4, 5, 6, 0, 13],
		[0, 0, 0, 0, 0],
	];

	this.desenhar = function (contextoGrafico) {
		contextoGrafico.clearRect(0, 0, 1920, 1080);
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = 0; j < this.matriz[i].length; j++) {
				if (this.matriz[i][j]) {
					this.matriz[i][j].desenhar(contextoGrafico);
				}
			}
		}
	}

	this.posicionarJogador = function(x, y, posicao, cor) {
		var celula = this.matriz[y][x];
		var jogador = new Jogador(celula, posicao, cor);
		celula.atribuirJogador(jogador);
	}

	this.inicializarTabuleiro = function() {
		var anterior = null;
		var posicaoFinal = this.determinarPosicaoFinal();
		for (var i = 1; i <= posicaoFinal; i++) {
			for (var j = 0; j < this.matriz.length; j++) {
				for (var k = 0; k < this.matriz[j].length; k++) {
					if (this.matriz[j][k] == i) {
						this.matriz[j][k] = new Celula(k, j, i);
						if (anterior != null) {
							anterior.sucessor = this.matriz[j][k];
						}
						anterior = this.matriz[j][k];
					}
				}
			}
		}
	}
	
	this.determinarPosicaoFinal = function() {
		var maior = 0;
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = 0; j < this.matriz[i].length; j++) {
				if (this.matriz[i][j] > maior) {
					maior = this.matriz[i][j];
				}
			}
		}
		return maior;
	}
	
 	this.inicializarTabuleiro();
}